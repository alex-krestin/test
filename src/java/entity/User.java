package entity;

import utility.Constants;
import utility.MD5;

import java.util.Objects;


public class User extends TransferObject {
    private int id;
    private String name;
    private String surname;
    private String username;
    private String accountType;
    private String password;
    private boolean accessGranted;
    private Agency agency;

    public User() {}

    // uses for getUsers
    public User(int id, String name, String surname, String username, String password, String accountType, boolean accessGranted, Agency agency) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.accountType = accountType;
        this.password = password;
        this.accessGranted = accessGranted;
        this.agency = agency;
    }

    // uses for addUser
    public User(String name, String surname, String username, String password, String accountType, Boolean accessGranted, Agency agency) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.accountType = accountType;
        this.password = password;
        this.accessGranted = accessGranted;
        this.agency = agency;
    }

    // uses for updateUser
    public User(int id, String name, String surname, String username, String accountType, Agency agency) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.accountType = accountType;
        this.agency = agency;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccessGranted() {
        return accessGranted;
    }

    public void setAccessGranted(boolean accessGranted) {
        this.accessGranted = accessGranted;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", accountType='" + accountType + '\'' +
                ", password='" + password + '\'' +
                ", accessGranted=" + accessGranted +
                ", agencyID=" + agency.getAgencyID() +
                ", agencyCode=" + agency.getAgencyCode() +
                ", agencyCity=" + agency.getCity() +
                ", agencyAddress=" + agency.getAddress() +
                ", agencyTel=" + agency.getPhoneNumber() +
                ", agencyFax=" + agency.getFaxNumber() +
                ", agencyEmail=" + agency.getEmail() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (accountType != null ? !accountType.equals(user.accountType) : user.accountType != null) return false;
        return !(agency != null ? !agency.equals(user.agency) : user.agency != null);
    }
}

