package entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;

public class Car implements TransferObject, Groupable, Embedded, ContractAddition {

    private int carID;
    private String plate;
    private String brand;
    private String model;
    private int year;
    private int doors;
    private int passengers;
    private String description;
    private int mileage;
    private Agency currentAgency;
    private boolean available = true;
    private BigDecimal bookingPrice;
    private final Category category;

    public int getCategoryID() {
        return category.getId();
    }

    public int getCurrentAgencyID() {
        return currentAgency.getId();
    }

    public String getCurrentAgencyCode() {
        return currentAgency.getAgencyCode();
    }

    public static class Builder {
        private int carID;
        private String plate;
        private String brand;
        private String model;
        private int year;
        private int doors;
        private int passengers;
        private String description;
        private int mileage;
        private Agency currentAgency;
        private boolean available = true;
        private BigDecimal bookingPrice;
        private Category category;


        public Builder carID(int value) {
            carID = value;
            return this;
        }

        public Builder plate(String value) {
            plate = value;
            return this;
        }

        public Builder brand(String value) {
            brand = value;
            return this;
        }

        public Builder model(String value) {
            model = value;
            return this;
        }

        public Builder year(int value) {
            year = value;
            return this;
        }

        public Builder doors(int value) {
            doors = value;
            return this;
        }

        public Builder passengers(int value) {
            passengers = value;
            return this;
        }

        public Builder description(String value) {
            description = value;
            return this;
        }

        public Builder mileage(int value) {
            mileage = value;
            return this;
        }

        public Builder currentAgency(Agency agency) {
            currentAgency = agency;
            return this;
        }

        public Builder available(boolean value) {
            available = value;
            return this;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder bookingPrice(BigDecimal value) {
            bookingPrice = value;
            return this;
        }

        public Car build() {
            return new Car(this);
        }

    }

    private Car(Builder builder) {
        carID           = builder.carID;
        plate           = builder.plate;
        brand           = builder.brand;
        model           = builder.model;
        year            = builder.year;
        doors           = builder.doors;
        passengers      = builder.passengers;
        description     = builder.description;
        mileage         = builder.mileage;
        currentAgency   = builder.currentAgency;
        available       = builder.available;
        bookingPrice    = builder.bookingPrice;
        category        = builder.category;
    }

    @Override
    public String toStringField() {
        return "[" + plate + "] " +
                brand + " " + model + "(" + year + ")" +
                " - " + doors + " porte / " + passengers + " passeggeri";
    }

    public int getId() {
        return carID;
    }

    public void setId(int id) {
        this.carID = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public Agency getCurrentAgency() {
        return currentAgency;
    }

    public void setCurrentAgency(Agency currentAgency) {
        this.currentAgency = currentAgency;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public BigDecimal getBookingPrice() {
        return bookingPrice;
    }

    public void setBookingPrice(BigDecimal bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String getCategoryName() {
        return category.getName();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Car)) return false;

        Car that  = (Car) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(carID, that.carID)
                .append(plate, that.plate)
                .append(brand, that.brand)
                .append(model, that.model)
                .append(year, that.year)
                .append(doors, that.doors)
                .append(passengers, that.passengers)
                .append(description, that.description)
                .append(mileage, that.mileage)
                .append(currentAgency, that.currentAgency)
                .append(bookingPrice, that.bookingPrice)
                .append(category, that.category);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(carID)
                .append(plate)
                .append(brand)
                .append(model)
                .append(year)
                .append(doors)
                .append(passengers)
                .append(description)
                .append(mileage)
                .append(currentAgency)
                .append(bookingPrice)
                .append(category);
        return builder.hashCode();
    }
}


