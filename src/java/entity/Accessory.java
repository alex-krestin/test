package entity;


public class Accessory extends TransferObject {
    private int id;
    private Category category;
    private String title;
    private String description;
    private String inventoryCode;
    private Agency agency;
    private boolean available = true;


    public Accessory(int id, Category category, String title, String description, String inventoryCode, Agency agency) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.description = description;
        this.inventoryCode = inventoryCode;
        this.agency = agency;
    }

    public Accessory(String title, String description, String inventoryCode, Category category, Agency agency) {
        this.title = title;
        this.description = description;
        this.inventoryCode = inventoryCode;
        this.category = category;
        this.agency = agency;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInventoryCode() {
        return inventoryCode;
    }

    public void setInventoryCode(String inventoryCode) {
        this.inventoryCode = inventoryCode;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Accessory)) return false;

        Accessory accessory = (Accessory) o;

        if (category != null ? !category.equals(accessory.category) : accessory.category != null) return false;
        if (title != null ? !title.equals(accessory.title) : accessory.title != null) return false;
        if (description != null ? !description.equals(accessory.description) : accessory.description != null)
            return false;
        if (inventoryCode != null ? !inventoryCode.equals(accessory.inventoryCode) : accessory.inventoryCode != null)
            return false;
        return !(agency != null ? !agency.equals(accessory.agency) : accessory.agency != null);
    }

}
