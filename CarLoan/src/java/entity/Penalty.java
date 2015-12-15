package entity;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Penalty implements TransferObject, Groupable, Embedded, ContractAddition {
    private int id;
    private String title;
    private String description;
    private Category category;
    private boolean available;

    @Override
    public String toStringField() {
        return title;
    }

    public String getCategoryName() {
        return category.getName();
    }

    public int getCategoryID() {
        return category.getId();
    }

    public static class Builder {
        private int id;
        private String title;
        private String description;
        private Category category;
        private final boolean available = true;

        public Builder id(int value) {
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

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Penalty build() {
            return new Penalty(this);
        }
    }

    private Penalty(Builder builder) {
        id = builder.id;
        title = builder.title;
        description = builder.description;
        category = builder.category;
        available = builder.available;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }



    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Penalty)) return false;

        Penalty that  = (Penalty) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(id, that.id)
                .append(title, that.title)
                .append(description, that.description)
                .append(category, that.category);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(id)
                .append(title)
                .append(description)
                .append(category);
        return builder.hashCode();
    }

}
