package it.uniba.carloan.entity;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Accessory implements TransferObject, Groupable, Embedded, Identified<Integer>, ContractAddition {
    private Integer id;
    private String title;
    private String description;
    private String inventoryCode;
    private Agency currentAgency;

    private Category category;
    private boolean available;

    public static class Builder {
        private Integer id;
        private String title;
        private String description;
        private String inventoryCode;
        private Agency currentAgency;
        private Category category;
        private boolean available = true;

        public Builder id(Integer value) {
            id = value;
            return this;
        }

        public Builder title(String value) {
            title = value;
            return this;
        }

        public Builder description(String value) {
            description = value;
            return this;
        }

        public Builder inventoryCode(String value) {
            inventoryCode = value;
            return this;
        }

        public Builder currentAgency(Agency agency) {
            currentAgency = agency;
            return this;
        }


        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder available(boolean value) {
            available = value;
            return this;
        }

        public Accessory build() {
            return new Accessory(this);
        }
    }

    private Accessory(Builder builder) {
        id              = builder.id;
        title           = builder.title;
        description     = builder.description;
        inventoryCode   = builder.inventoryCode;
        currentAgency   = builder.currentAgency;
        category        = builder.category;
        available       = builder.available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Agency getCurrentAgency() {
        return currentAgency;
    }

    public void setCurrentAgency(Agency currentAgency) {
        this.currentAgency = currentAgency;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toStringField() {
        return title;
    }

    public int getCategoryID() {
        return category.getId();
    }

    @Override
    public String getCategoryName() {
        return category.getName();
    }

    public int getCurrentAgencyID() {
        return currentAgency.getId();
    }

    public String getCurrentAgencyCode() {
        return currentAgency.getAgencyCode();
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Accessory)) return false;

        Accessory that  = (Accessory) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(category, that.category)
                .append(title, that.title)
                .append(description, that.description)
                .append(inventoryCode, that.inventoryCode);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(category)
                .append(title)
                .append(description)
                .append(inventoryCode);
        return builder.hashCode();
    }

}
