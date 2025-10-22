package com.tracking.repository;

import com.tracking.domain.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Integer> {
    List<Incident> findByCreatedBy_UserId(Integer userId);
    List<Incident> findByAssignedTo_UserId(Integer userId);
    @Query("""
        SELECT i FROM Incident i
        WHERE (:keyword IS NULL OR LOWER(i.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(i.description) LIKE LOWER(CONCAT('%', :keyword, '%')))
          AND (:status IS NULL OR i.status = :status)
          AND (:priority IS NULL OR i.priority = :priority)
        """)
    List<Incident> search(
            @Param("keyword") String keyword,
            @Param("status") String status,
            @Param("priority") String priority
    );
    @Modifying
    @Transactional
    @Query(value = "DBCC CHECKIDENT ('incidents', RESEED, 0)", nativeQuery = true)
    void resetIdentity();
}
