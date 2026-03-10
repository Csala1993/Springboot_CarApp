package com.carapp.carmaintenance.repository;

import com.carapp.carmaintenance.model.Rovinieta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RovinietaRepository extends JpaRepository<Rovinieta, Long> {


    List<Rovinieta> findByDataExpirareBeforeAndDataExpirareAfter(LocalDate before, LocalDate after);

    List<Rovinieta> findByDurata(Rovinieta.DurataRovinieta durata);

    List<Rovinieta> findByDataExpirareBefore(LocalDate data);
}