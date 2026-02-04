package com.carapp.carmaintenance.repository;

import com.carapp.carmaintenance.model.Rovinieta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RovinietaRepository extends JpaRepository<Rovinieta, Long> {

    // Găsește roviniete care expiră între două date
    List<Rovinieta> findByDataExpirareBeforeAndDataExpirareAfter(LocalDate before, LocalDate after);

    // Găsește roviniete după durată
    List<Rovinieta> findByDurata(Rovinieta.DurataRovinieta durata);

    // Găsește roviniete expirate
    List<Rovinieta> findByDataExpirareBefore(LocalDate data);
}