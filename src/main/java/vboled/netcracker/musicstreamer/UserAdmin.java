package vboled.netcracker.musicstreamer;

import org.springframework.stereotype.Component;
import vboled.netcracker.musicstreamer.model.user.Role;
import vboled.netcracker.musicstreamer.model.user.User;

import java.time.LocalDateTime;

@Component
public class UserAdmin extends User {

//    public UserAdmin() {
//        this.setId(Long.valueOf(1));
//        this.setUserName("admin");
//        this.setPassword("$2y$12$IWmpDwOU9N0i2CICEy/1suJbCjZVfRXmcYk0/qNr/nWIhKEt1taSK");
//        this.setName("tLName1");
//        this.setLastName("tName1");
//        this.setRegionID(Long.valueOf(1));
//        this.setEmail("tEmail1@edu.ru");
//        this.setPhoneNumber("79998881122");
//        this.setPlayListID(Long.valueOf(1));
//        this.setRole(Role.ADMIN);
//    }

    public UserAdmin() {
        this.setId(Long.valueOf(3));
        this.setUserName("owner");
        this.setPassword("$2a$12$MCj.Ur2zrNWSW5I7i.xPGOFDB.WzBTqkz0ejMIWXI0pJ4nncZcLDO");
        this.setName("tLName2");
        this.setLastName("tName2");
        this.setRegionID(Long.valueOf(2));
        this.setEmail("tEmafreil2@edu.ru");
        this.setPhoneNumber("7999877773");
        this.setRole(Role.OWNER);
    }
}