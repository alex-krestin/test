package it.uniba.carloan.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDate;


public class Client implements TransferObject, Identified<Integer> {
    private Integer id;
    private String name;
    private String surname;
    private String sex;
    private String fiscalCode;
    private LocalDate birthday;
    private String phoneNumber;
    private String comment;

    public static class Builder {
        private Integer id;
        private String name;
        private String surname;
        private String sex;
        private String fiscalCode;
        private LocalDate birthday;
        private String phoneNumber;
        private String comment;

        public Builder id(Integer value) {
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

        public Builder sex(String value) {
            sex = value;
            return this;
        }

        public Builder fiscalCode(String value) {
            fiscalCode = value;
            return this;
        }

        public Builder birthday(LocalDate value) {
            birthday = value;
            return this;
        }

        public Builder phoneNumber(String value) {
            phoneNumber = value;
            return this;
        }

        public Builder comment(String value) {
            comment = value;
            return this;
        }

        public Client build() {
            return new Client(this);
        }
    }

    private Client(Builder builder) {
        id          = builder.id;
        name        = builder.name;
        surname     = builder.surname;
        sex         = builder.sex;
        fiscalCode  = builder.fiscalCode;
        birthday    = builder.birthday;
        phoneNumber = builder.phoneNumber;
        comment     = builder.comment;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Client)) return false;

        Client that  = (Client) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(id, that.id)
                .append(name, that.name)
                .append(surname, that.surname)
                .append(sex, that.sex)
                .append(fiscalCode, that.fiscalCode)
                .append(birthday, that.birthday)
                .append(phoneNumber, that.phoneNumber)
                .append(comment, that.comment);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(id)
                .append(name)
                .append(surname)
                .append(sex)
                .append(fiscalCode)
                .append(birthday)
                .append(phoneNumber)
                .append(comment);
        return builder.hashCode();
    }

}
