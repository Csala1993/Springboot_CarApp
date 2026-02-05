package com.carapp.carmaintenance.repository;

import com.carapp.carmaintenance.model.IstoricInvestitii;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IstoricInvestitiiRepository extends JpaRepository<IstoricInvestitii, Long> {
    List<IstoricInvestitii> findByMasinaId(Long masinaId);
}
