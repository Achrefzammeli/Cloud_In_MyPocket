package tn.esprit.cloud_in_mypocket.repository;

import tn.esprit.cloud_in_mypocket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
//tlawejj ala users active ama maconnectewch aandhom barcha
    @Query("SELECT u FROM User u WHERE u.lastLoginDate < :date AND u.isActive = true")
    List<User> findInactiveUsersSince(@Param("date") LocalDateTime date);
}
