package vboled.netcracker.musicstreamer.model.user;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

@Data
public class UserAdminView {

    public UserAdminView(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.regionID = user.getRegionID();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.createDate = user.getCreateDate();
        this.lastLoginDate = user.getLastLoginDate();
        this.playListID = user.getPlayListID();
        this.role = user.getRole();
    }

    private int id;

    private String userName;

    private String name;

    private String lastName;

    private int regionID;

    private String email;

    private String phoneNumber;

    private LocalDateTime createDate;

    private Date lastLoginDate;

    private int playListID;

    private Role role;

}
