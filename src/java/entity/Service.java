package entity;

import java.util.Currency;
import java.util.Date;


public class Service extends TransferObject {
    private int id;
    private String title;
    private String description;
    private boolean available = true;

    public Service(int serviceID) {
        this.id = serviceID;
    }

    public Service(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Service(int serviceID, String title, String description) {
        this.id = serviceID;
        this.title = title;
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service)) return false;

        Service service = (Service) o;

        if (id != service.id) return false;
        if (title != null ? !title.equals(service.title) : service.title != null) return false;
        return !(description != null ? !description.equals(service.description) : service.description != null);

    }
}
