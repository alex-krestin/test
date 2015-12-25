package it.uniba.carloan.entity;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 *
 */
public class Agency implements TransferObject, Embedded, Identified<Integer> {
    private Integer id;
    private String agencyCode;
    private String city;
    private String address;
    private String phoneNumber;
    private String faxNumber;
    private String email;
    private boolean active;

    public static class Builder {
        private Integer id;
        private String agencyCode;
        private String city;
        private String address;
        private String phoneNumber;
        private String faxNumber;
        private String email;
        private boolean active = true;

        public Builder id(Integer value) {
            id = value;
            return this;
        }

        public Builder agencyCode(String value) {
            agencyCode = value;
            return this;
        }

        public Builder city(String value) {
            city = value;
            return this;
        }

        public Builder address(String value) {
            address = value;
            return this;
        }

        public Builder phoneNumber(String value) {
            phoneNumber = value;
            return this;
        }

        public Builder faxNumber(String value) {
            faxNumber = value;
            return this;
        }

        public Builder email(String value) {
            email = value;
            return this;
        }

        public Builder active(boolean value) {
            active = value;
            return this;
        }

        public Agency build() {
            return new Agency(this);
        }
    }

    private Agency(Builder builder) {
        id           = builder.id;
        agencyCode   = builder.agencyCode;
        city         = builder.city;
        address      = builder.address;
        phoneNumber  = builder.phoneNumber;
        faxNumber    = builder.faxNumber;
        email        = builder.email;
        active       = builder.active;
    }


    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public String toStringField() {
        return '[' + agencyCode + ']' +
                " - " + city +
                ", " + address;
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Agency)) return false;

        Agency that  = (Agency) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(agencyCode, that.agencyCode)
                .append(city, that.city)
                .append(address, that.address)
                .append(phoneNumber, that.phoneNumber)
                .append(faxNumber, that.faxNumber)
                .append(email, that.email);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(agencyCode)
                .append(city)
                .append(address)
                .append(phoneNumber)
                .append(faxNumber)
                .append(email);
        return builder.hashCode();
    }
}
