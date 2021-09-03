package vboled.netcracker.musicstreamer.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import vboled.netcracker.musicstreamer.model.user.User;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    private UserRepository userRepositoryTest;

    @Test
    void findByUserNameIfExist() {
        String email = userRepositoryTest.findByUserName("admin").get().getEmail();

        assertThat(email).isEqualTo("tEmail1@edu.ru");
    }
}