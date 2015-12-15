package entity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SearchRequest<T> implements TransferObject {
    private T object;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    private Agency departureAgency;

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Agency getDepartureAgency() {
        return departureAgency;
    }

    public void setDepartureAgency(Agency departureAgency) {
        this.departureAgency = departureAgency;
    }


    public static class Builder<T> {
        private T object;
        private LocalDate departureDate;
        private LocalTime departureTime;
        private LocalDate arrivalDate;
        private LocalTime arrivalTime;
        private Agency departureAgency;

        public Builder object(T object) {
            this.object = object;
            return this;
        }

        public Builder departureDate(LocalDate value) {
            departureDate = value;
            return this;
        }

        public Builder departureTime(LocalTime value) {
            departureTime = value;
            return this;
        }

        public Builder arrivalDate(LocalDate value) {
            arrivalDate = value;
            return this;
        }

        public Builder arrivalTime(LocalTime value) {
            arrivalTime = value;
            return this;
        }

        public Builder departureAgency(Agency agency) {
            departureAgency = agency;
            return this;
        }

        public SearchRequest build() {
            return new SearchRequest<>(this);
        }
    }

    private SearchRequest(Builder<T> builder) {
        object = builder.object;
        departureDate = builder.departureDate;
        departureTime = builder.departureTime;
        arrivalDate = builder.arrivalDate;
        arrivalTime = builder.arrivalTime;
        departureAgency = builder.departureAgency;
    }

    public LocalDateTime getDepartureDateTime() {
        return LocalDateTime.of(departureDate, departureTime);
    }

    public LocalDateTime getArrivalDateTime() {
        return LocalDateTime.of(arrivalDate, arrivalTime);
    }
}

