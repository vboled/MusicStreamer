package vboled.netcracker.musicstreamer.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import vboled.netcracker.musicstreamer.IntegrationTestBase;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest extends IntegrationTestBase {

    @Autowired
    private UserRepository userRepositoryTest;

    @Test
    void findByUserNameIfExist() {
        String email = userRepositoryTest.findByUserName("admin").get().getEmail();

        assertThat(email).isEqualTo("tEmail1@edu.ru");
    }

    @Test
    void findByUserNameIfNotExist() {
        try {
            String email = userRepositoryTest.findByUserName("admin_NOT_EXIST").get().getEmail();
        } catch (NoSuchElementException e) {

        }

    }

    @Test
    void findByPhoneNumberIfExist() {
        String email = userRepositoryTest.findByPhoneNumber("+79998881122").get().getEmail();

        assertThat(email).isEqualTo("tEmail1@edu.ru");
    }

    @Test
    void findByPhoneNumberIfNotExist() {
        try {
            String email = userRepositoryTest.findByPhoneNumber("admin_NOT_EXIST").get().getEmail();
        } catch (NoSuchElementException e) {

        }

    }
}