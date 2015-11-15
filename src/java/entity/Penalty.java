package entity;


public class Penalty extends TransferObject {
    private int id;
    private String title;
    private String description;
    private Category category;
    private boolean available = true;

    public Penalty(int penaltyID) {
        this.id = penaltyID;
    }

    public Penalty(String title, String description, Category category) {
        this.title = title;
        this.description = description;
        this.category = category;
    }

    public Penalty(int penaltyID, String title, String description, Category category) {
        this.id = penaltyID;
        this.title = title;
        this.description = description;
        this.category = category;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Penalty)) return false;

        Penalty penalty = (Penalty) o;

        if (title != null ? !title.equals(penalty.title) : penalty.title != null) return false;
        if (description != null ? !description.equals(penalty.description) : penalty.description != null)
            return false;
        return  !(category != null ? !category.equals(penalty.category) : penalty.category != null);
    }
}
