package  com.tracking.repository;


import  com.tracking.domain.IncidentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentHistoryRepository extends JpaRepository<IncidentHistory, Integer> {
    List<IncidentHistory> findByIncident_IncidentId(Integer incidentId);
    List<IncidentHistory> findByChangedBy_UserId(Integer userId);
}
