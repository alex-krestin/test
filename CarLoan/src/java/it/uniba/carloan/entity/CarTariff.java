package it.uniba.carloan.entity;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CarTariff implements TransferObject, Tariff, Identified<Integer> {
    private Integer tariffID;
    Category category;
    private BigDecimal dailyPrice;
    private BigDecimal weeklyPrice;
    private BigDecimal mileagePrice;
    private BigDecimal freeMileagePrice;
    private LocalDate fromDate;
    private LocalDate toDate;

    public int getCategoryID() {
        return category.getId();
    }

    public String getCategoryName() {
        return category.getName();
    }

    public static class Builder {
        private Integer tariffID;
        Category category;
        private BigDecimal dailyPrice;
        private BigDecimal weeklyPrice;
        private BigDecimal mileagePrice;
        private BigDecimal freeMileagePrice;
        private LocalDate fromDate;
        private LocalDate toDate;

        public Builder tariffID(Integer value) {
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

        public Builder weeklyPrice(BigDecimal value) {
            weeklyPrice = value;
            return this;
        }

        public Builder mileagePrice(BigDecimal value) {
            mileagePrice = value;
            return this;
        }

        public Builder freeMileagePrice(BigDecimal value) {
            freeMileagePrice = value;
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

        public CarTariff build() {
            return new CarTariff(this);
        }
    }

    private CarTariff(Builder builder) {
        tariffID            = builder.tariffID;
        category            = builder.category;
        dailyPrice          = builder.dailyPrice;
        weeklyPrice         = builder.weeklyPrice;
        mileagePrice        = builder.mileagePrice;
        freeMileagePrice    = builder.freeMileagePrice;
        fromDate            = builder.fromDate;
        toDate              = builder.toDate;
    }


    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    @Override
    public Integer getId() {
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

    public BigDecimal getWeeklyPrice() {
        return weeklyPrice;
    }

    public void setWeeklyPrice(BigDecimal weeklyPrice) {
        this.weeklyPrice = weeklyPrice;
    }

    public BigDecimal getMileagePrice() {
        return mileagePrice;
    }

    public void setMileagePrice(BigDecimal mileagePrice) {
        this.mileagePrice = mileagePrice;
    }

    public BigDecimal getFreeMileagePrice() {
        return freeMileagePrice;
    }

    public void setFreeMileagePrice(BigDecimal freeMileagePrice) {
        this.freeMileagePrice = freeMileagePrice;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof CarTariff)) return false;

        CarTariff that  = (CarTariff) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(tariffID, that.tariffID)
                .append(category, that.category)
                .append(dailyPrice, that.dailyPrice)
                .append(weeklyPrice, that.weeklyPrice)
                .append(mileagePrice, that.mileagePrice)
                .append(freeMileagePrice, that.freeMileagePrice)
                .append(fromDate, that.fromDate)
                .append(toDate, that.toDate);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(tariffID)
                .append(category)
                .append(dailyPrice)
                .append(weeklyPrice)
                .append(mileagePrice)
                .append(freeMileagePrice)
                .append(fromDate)
                .append(toDate);
        return builder.hashCode();
    }
}
