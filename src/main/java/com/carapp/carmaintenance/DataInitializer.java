package com.carapp.carmaintenance;

import com.carapp.carmaintenance.model.Asigurare;
import com.carapp.carmaintenance.model.Masina;
import com.carapp.carmaintenance.model.User;
import com.carapp.carmaintenance.repository.AsigurareRepository;
import com.carapp.carmaintenance.repository.MasinaRepository;
import com.carapp.carmaintenance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MasinaRepository masinaRepository;

    @Autowired
    private AsigurareRepository asigurareRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verifică dacă deja există date (comentat pentru a permite reinițializarea)
        // if (userRepository.count() > 0) {
        //     System.out.println("Datele există deja în baza de date!");
        //     return;
        // }

        System.out.println("Inițializare date...");

        // Creează utilizatori
        User user1 = new User();
        user1.setNume("Ion Popescu");
        user1.setEmail("ion.popescu@email.com");
        user1.setParola("parola123");
        user1 = userRepository.save(user1);

        User user2 = new User();
        user2.setNume("Maria Ionescu");
        user2.setEmail("maria.ionescu@email.com");
        user2.setParola("parola456");
        user2 = userRepository.save(user2);

        // Creează mașini pentru Ion
        Masina masina1 = new Masina();
        masina1.setMarca("Dacia");
        masina1.setModel("Logan");
        masina1.setAn(2020);
        masina1.setNumarInmatriculare("TM-01-ABC");
        masina1.setVin("UU1TA31511L111111");
        masina1.setKilometraj(45000);
        masina1.setUser(user1);

        // Creează asigurare pentru masina1
        Asigurare asigurare1 = new Asigurare();
        asigurare1.setDataInceput(LocalDate.of(2024, 1, 15));
        asigurare1.setDataIncheiere(LocalDate.of(2025, 1, 15));
        asigurare1.setNumeAsigurator("Allianz-Tiriac");
        asigurare1.setVinMasina("UU1TA31511L111111");
        asigurare1.setNumeProprietar("Ion Popescu");

        // Setează relația bidirectională
        masina1.setAsigurare(asigurare1);
        asigurare1.setMasina(masina1);

        masina1 = masinaRepository.save(masina1);

        Masina masina2 = new Masina();
        masina2.setMarca("Volkswagen");
        masina2.setModel("Golf");
        masina2.setAn(2019);
        masina2.setNumarInmatriculare("TM-02-XYZ");
        masina2.setVin("WVWZZZ1KZBW123456");
        masina2.setKilometraj(60000);
        masina2.setUser(user1);

        // Creează asigurare pentru masina2
        Asigurare asigurare2 = new Asigurare();
        asigurare2.setDataInceput(LocalDate.of(2024, 6, 1));
        asigurare2.setDataIncheiere(LocalDate.of(2025, 6, 1));
        asigurare2.setNumeAsigurator("Omniasig");
        asigurare2.setVinMasina("WVWZZZ1KZBW123456");
        asigurare2.setNumeProprietar("Ion Popescu");

        // Setează relația bidirectională
        masina2.setAsigurare(asigurare2);
        asigurare2.setMasina(masina2);

        masina2 = masinaRepository.save(masina2);

        // Creează mașină pentru Maria
        Masina masina3 = new Masina();
        masina3.setMarca("Renault");
        masina3.setModel("Clio");
        masina3.setAn(2021);
        masina3.setNumarInmatriculare("TM-03-DEF");
        masina3.setVin("VF1RJA00654123456");
        masina3.setKilometraj(25000);
        masina3.setUser(user2);

        // Creează asigurare pentru masina3
        Asigurare asigurare3 = new Asigurare();
        asigurare3.setDataInceput(LocalDate.of(2024, 3, 20));
        asigurare3.setDataIncheiere(LocalDate.of(2025, 3, 20));
        asigurare3.setNumeAsigurator("Groupama");
        asigurare3.setVinMasina("VF1RJA00654123456");
        asigurare3.setNumeProprietar("Maria Ionescu");

        // Setează relația bidirectională
        masina3.setAsigurare(asigurare3);
        asigurare3.setMasina(masina3);

        masina3 = masinaRepository.save(masina3);

        System.out.println("Date inițializate cu succes!");
        System.out.println("- " + userRepository.count() + " utilizatori");
        System.out.println("- " + masinaRepository.count() + " mașini");
        System.out.println("- " + asigurareRepository.count() + " asigurări");
    }
}