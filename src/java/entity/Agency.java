package entity;


import java.util.Objects;

/**
 *
 */
public class Agency extends TransferObject {
    private int id;
    private String agencyCode;
    private String city;
    private String address;
    private String phoneNumber;
    private String faxNumber;
    private String email;
    private boolean active = true;

    public Agency() {}

    public Agency(int id, String agencyCode, String city, String address, String phoneNumber, String faxNumber, String email) {
        this.id = id;
        this.agencyCode = agencyCode;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
        this.email = email;
    }

    public Agency(String agencyCode, String city, String address, String phoneNumber, String faxNumber, String email) {
        this.agencyCode = agencyCode;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
        this.email = email;
    }

    public Agency(int id) {
        this.id = id;
    }

    public int getAgencyID() {
        return id;
    }

    public void setAgencyID(int id) {
        this.id = id;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return '[' + agencyCode + ']' +
                " - " + city +
                ", " + address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Agency)) return false;

        Agency agency = (Agency) o;

        if (id != agency.id) return false;
        if (agencyCode != null ? !agencyCode.equals(agency.agencyCode) : agency.agencyCode != null) return false;
        if (city != null ? !city.equals(agency.city) : agency.city != null) return false;
        if (address != null ? !address.equals(agency.address) : agency.address != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(agency.phoneNumber) : agency.phoneNumber != null) return false;
        if (faxNumber != null ? !faxNumber.equals(agency.faxNumber) : agency.faxNumber != null) return false;
        return !(email != null ? !email.equals(agency.email) : agency.email != null);

    }
}
