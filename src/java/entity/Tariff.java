package entity;


import java.util.Currency;
import java.util.Date;

public class Tariff {
    private Type type;
    private int id;
    private int referenceID;
    private String referenceName;
    private Currency fixedPrice; //Decimal(19,4) in MySql
    private Currency dailyPrice;
    private Currency weeklyPrice;
    private Currency mileagePrice;
    private Currency freeMileagePrice;
    private int maxDays;
    private Date fromDate; // datetime in Mysql
    private Date toDate = null;

    public enum Type {
        ACCESSORY,
        CAR,
        PENALTY,
        SERVICE
    }

    public Tariff(int referenceID, Currency dailyPrice, Currency weeklyPrice, Currency mileagePrice,
                  Currency freeMileagePrice, Date fromDate, Date toDate) { //addCar
        this.referenceID = referenceID;
        this.dailyPrice = dailyPrice;
        this.weeklyPrice = weeklyPrice;
        this.mileagePrice = mileagePrice;
        this.freeMileagePrice = freeMileagePrice;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Tariff(int referenceID, Currency dailyPrice, int maxDays, Date fromDate, Date toDate) { // addAccessory
        this.referenceID = referenceID;
        this.dailyPrice = dailyPrice;
        this.maxDays = maxDays;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Tariff(Type type, int referenceID, Currency fixedPrice, Date fromDate, Date toDate) {   // addService, addPenalty
        this.type = type;
        this.referenceID = referenceID;
        this.fixedPrice = fixedPrice;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }








}
