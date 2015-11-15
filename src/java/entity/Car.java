package entity;

public class Car extends TransferObject {
    private int id;
    private String plate;
    private String brand;
    private String model;
    private int year;
    private Category category;
    private int doors;
    private int passengers;
    private String description;
    private int mileage;
    private Agency currentAgency;
    boolean available = true;

    public Car(int carID, String brand, String model, int year, String plate, Category category, int doors,
               int passengers, String description, int mileage, Agency currentAgency) {
        this.id = carID;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.plate = plate;
        this.category = category;
        this.doors = doors;
        this.passengers = passengers;
        this.description = description;
        this.mileage = mileage;
        this.currentAgency = currentAgency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
}


