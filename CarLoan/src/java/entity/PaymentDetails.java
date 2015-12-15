package entity;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;

public class PaymentDetails {
    private BigDecimal VAT;
    private BigDecimal discount;
    private BigDecimal franchise;
    private BigDecimal bookingDeposit;
    private boolean paidBookingDeposit;
    private BigDecimal franchiseDeposit;
    private boolean paidFranchiseDeposit;

    public PaymentDetails() {
        // default values only for demonstration purpose
        // TODO Enable values choice
        VAT = BigDecimal.valueOf(22);
        discount = BigDecimal.ZERO;
        franchise = BigDecimal.valueOf(500.00);
    }

    public BigDecimal getVAT() {
        return VAT;
    }

    public void setVAT(BigDecimal VAT) {
        this.VAT = VAT;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getBookingDeposit() {
        return bookingDeposit;
    }

    public void setBookingDeposit(BigDecimal bookingDeposit) {
        this.bookingDeposit = bookingDeposit;
    }

    public boolean isPaidBookingDeposit() {
        return paidBookingDeposit;
    }

    public void setPaidBookingDeposit(boolean paidBookingDeposit) {
        this.paidBookingDeposit = paidBookingDeposit;
    }

    public BigDecimal getFranchiseDeposit() {
        return franchiseDeposit;
    }

    public void setFranchiseDeposit(BigDecimal franchiseDeposit) {
        this.franchiseDeposit = franchiseDeposit;
    }

    public boolean isPaidFranchiseDeposit() {
        return paidFranchiseDeposit;
    }

    public void setPaidFranchiseDeposit(boolean paidFranchiseDeposit) {
        this.paidFranchiseDeposit = paidFranchiseDeposit;
    }

    public BigDecimal getFranchise() {
        return franchise;
    }

    public void setFranchise(BigDecimal franchise) {
        this.franchise = franchise;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof PaymentDetails)) return false;

        PaymentDetails that  = (PaymentDetails) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(VAT, that.VAT)
                .append(discount, that.discount)
                .append(franchise, that.franchise)
                .append(bookingDeposit, that.bookingDeposit)
                .append(paidBookingDeposit, that.paidBookingDeposit)
                .append(franchiseDeposit, that.franchiseDeposit)
                .append(paidFranchiseDeposit, that.paidFranchiseDeposit);
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(VAT)
                .append(discount)
                .append(franchise)
                .append(bookingDeposit)
                .append(paidBookingDeposit)
                .append(franchiseDeposit)
                .append(paidFranchiseDeposit);
        return builder.hashCode();
    }
}
