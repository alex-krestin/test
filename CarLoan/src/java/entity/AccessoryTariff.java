package entity;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AccessoryTariff implements TransferObject, Tariff, Embedded {
    private int tariffID;
    private Category category;
    private BigDecimal dailyPrice;
    private int maxDays;
    private LocalDate fromDate;
    private LocalDate toDate;


    public static class Builder {
        private int tariffID;
        private Category category;
        private BigDecimal dailyPrice;
        private int maxDays;
        private LocalDate fromDate;
        private LocalDate toDate;

        public Builder tariffID(int value) {
            tariffID = value;
            return this;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder dailyPrice(BigDecimal value) {
            dailyPrice = value;
            return this;
        }

        public Builder maxDays(int value) {
            maxDays = value;
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

        public AccessoryTariff build() {
            return new AccessoryTariff(this);
        }
    }


    private AccessoryTariff(Builder builder) {
        tariffID   = builder.tariffID;
        category   = builder.category;
        dailyPrice = builder.dailyPrice;
        maxDays    = builder.maxDays;
        fromDate   = builder.fromDate;
        toDate     = builder.toDate;
    }


    @Override
    public String toStringField() {
        return "todo";
    }

    @Override
    public int getTariffID() {
        return tariffID;
    }

    public void setTariffID(int tariffID) {
        this.tariffID = tariffID;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(BigDecimal dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public int getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(int maxDays) {
        this.maxDays = maxDays;
    }

    @Override
    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    @Override
    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public int getCategoryID() {
        return category.getId();
    }

    public String getCategoryName() {
        return category.getName();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof AccessoryTariff)) return false;

        AccessoryTariff that  = (AccessoryTariff) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(category, that.category)
                .append(dailyPrice, that.dailyPrice)
                .append(maxDays, that.maxDays)
                .append(fromDate, that.fromDate)
                .append(toDate, that.toDate);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(category)
                .append(dailyPrice)
                .append(maxDays)
                .append(fromDate)
                .append(toDate);
        return builder.hashCode();
    }
}
