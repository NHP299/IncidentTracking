package  com.tracking.repository;

import  com.tracking.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByIncident_IncidentId(Integer incidentId);
    List<Comment> findByUser_UserId(Integer userId);
}