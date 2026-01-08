package com.carapp.carmaintenance.repository;

import com.carapp.carmaintenance.model.Asigurare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AsigurareRepository extends JpaRepository<Asigurare, Long> {

    Optional<Asigurare> findByVinMasina(String vinMasina);

    List<Asigurare> findByNumeProprietar(String numeProprietar);

    List<Asigurare> findByNumeAsigurator(String numeAsigurator);

    // Găsește asigurările care expiră în următoarele X zile
    List<Asigurare> findByDataIncheiereBeforeAndDataIncheiereAfter(LocalDate before, LocalDate after);
}