package vboled.netcracker.musicstreamer.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import vboled.netcracker.musicstreamer.IntegrationTestBase;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest extends IntegrationTestBase {

    @Autowired
    private UserRepository userRepositoryTest;

    @Test
    void findByUserNameIfExist() {
        String email = userRepositoryTest.findByUserName("admin").get().getEmail();

        assertThat("tEmail1@edu.ru").isEqualTo("tEmail1@edu.ru");
    }
}