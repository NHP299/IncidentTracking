package  com.tracking.repository;

import  com.tracking.domain.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Integer> {

    List<Incident> findByReporter_UserId(Integer userId);

    @Query("SELECT i FROM Incident i " +
            "WHERE (:keyword IS NULL OR i.title LIKE %:keyword% OR i.description LIKE %:keyword%) " +
            "AND (:status IS NULL OR i.status = :status) " +
            "AND (:priority IS NULL OR i.priority = :priority)")
    List<Incident> search(String keyword, String status, String priority);
}
