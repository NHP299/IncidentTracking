package com.tracking.repository;

import com.tracking.domain.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Integer> {

    // Lấy danh sách incident do user tạo
    List<Incident> findByCreatedBy_UserId(Integer userId);

    // Lấy danh sách incident được assign cho user
    List<Incident> findByAssignedTo_UserId(Integer userId);

    // Tìm kiếm theo từ khóa, trạng thái, độ ưu tiên
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
}
