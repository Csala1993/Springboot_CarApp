package com.carapp.carmaintenance;

import com.carapp.carmaintenance.model.Asigurare;
import com.carapp.carmaintenance.model.ITP;
import com.carapp.carmaintenance.model.IstoricInvestitii;
import com.carapp.carmaintenance.model.IstoricService;
import com.carapp.carmaintenance.model.Masina;
import com.carapp.carmaintenance.model.Piesa;
import com.carapp.carmaintenance.model.Rovinieta;
import com.carapp.carmaintenance.model.User;
import com.carapp.carmaintenance.repository.AsigurareRepository;
import com.carapp.carmaintenance.repository.IstoricInvestitiiRepository;
import com.carapp.carmaintenance.repository.IstoricServiceRepository;
import com.carapp.carmaintenance.repository.MasinaRepository;
import com.carapp.carmaintenance.repository.PiesaRepository;
import com.carapp.carmaintenance.repository.RovinietaRepository;
import com.carapp.carmaintenance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private UserRepository userRepository;
    @Autowired private MasinaRepository masinaRepository;
    @Autowired private AsigurareRepository asigurareRepository;
    @Autowired private PiesaRepository piesaRepository;
    @Autowired private IstoricServiceRepository istoricServiceRepository;
    @Autowired private RovinietaRepository rovinietaRepository;
    @Autowired private IstoricInvestitiiRepository investitiiRepository;

    @Autowired private JdbcTemplate jdbcTemplate;

    // Setează la true când vrei să recreezi datele (și să pornească ID-urile de la 1)
    private static final boolean FORCE_RECREATE_DATA = false;

    @Override
    public void run(String... args) {
        if (FORCE_RECREATE_DATA) {
            System.out.println("FORCE_RECREATE_DATA = true - TRUNCATE + RESTART IDENTITY + CASCADE...");

            // ✅ include și join tables: service_piese + investitie_piese
            jdbcTemplate.execute("""
                    TRUNCATE TABLE
                        service_piese,
                        investitie_piese,
                        istoric_service,
                        istoric_investitii,
                        masini,
                        roviniete,
                        itp,
                        asigurari,
                        piese,
                        users
                    RESTART IDENTITY
                    CASCADE
                    """);
        }

        if (userRepository.count() > 0) {
            System.out.println("===========================================");
            System.out.println("Datele există deja în baza de date!");
            System.out.println("- " + userRepository.count() + " utilizatori");
            System.out.println("- " + masinaRepository.count() + " mașini");
            System.out.println("- " + asigurareRepository.count() + " asigurări");
            System.out.println("- " + rovinietaRepository.count() + " roviniete");
            System.out.println("- " + piesaRepository.count() + " piese");
            System.out.println("- " + istoricServiceRepository.count() + " servicii efectuate");
            System.out.println("- " + investitiiRepository.count() + " investiții");
            System.out.println("===========================================");
            return;
        }

        System.out.println("Inițializare date...");

        // =========================
        // UTILIZATORI
        // =========================
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

        // =========================
        // MAȘINI + ASIGURĂRI + ROVINIETE + ITP
        // =========================

        Masina masina1 = new Masina();
        masina1.setMarca("Dacia");
        masina1.setModel("Logan");
        masina1.setAn(2020);
        masina1.setNumarInmatriculare("TM-01-ABC");
        masina1.setVin("UU1TA31511L111111");
        masina1.setKilometraj(45000);
        masina1.setUser(user1);

        Asigurare asigurare1 = new Asigurare();
        asigurare1.setDataInceput(LocalDate.of(2024, 1, 15));
        asigurare1.setDataIncheiere(LocalDate.of(2025, 1, 15));
        asigurare1.setNumeAsigurator("Allianz-Tiriac");
        asigurare1.setVinMasina("UU1TA31511L111111");
        asigurare1.setNumeProprietar("Ion Popescu");
        masina1.setAsigurare(asigurare1);

        masina1.setRovinieta(new Rovinieta(LocalDate.of(2026, 1, 10), Rovinieta.DurataRovinieta.UN_AN));
        masina1.setItp(createItpForMasina(masina1, LocalDate.of(2026, 1, 5)));

        masina1 = masinaRepository.save(masina1);

        Masina masina2 = new Masina();
        masina2.setMarca("Volkswagen");
        masina2.setModel("Golf");
        masina2.setAn(2019);
        masina2.setNumarInmatriculare("TM-02-XYZ");
        masina2.setVin("WVWZZZ1KZBW123456");
        masina2.setKilometraj(60000);
        masina2.setUser(user1);

        Asigurare asigurare2 = new Asigurare();
        asigurare2.setDataInceput(LocalDate.of(2024, 6, 1));
        asigurare2.setDataIncheiere(LocalDate.of(2025, 6, 1));
        asigurare2.setNumeAsigurator("Omniasig");
        asigurare2.setVinMasina("WVWZZZ1KZBW123456");
        asigurare2.setNumeProprietar("Ion Popescu");
        masina2.setAsigurare(asigurare2);

        masina2.setRovinieta(new Rovinieta(LocalDate.of(2026, 2, 1), Rovinieta.DurataRovinieta.TREIZECI_ZILE));
        masina2.setItp(createItpForMasina(masina2, LocalDate.of(2026, 2, 3)));

        masina2 = masinaRepository.save(masina2);

        Masina masina3 = new Masina();
        masina3.setMarca("Renault");
        masina3.setModel("Clio");
        masina3.setAn(2021);
        masina3.setNumarInmatriculare("TM-03-DEF");
        masina3.setVin("VF1RJA00654123456");
        masina3.setKilometraj(25000);
        masina3.setUser(user2);

        Asigurare asigurare3 = new Asigurare();
        asigurare3.setDataInceput(LocalDate.of(2024, 3, 20));
        asigurare3.setDataIncheiere(LocalDate.of(2025, 3, 20));
        asigurare3.setNumeAsigurator("Groupama");
        asigurare3.setVinMasina("VF1RJA00654123456");
        asigurare3.setNumeProprietar("Maria Ionescu");
        masina3.setAsigurare(asigurare3);

        masina3.setItp(createItpForMasina(masina3, LocalDate.of(2026, 2, 4)));
        masina3 = masinaRepository.save(masina3);

        // =========================
        // PIESE (le refolosim și la service și la investiții)
        // =========================
        Piesa ulei = piesaRepository.save(new Piesa("Ulei motor 5W30", 150.0, "Castrol"));
        Piesa filtruUlei = piesaRepository.save(new Piesa("Filtru ulei", 25.0, "Mann Filter"));
        Piesa filtruAer = piesaRepository.save(new Piesa("Filtru aer", 35.0, "Bosch"));
        Piesa distributie = piesaRepository.save(new Piesa("Kit distributie", 450.0, "Gates"));
        Piesa clapetaAcceleratie = piesaRepository.save(new Piesa("Clapeta acceleratie", 320.0, "Pierburg"));
        Piesa placuteFrana = piesaRepository.save(new Piesa("Placute frana fata", 180.0, "Brembo"));

        // 🧩 piese “investiții”
        Piesa jante = piesaRepository.save(new Piesa("Set jante 18 inch", 3200.0, "OZ Racing"));
        Piesa folii = piesaRepository.save(new Piesa("Folie omologată spate", 750.0, "LLumar"));
        Piesa audio = piesaRepository.save(new Piesa("Sistem audio upgrade", 1900.0, "Pioneer"));

        // =========================
        // ISTORIC SERVICE (mentenanță)
        // =========================
        IstoricService service1 = new IstoricService();
        service1.setDataService(LocalDate.of(2023, 5, 10));
        service1.setKilometrajLaService(40000);
        service1.setDescriere("Schimb ulei si filtre");
        service1.setServiceAuto("Service Auto Dacia TM");
        service1.setMasina(masina1);
        service1.adaugaPiesa(ulei);
        service1.adaugaPiesa(filtruUlei);
        service1.adaugaPiesa(filtruAer);
        service1.calculeazaCostTotal();
        istoricServiceRepository.save(service1);

        IstoricService service2 = new IstoricService();
        service2.setDataService(LocalDate.of(2024, 2, 15));
        service2.setKilometrajLaService(120000);
        service2.setDescriere("Schimb distributie");
        service2.setServiceAuto("Service Auto Expert");
        service2.setMasina(masina1);
        service2.adaugaPiesa(distributie);
        service2.calculeazaCostTotal();
        istoricServiceRepository.save(service2);

        // =========================
        // ISTORIC INVESTIȚII (upgrade-uri)
        // =========================
        IstoricInvestitii inv1 = new IstoricInvestitii();
        inv1.setDataInvestitie(LocalDate.of(2026, 1, 20));
        inv1.setTitlu("Jante + folii");
        inv1.setDescriere("Set jante 18 inch + folii omologate spate");
        inv1.setManopera(300.0);
        inv1.setMasina(masina1);
        inv1.adaugaPiesa(jante);
        inv1.adaugaPiesa(folii);
        inv1.calculeazaCostTotal();
        investitiiRepository.save(inv1);

        IstoricInvestitii inv2 = new IstoricInvestitii();
        inv2.setDataInvestitie(LocalDate.of(2026, 2, 2));
        inv2.setTitlu("Upgrade audio");
        inv2.setDescriere("Sistem audio îmbunătățit + montaj");
        inv2.setManopera(250.0);
        inv2.setMasina(masina2);
        inv2.adaugaPiesa(audio);
        inv2.calculeazaCostTotal();
        investitiiRepository.save(inv2);

        System.out.println("===========================================");
        System.out.println("Date inițializate cu succes!");
        System.out.println("- " + userRepository.count() + " utilizatori");
        System.out.println("- " + masinaRepository.count() + " mașini");
        System.out.println("- " + asigurareRepository.count() + " asigurări");
        System.out.println("- " + rovinietaRepository.count() + " roviniete");
        System.out.println("- " + piesaRepository.count() + " piese");
        System.out.println("- " + istoricServiceRepository.count() + " servicii efectuate");
        System.out.println("- " + investitiiRepository.count() + " investiții");
        System.out.println("===========================================");
    }

    private ITP createItpForMasina(Masina masina, LocalDate dataEfectuare) {
        int varsta = dataEfectuare.getYear() - masina.getAn();
        int aniValabilitate;

        if (varsta < 3) aniValabilitate = 3;
        else if (varsta <= 12) aniValabilitate = 2;
        else aniValabilitate = 1;

        ITP itp = new ITP(dataEfectuare);
        itp.setDataExpirare(dataEfectuare.plusYears(aniValabilitate));
        return itp;
    }
}
