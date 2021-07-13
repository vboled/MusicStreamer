package vboled.netcracker.musicstreamer.model.user;

import java.time.LocalDateTime;
import java.util.Date;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRegionID() {
        return regionID;
    }

    public void setRegionID(int regionID) {
        this.regionID = regionID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public int getPlayListID() {
        return playListID;
    }

    public void setPlayListID(int playListID) {
        this.playListID = playListID;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
