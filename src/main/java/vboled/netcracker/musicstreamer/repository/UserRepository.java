package vboled.netcracker.musicstreamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vboled.netcracker.musicstreamer.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
