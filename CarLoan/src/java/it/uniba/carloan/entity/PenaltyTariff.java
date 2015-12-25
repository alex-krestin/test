package it.uniba.carloan.entity;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PenaltyTariff implements TransferObject, Tariff, Identified<Integer> {

    private Integer tariffID;
    private Penalty penalty;
    private BigDecimal price;
    private LocalDate fromDate;
    private LocalDate toDate;

    @Override
    public Integer getId() {
        return tariffID;
    }

    public void setTariffID(Integer tariffID) {
        this.tariffID = tariffID;
    }

    public Penalty getPenalty() {
        return penalty;
    }

    public void setPenalty(Penalty penalty) {
        this.penalty = penalty;
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

    public int getPenaltyID() {
        return penalty.getId();
    }


    public static class Builder {
        private Integer tariffID;
        private Penalty penalty;
        private BigDecimal price;
        private LocalDate fromDate;
        private LocalDate toDate;

        public Builder tariffID(Integer value) {
            tariffID = value;
            return this;
        }

        public Builder penalty(Penalty penalty) {
            this.penalty = penalty;
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

        public PenaltyTariff build() {
            return new PenaltyTariff(this);
        }
    }


    private PenaltyTariff(Builder builder) {
        tariffID = builder.tariffID;
        penalty  = builder.penalty;
        price    = builder.price;
        fromDate = builder.fromDate;
        toDate   = builder.toDate;
    }

    public String getPenaltyTitle() {
        return penalty.getTitle();
    }

    public String getPenaltyCategoryName() {
        return penalty.getCategoryName();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof PenaltyTariff)) return false;

        PenaltyTariff that  = (PenaltyTariff) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(tariffID, that.tariffID)
                .append(penalty, that.penalty)
                .append(price, that.price)
                .append(fromDate, that.fromDate)
                .append(toDate, that.toDate);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(tariffID)
                .append(penalty)
                .append(price)
                .append(fromDate)
                .append(toDate);
        return builder.hashCode();
    }

}
