package entity;

import java.util.ArrayList;

public class TransferObject {
    private boolean operationResult;
    private ArrayList<?> arrayList;

    public TransferObject() {}

    public TransferObject(boolean operationResult) {
        this.operationResult = operationResult;
    }

    public TransferObject(ArrayList<?> arrayList) {
        this.arrayList = arrayList;
    }

    public boolean getOperationResult() {
        return operationResult;
    }

    public ArrayList<?> getArrayList() {
        return arrayList;
    }

}
