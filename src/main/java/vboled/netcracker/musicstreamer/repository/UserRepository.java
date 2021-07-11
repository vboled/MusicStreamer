package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vboled.netcracker.musicstreamer.model.User;

import java.util.Date;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT CURRENT_TIMESTAMP", nativeQuery = true)
    String getCurrentTime();

    Optional<User> findByUserName(String username);
}
