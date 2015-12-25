package it.uniba.carloan.entity;


public class ContractObject<T extends ContractAddition> implements TransferObject, Embedded {
    private T object;
    private Tariff tariff;

    public ContractObject(T object, Tariff tariff) {
        this(object);
        this.tariff = tariff;
    }

    public ContractObject(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    @Override
    public String toStringField() {
        return object.toStringField();
    }
}
