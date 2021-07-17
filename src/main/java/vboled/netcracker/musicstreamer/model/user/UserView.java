package vboled.netcracker.musicstreamer.model.user;

import lombok.Data;

@Data
public class UserView {

    public UserView(User user) {
        this.userName = user.getUserName();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.regionName = "Implement_this";
    }

    private String userName;

    private String name;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String regionName;
}
