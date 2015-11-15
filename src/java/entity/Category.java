package entity;


public class Category extends TransferObject {
    private int id;
    private String name;
    private String description;

    public Category(int categoryID, String categoryName, String description) {
        this.id = categoryID;
        this.name = categoryName;
        this.description = description;
    }

    public Category(String categoryName, String description) {
        this.name = categoryName;
        this.description = description;
    }

    public Category(int categoryID, String categoryName) {
        this.id = categoryID;
        this.name = categoryName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        if (id != category.id) return false;
        if (!name.equals(category.name)) return false;
        return !(description != null ? !description.equals(category.description) : category.description != null);
    }

    @Override
    public String toString() {
        return name;
    }
}
