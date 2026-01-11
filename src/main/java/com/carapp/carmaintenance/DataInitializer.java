package com.carapp.carmaintenance;

import com.carapp.carmaintenance.model.Asigurare;
import com.carapp.carmaintenance.model.IstoricService;
import com.carapp.carmaintenance.model.Masina;
import com.carapp.carmaintenance.model.Piesa;
import com.carapp.carmaintenance.model.User;
import com.carapp.carmaintenance.repository.AsigurareRepository;
import com.carapp.carmaintenance.repository.IstoricServiceRepository;
import com.carapp.carmaintenance.repository.MasinaRepository;
import com.carapp.carmaintenance.repository.PiesaRepository;
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

    @Autowired
    private PiesaRepository piesaRepository;

    @Autowired
    private IstoricServiceRepository istoricServiceRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verifică dacă deja există date
        if (userRepository.count() > 0) {
            System.out.println("===========================================");
            System.out.println("Datele există deja în baza de date!");
            System.out.println("- " + userRepository.count() + " utilizatori");
            System.out.println("- " + masinaRepository.count() + " mașini");
            System.out.println("- " + asigurareRepository.count() + " asigurări");
            System.out.println("- " + piesaRepository.count() + " piese");
            System.out.println("- " + istoricServiceRepository.count() + " servicii efectuate");
            System.out.println("===========================================");
            return;
        }

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

        // Creează piese auto
        Piesa ulei = new Piesa("Ulei motor 5W30", 150.0, "Castrol");
        ulei = piesaRepository.save(ulei);

        Piesa filtruUlei = new Piesa("Filtru ulei", 25.0, "Mann Filter");
        filtruUlei = piesaRepository.save(filtruUlei);

        Piesa filtruAer = new Piesa("Filtru aer", 35.0, "Bosch");
        filtruAer = piesaRepository.save(filtruAer);

        Piesa distributie = new Piesa("Kit distributie", 450.0, "Gates");
        distributie = piesaRepository.save(distributie);

        Piesa clapetaAcceleratie = new Piesa("Clapeta acceleratie", 320.0, "Pierburg");
        clapetaAcceleratie = piesaRepository.save(clapetaAcceleratie);

        Piesa placuteFrana = new Piesa("Placute frana fata", 180.0, "Brembo");
        placuteFrana = piesaRepository.save(placuteFrana);

        // Creează istoric service pentru masina1 (Dacia Logan)
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

        // Creează istoric service pentru masina2 (Golf)
        IstoricService service3 = new IstoricService();
        service3.setDataService(LocalDate.of(2024, 8, 20));
        service3.setKilometrajLaService(55000);
        service3.setDescriere("Schimb clapeta acceleratie si placute frana");
        service3.setServiceAuto("VW Service Center");
        service3.setMasina(masina2);
        service3.adaugaPiesa(clapetaAcceleratie);
        service3.adaugaPiesa(placuteFrana);
        service3.calculeazaCostTotal();
        istoricServiceRepository.save(service3);

        System.out.println("===========================================");
        System.out.println("Date inițializate cu succes!");
        System.out.println("- " + userRepository.count() + " utilizatori");
        System.out.println("- " + masinaRepository.count() + " mașini");
        System.out.println("- " + asigurareRepository.count() + " asigurări");
        System.out.println("- " + piesaRepository.count() + " piese");
        System.out.println("- " + istoricServiceRepository.count() + " servicii efectuate");
        System.out.println("===========================================");
    }
}