package it.uniba.carloan.entity;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Category implements TransferObject, Embedded, Identified<Integer> {

    private final Integer id;
    private final String name;
    private final String description;

    public static class Builder {
        private Integer id;
        private final String name;
        private String description;

        public Builder(String name) {
            this.name = name;
        }

        public Builder id(Integer value) {
            id = value;
            return this;
        }

        public Builder description(String value) {
            description = value;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }

    private Category(Builder builder) {
        id = builder.id;
        name = builder.name;
        description = builder.description;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category that  = (Category) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(name, that.name)
                .append(description, that.description);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(name)
                .append(description);
        return builder.hashCode();
    }

    @Override
    public String toStringField() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
