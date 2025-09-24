package  com.tracking.repository;

import  com.tracking.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUser_UserId(Integer userId);
    List<Notification> findByIsReadFalse();
}