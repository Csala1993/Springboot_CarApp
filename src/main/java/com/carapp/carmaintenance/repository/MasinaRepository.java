package com.carapp.carmaintenance.repository;

import com.carapp.carmaintenance.model.Masina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasinaRepository extends JpaRepository<Masina, Long> {

    List<Masina> findByUserId(Long userId);

    boolean existsByNumarInmatriculare(String numarInmatriculare);
}