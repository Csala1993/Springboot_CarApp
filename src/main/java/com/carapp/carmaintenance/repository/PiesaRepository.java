package com.carapp.carmaintenance.repository;

import com.carapp.carmaintenance.model.Piesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository
public interface PiesaRepository extends JpaRepository<Piesa, Long> {

    List<Piesa> findByNumeContainingIgnoreCase(String nume);

    List<Piesa> findByDistribuitor(String distribuitor);

    List<Piesa> findByPretBetween(Double pretMin, Double pretMax);

    Optional<Piesa> findByNumeIgnoreCaseAndDistribuitor(String nume, String distribuitor);
}