package com.carapp.carmaintenance.repository;

import com.carapp.carmaintenance.model.IstoricService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IstoricServiceRepository extends JpaRepository<IstoricService, Long> {

    List<IstoricService> findByMasinaId(Long masinaId);

    List<IstoricService> findByMasinaIdOrderByKilometrajLaServiceDesc(Long masinaId);

    List<IstoricService> findByMasinaIdOrderByDataServiceDesc(Long masinaId);
}