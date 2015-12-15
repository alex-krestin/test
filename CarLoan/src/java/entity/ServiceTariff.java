package entity;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ServiceTariff implements TransferObject, Tariff, Embedded {

    private int tariffID;
    private Service service;
    private BigDecimal price;
    private LocalDate fromDate;
    private LocalDate toDate;

    public int getTariffID() {
        return tariffID;
    }

    public void setTariffID(int tariffID) {
        this.tariffID = tariffID;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    @Override
    public String toStringField() {
        return service.toStringField();
    }

    public static class Builder {
        private int tariffID;
        private Service service;
        private BigDecimal price;
        private LocalDate fromDate;
        private LocalDate toDate;

        public Builder tariffID(int value) {
            tariffID = value;
            return this;
        }

        public Builder service(Service service) {
            this.service = service;
            return this;
        }

        public Builder price(BigDecimal value) {
            price = value;
            return this;
        }

        public Builder fromDate(LocalDate value) {
            fromDate = value;
            return this;
        }

        public Builder toDate(LocalDate value) {
            toDate = value;
            return this;
        }

        public ServiceTariff build() {
            return new ServiceTariff(this);
        }
    }

    private ServiceTariff(Builder builder) {
        tariffID = builder.tariffID;
        service  = builder.service;
        price    = builder.price;
        fromDate = builder.fromDate;
        toDate   = builder.toDate;
    }

    public int getServiceID() {
        return service.getId();
    }

    public String getServiceName() {
        return service.getTitle();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof ServiceTariff)) return false;

        ServiceTariff that  = (ServiceTariff) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(tariffID, that.tariffID)
                .append(service, that.service)
                .append(price, that.price)
                .append(fromDate, that.fromDate)
                .append(toDate, that.toDate);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(tariffID)
                .append(service)
                .append(price)
                .append(fromDate)
                .append(toDate);
        return builder.hashCode();
    }


}
