package entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Service implements TransferObject, Embedded, ContractAddition {
    private int id;
    private String title;
    private String description;
    private boolean available;

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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }


    public static class Builder {
        private int id;
        private String title;
        private String description;
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

        public Service build() {
            return new Service(this);
        }
    }

    private Service(Builder builder) {
        id = builder.id;
        title = builder.title;
        description = builder.description;
        available = builder.available;
    }



    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Service)) return false;

        Service that  = (Service) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(id, that.id)
                .append(title, that.title)
                .append(description, that.description);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(id)
                .append(title)
                .append(description);
        return builder.hashCode();
    }

    @Override
    public String toStringField() {
        return title;
    }

}
