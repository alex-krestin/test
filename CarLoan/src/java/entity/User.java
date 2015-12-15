package entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class User implements TransferObject {
    private int id;
    private String name;
    private String surname;
    private String username;
    private String accountType;
    private String password;
    private boolean accessGranted;
    private Agency agency;

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

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public String getAgencyCity() {
        return agency.getCity();
    }

    public int getAgencyID() {
        return agency.getId();
    }

    public String getAgencyCode() {
        return agency.getAgencyCode();
    }

    public String getAgencyPhoneNumber() {
        return agency.getPhoneNumber();
    }

    public String getAgencyFaxNumber() {
        return agency.getFaxNumber();
    }

    public String getAgencyAddress() {
        return agency.getAddress();
    }

    public String getAgencyEmail() {
        return agency.getEmail();
    }

    public static class Builder {
        private int id;
        private String name;
        private String surname;
        private String username;
        private String accountType;
        private String password;
        private boolean accessGranted = true;
        private Agency agency;

        public Builder id(int value) {
            id = value;
            return this;
        }

        public Builder name(String value) {
            name = value;
            return this;
        }

        public Builder surname(String value) {
            surname = value;
            return this;
        }

        public Builder username(String value) {
            username = value;
            return this;
        }

        public Builder accountType(String value) {
            accountType = value;
            return this;
        }

        public Builder password(String value) {
            password = value;
            return this;
        }

        public Builder accessGranted(boolean value) {
            accessGranted = value;
            return this;
        }

        public Builder agency(Agency agency) {
            this.agency = agency;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    private User(Builder builder) {
        id            = builder.id;
        name          = builder.name;
        surname       = builder.surname;
        username      = builder.username;
        accountType   = builder.accountType;
        password      = builder.password;
        accessGranted = builder.accessGranted;
        agency        = builder.agency;
    }



    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User that  = (User) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(id, that.id)
                .append(name, that.name)
                .append(surname, that.surname)
                .append(username, that.username)
                .append(accountType, that.accountType)
                .append(agency, that.agency);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(id)
                .append(name)
                .append(surname)
                .append(username)
                .append(accountType)
                .append(agency);
        return builder.hashCode();
    }

}

