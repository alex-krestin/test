package entity;

import utility.Constants;

import java.util.Date;


public class Client extends TransferObject {
    private int id;
    private String name;
    private String surname;
    private String sex;
    private String fiscalCode;
    private Date birthday;
    private String phoneNumber;
    private String comment;

    public Client(int id) {
        this.id = id;
    }

    public Client(int id, String name, String surname, String sex, String fiscalCode, Date birthday, String phoneNumber, String comment) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.fiscalCode = fiscalCode;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.comment = comment;
    }

    public Client(String name, String surname, String sex, String fiscalCode, Date birthday, String phoneNumber, String comment) {
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.fiscalCode = fiscalCode;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.comment = comment;
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

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(Constants.Sex sex) {
        this.sex = sex.name();
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

        Client client = (Client) o;

        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (surname != null ? !surname.equals(client.surname) : client.surname != null) return false;
        if (sex != null ? !sex.equals(client.sex) : client.sex != null) return false;
        if (fiscalCode != null ? !fiscalCode.equals(client.fiscalCode) : client.fiscalCode != null) return false;
        if (birthday != null ? !birthday.equals(client.birthday) : client.birthday != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(client.phoneNumber) : client.phoneNumber != null) return false;
        return !(comment != null ? !comment.equals(client.comment) : client.comment != null);

    }
}
