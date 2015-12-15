package entity;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Contract implements TransferObject {
    private int contractID;
    private int contractType; // 0 for preventivo, 1 for booking, 2 for exec
    private Client client;
    private ContractObject<Car> car;
    private List<ContractObject<Accessory>> accessories;
    private List<ContractObject<Service>> services;
    private List<ContractObject<Penalty>> penalties;
    private PaymentDetails paymentDetails;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    private Agency departureAgency;
    private Agency arrivalAgency;
    private User operator;

    private int tariffType; // 0 for daily, 1 for weekly
    private Integer mileage;
    private boolean freeMileageOption = false;
    private String notes;


    public Contract() {
        accessories = new ArrayList<>();
        services = new ArrayList<>();
        penalties = new ArrayList<>();
        paymentDetails = new PaymentDetails();
    }

    public int getContractID() {
        return contractID;
    }

    public void setContractID(int contractID) {
        this.contractID = contractID;
    }

    public int getContractType() {
        return contractType;
    }

    public void setContractType(int contractType) {
        this.contractType = contractType;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ContractObject<Car> getCar() {
        return car;
    }

    public void setCar(ContractObject<Car> car) {
        this.car = car;
    }

    public Collection<ContractObject<Accessory>> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<ContractObject<Accessory>> accessories) {
        this.accessories = accessories;
    }

    public Collection<ContractObject<Service>> getServices() {
        return services;
    }

    public void setServices(List<ContractObject<Service>> services) {
        this.services = services;
    }

    public List<ContractObject<Penalty>> getPenalties() {
        return penalties;
    }

    public void setPenalties(List<ContractObject<Penalty>> penalties) {
        this.penalties = penalties;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
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

    public Agency getArrivalAgency() {
        return arrivalAgency;
    }

    public void setArrivalAgency(Agency arrivalAgency) {
        this.arrivalAgency = arrivalAgency;
    }

    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    public int getTariffType() {
        return tariffType;
    }

    public void setTariffType(int tariffType) {
        this.tariffType = tariffType;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public boolean isFreeMileageOption() {
        return freeMileageOption;
    }

    public void setFreeMileageOption(boolean freeMileageOption) {
        this.freeMileageOption = freeMileageOption;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BigDecimal getVAT() {
        return paymentDetails.getVAT();
    }

    public BigDecimal getDiscount() {
        return paymentDetails.getDiscount();
    }



    public Temporal getDepartureDateTime() {
        try {
            return LocalDateTime.of(departureDate, departureTime);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Temporal getArrivalDateTime() {
        try {
            return LocalDateTime.of(arrivalDate, arrivalTime);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public BigDecimal getBasePrice() {
        BigDecimal result = BigDecimal.valueOf(0);

        try {
            CarTariff tariff = (CarTariff) car.getTariff();
            Long duration = ChronoUnit.HOURS.between(getDepartureDateTime(), getArrivalDateTime());

            BigDecimal coefficient;
            BigDecimal tariffBasePrice;

            if (tariffType == 0) {
                tariffBasePrice = tariff.getDailyPrice();
                coefficient = BigDecimal.valueOf(duration).divide(BigDecimal.valueOf(24), 0, RoundingMode.UP);
            } else {
                tariffBasePrice = tariff.getWeeklyPrice();
                coefficient = BigDecimal.valueOf(duration).divide(BigDecimal.valueOf(168), 0, RoundingMode.UP);
            }

            if (mileage != null) {
                result = tariffBasePrice.multiply(coefficient)
                        .add(BigDecimal.valueOf(mileage).multiply(tariff.getMileagePrice()));
            } else if (freeMileageOption) {
                result = tariffBasePrice.multiply(coefficient).add(tariff.getFreeMileagePrice());
            }
        } catch (NullPointerException e) {
            // Not sufficient data
        }

        return result.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getAccessoriesPrice() {
        BigDecimal result = BigDecimal.valueOf(0);

        if (accessories.size() > 0) {
            Long duration;
            try {
                duration = ChronoUnit.HOURS.between(getDepartureDateTime(), getArrivalDateTime());
            } catch (NullPointerException e) {
                return result;
            }

            BigDecimal days =  BigDecimal.valueOf(duration).divide(BigDecimal.valueOf(24), 0, RoundingMode.UP);

            for (ContractObject<Accessory> accessory: accessories) {
                AccessoryTariff tariff = (AccessoryTariff) accessory.getTariff();
                BigDecimal maxDays = BigDecimal.valueOf(tariff.getMaxDays());
                BigDecimal dailyPrice = tariff.getDailyPrice();

                if (days.compareTo(maxDays) == 1) {
                    result = result.add(maxDays.multiply(dailyPrice));
                }
                else {
                    result = result.add(days.multiply(dailyPrice));
                }
            }
        }

        return result.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getServicesPrice() {
        BigDecimal result = BigDecimal.valueOf(0);

        if (services.size() > 0) {
            for (ContractObject<Service> service: services) {
                ServiceTariff tariff = (ServiceTariff) service.getTariff();
                result = result.add(tariff.getPrice());
            }
        }

        return result.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTaxableAmount() {

        BigDecimal base = getBasePrice().add(getAccessoriesPrice()).add(getServicesPrice());
        BigDecimal discount = base.multiply(paymentDetails.getDiscount())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        return base.subtract(discount);
    }

    public BigDecimal getVatAmount() {
        BigDecimal taxable = getTaxableAmount();
        return taxable.multiply(paymentDetails.getVAT()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalAmount() {
        return getTaxableAmount().add(getVatAmount());
    }


    @Override
    public String toString() {
        return "Contract{" +
                "contractID=" + contractID +
                ", contractType=" + contractType +
                ", client=" + client +
                ", car=" + car +
                ", accessories=" + accessories +
                ", services=" + services +
                ", penalties=" + penalties +
                ", paymentDetails=" + paymentDetails +
                ", departureDate=" + departureDate +
                ", departureTime=" + departureTime +
                ", arrivalDate=" + arrivalDate +
                ", arrivalTime=" + arrivalTime +
                ", departureAgency=" + departureAgency +
                ", arrivalAgency=" + arrivalAgency +
                ", operator=" + operator +
                ", tariffType=" + tariffType +
                ", mileage=" + mileage +
                ", freeMileageOption=" + freeMileageOption +
                ", notes='" + notes + '\'' +
                '}';
    }

    public BigDecimal getFranchise() {
        return paymentDetails.getFranchise();
    }
}
