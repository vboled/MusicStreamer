package vboled.netcracker.musicstreamer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import vboled.netcracker.musicstreamer.IntegrationTestBase;
import vboled.netcracker.musicstreamer.model.Region;
import vboled.netcracker.musicstreamer.model.user.User;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest extends IntegrationTestBase {

    @Autowired
    private UserService userService;

    @Test
    void findByUPE() {
        String email = userService.getByUPE("admin").getEmail();
        assertThat(email).isEqualTo("tEmail1@edu.ru");

        email = userService.getByUPE("tEmail1@edu.ru").getEmail();
        assertThat(email).isEqualTo("tEmail1@edu.ru");

        email = userService.getByUPE("+79998881122").getEmail();
        assertThat(email).isEqualTo("tEmail1@edu.ru");
    }

    @Test
    void createValid() {
        User user = new User();
        user.setEmail("valid1@mail.ru");
        user.setUserName("valid1");
        user.setLastName("valid");
        user.setName("valid1");
        user.setPassword("valid");
        Region r = new Region();
        r.setId(Long.valueOf(1));
        user.setRegion(r);
        user.setLastName("valid1");
        user.setPhoneNumber("+177777777778");

        userService.create(user);

        User created = userService.getByUPE(user.getUserName());
        assertThat(user.getEmail()).isEqualTo(created.getEmail());
    }

    @Test
    void createWithoutEmail() {
        User user = new User();
        user.setUserName("valid1");
        user.setLastName("valid");
        user.setPassword("valid");
        user.setName("valid1");
        Region r = new Region();
        r.setId(Long.valueOf(1));
        user.setRegion(r);
        user.setLastName("valid1");
        user.setPhoneNumber("+177777777778");

        try {
            userService.create(user);
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    void createWithTakenEmail() {
        User user = new User();
        user.setEmail("valid1@mail.ru");
        user.setUserName("valid2");
        user.setLastName("valid");
        user.setName("valid1");
        user.setPassword("valid");
        Region r = new Region();
        r.setId(Long.valueOf(1));
        user.setRegion(r);
        user.setLastName("valid1");
        user.setPhoneNumber("+277777777778");

        try {
            userService.create(user);
        } catch (NoSuchElementException e) {

        }
    }

    @Test
    void updateEmail() {
        User user = new User();
        user.setEmail("valid1@mail.ru");
        user.setUserName("valid1");
        user.setLastName("valid");
        user.setName("valid1");
        user.setPassword("valid");
        Region r = new Region();
        r.setId(Long.valueOf(1));
        user.setRegion(r);
        user.setLastName("valid1");
        user.setPhoneNumber("+177777777778");

        userService.create(user);


    }
}
