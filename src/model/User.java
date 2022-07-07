package model;

import sun.security.util.Password;

import java.time.LocalDate;

public class User {
    private int userId;
    private String userName;
    private Password pw;
    private String email;
    private String contact;
    private LocalDate regDate;

    public User() {
    }

    public User(int userId, String userName, Password pw, String email, String contact, LocalDate regDate) {
        this.userId = userId;
        this.userName = userName;
        this.pw = pw;
        this.email = email;
        this.contact = contact;
        this.regDate = regDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Password getPw() {
        return pw;
    }

    public void setPw(Password pw) {
        this.pw = pw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", pw=" + pw +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", regDate=" + regDate +
                '}';
    }
}
