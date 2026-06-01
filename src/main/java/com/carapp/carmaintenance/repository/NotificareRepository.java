package com.carapp.carmaintenance.repository;

import com.carapp.carmaintenance.model.Notificare;
import com.carapp.carmaintenance.model.Notificare.TipNotificare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificareRepository extends JpaRepository<Notificare, Long> {
    List<Notificare> findByUserIdOrderByDataCreareDesc(Long userId);
    List<Notificare> findByUserIdAndCititaFalse(Long userId);
    long countByUserIdAndCititaFalse(Long userId);

    void deleteByUserIdAndTip(Long userId, TipNotificare tip);
}