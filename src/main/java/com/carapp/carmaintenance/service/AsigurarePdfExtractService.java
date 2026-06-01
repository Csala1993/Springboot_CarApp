package com.carapp.carmaintenance.service;

import com.carapp.carmaintenance.dto.AsigurareExtractDTO;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AsigurarePdfExtractService {

    public AsigurareExtractDTO extrageDate(MultipartFile file) {
        try {
            String text = extractText(file);
            String searchText = normalizeForSearch(text);

            System.out.println("TEXT PDF EXTRAS:");
            System.out.println(text);

            System.out.println("TEXT PDF NORMALIZAT:");
            System.out.println(searchText);

            AsigurareExtractDTO dto = new AsigurareExtractDTO();

            dto.setNumeAsigurator(extrageAsigurator(searchText));
            dto.setNumeProprietar(extrageProprietar(searchText));
            dto.setVinMasina(extrageVin(searchText));
            dto.setNumarInmatriculare(extrageNumarInmatriculare(searchText));
            dto.setDataInceput(extrageDataInceput(searchText));
            dto.setDataIncheiere(extrageDataIncheiere(searchText));

            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Nu am putut citi PDF-ul: " + e.getMessage(), e);
        }
    }

    private String extractText(MultipartFile file) throws Exception {
        try (PDDocument document = Loader.loadPDF(file.getBytes(), "")) {
            PDFTextStripper stripper = new PDFTextStripper();
            return normalizeText(stripper.getText(document));
        }
    }

    private String normalizeText(String text) {
        if (text == null) {
            return "";
        }

        return text
                .replace("\r", "\n")
                .replaceAll("[ \\t]+", " ")
                .replaceAll("\\n+", "\n")
                .trim();
    }

    private String normalizeForSearch(String text) {
        String normalized = normalizeText(text);

        normalized = Normalizer.normalize(normalized, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        return normalized
                .replace("Ț", "T")
                .replace("ț", "t")
                .replace("Ţ", "T")
                .replace("ţ", "t")
                .replace("Ș", "S")
                .replace("ș", "s")
                .replace("Ş", "S")
                .replace("ş", "s")
                .replace("Ă", "A")
                .replace("ă", "a")
                .replace("Â", "A")
                .replace("â", "a")
                .replace("Î", "I")
                .replace("î", "i")
                .replaceAll("[ \\t]+", " ")
                .replaceAll("\\n+", "\n")
                .trim();
    }

    private String extrageAsigurator(String text) {
        String value = firstMatch(text,
                "Denumire asigurator:\\s*([A-Z0-9 .\\-]+?S\\.A\\.)",
                "(ALLIANZ\\s*-?\\s*TIRIAC\\s+ASIGURARI\\s*S\\.A\\.)",
                "(OMNIASIG\\s+VIENNA\\s+INSURANCE\\s+GROUP\\s+S\\.A\\.)",
                "(GROUPAMA\\s+ASIGURARI\\s*S\\.A\\.)",
                "(GENERALI\\s+ROMANIA\\s+ASIGURARE\\s+REASIGURARE\\s*S\\.A\\.)"
        );

        return clean(value);
    }

    private String extrageProprietar(String text) {
        String value = firstMatch(text,
                "Nume/Denumire Asigurat/\\s*Proprietar:\\s*([A-Z0-9 .&'\\-]+?)\\s*Fel,",
                "Nume/Denumire Asigurat:\\s*PROPRIETAR\\s*([A-Z0-9 .&'\\-]+?)\\s*Fel,",
                "Nume/Denumire Asigurat:\\s*PROPRIETAR\\s*([A-Z0-9 .&'\\-]+?)\\s*C\\.U\\.I\\.",
                "Numele si adresa asiguratului.*?\\n([A-Z0-9 .&'\\-]+?)(?:,|\\n)",
                "Proprietar:\\s*([A-Z0-9 .&'\\-]+?)(?=\\s+Fel|\\s+C\\.U\\.I\\.|\\s+CNP|\\n)"
        );

        return clean(value);
    }

    private String extrageVin(String text) {
        String value = firstMatch(text,
                "Nr\\.?\\s*identificare\\s*-\\s*Serie CIV\\s*/\\s*nr\\.?\\s*de inventar:?\\s*([A-HJ-NPR-Z0-9]{17})",
                "nr\\.?\\s*de inventar:?\\s*([A-HJ-NPR-Z0-9]{17})",
                "\\b([A-HJ-NPR-Z0-9]{17})\\b"
        );

        return clean(value);
    }

    private String extrageNumarInmatriculare(String text) {
        String value = firstMatch(text,
                "Nr\\.?\\s*inmatriculare/inregistrare:\\s*([A-Z0-9]+)",
                "Registration No\\..*?\\n([A-Z]{1,2}\\d{2,3}[A-Z]{3})",
                "\\b([A-Z]{1,2}\\d{2,3}[A-Z]{3})\\b"
        );

        return clean(value);
    }

    private LocalDate extrageDataInceput(String text) {
        String value = firstMatch(text,
                "Valabilitate Contract de la\\s*(\\d{2}[-.]\\d{2}[-.]\\d{4})",
                "(\\d{2}[-.]\\d{2}[-.]\\d{4})\\s*pana la"
        );

        if (value == null) {
            value = extrageDataDinFormatOmniasig(text, false);
        }

        return parseDate(value);
    }

    private LocalDate extrageDataIncheiere(String text) {
        String value = firstMatch(text,
                "Valabilitate Contract de la\\s*\\d{2}[-.]\\d{2}[-.]\\d{4}\\s*pana la:?\\s*(\\d{2}[-.]\\d{2}[-.]\\d{4})",
                "pana la:?\\s*(\\d{2}[-.]\\d{2}[-.]\\d{4})"
        );

        if (value == null) {
            value = extrageDataDinFormatOmniasig(text, true);
        }

        return parseDate(value);
    }

    private String extrageDataDinFormatOmniasig(String text, boolean dataFinal) {
        Pattern pattern = Pattern.compile(
                "(\\d{2}[-.]\\d{2}[-.]\\d{4})\\s*pana la\\s*(\\d{2}[-.]\\d{2}[-.]\\d{4})\\s*Valabilitate Contract de la",
                Pattern.CASE_INSENSITIVE | Pattern.DOTALL
        );

        Matcher matcher = pattern.matcher(text);
        if (!matcher.find()) {
            return null;
        }

        String dataIncheiere = matcher.group(1);
        String dataInceput = matcher.group(2);

        return dataFinal ? dataIncheiere : dataInceput;
    }

    private LocalDate parseDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim().replace(".", "-");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(normalized, formatter);
    }

    private String firstMatch(String text, String... patterns) {
        for (String pattern : patterns) {
            Pattern compiled = Pattern.compile(
                    pattern,
                    Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.UNICODE_CASE
            );

            Matcher matcher = compiled.matcher(text);

            if (matcher.find()) {
                return matcher.group(1);
            }
        }

        return null;
    }

    private String clean(String value) {
        if (value == null) {
            return null;
        }

        return value
                .replaceAll("\\s+", " ")
                .replaceAll("(?i)Fel,.*", "")
                .replaceAll("(?i)Marca.*", "")
                .replaceAll("(?i)Model Vehicul.*", "")
                .replaceAll("(?i)Nr\\. identificare.*", "")
                .replaceAll("(?i)C\\.U\\.I\\..*", "")
                .replaceAll("(?i)CNP.*", "")
                .replaceAll("(?i)Adresa.*", "")
                .trim();
    }
}