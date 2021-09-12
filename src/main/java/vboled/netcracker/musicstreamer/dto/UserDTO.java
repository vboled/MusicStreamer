package vboled.netcracker.musicstreamer.dto;

import vboled.netcracker.musicstreamer.model.user.Role;

import java.time.LocalDateTime;
import java.util.Date;

public class UserDTO {
    private int id;

    private String userName;

    private String password;

    private String name;

    private String lastName;

    private int regionID = -1;

    private String email;

    private String phoneNumber;

    private LocalDateTime createDate;

    private Date lastLoginDate;

    private int playListID = -1;

    private Role role;

    public int getId() {
        return id;
    }

    public UserDTO setId(int id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserDTO setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public int getRegionID() {
        return regionID;
    }

    public UserDTO setRegionID(int regionID) {
        this.regionID = regionID;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public UserDTO setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public UserDTO setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
        return this;
    }

    public int getPlayListID() {
        return playListID;
    }

    public UserDTO setPlayListID(int playListID) {
        this.playListID = playListID;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public UserDTO setRole(Role role) {
        this.role = role;
        return this;
    }
}
