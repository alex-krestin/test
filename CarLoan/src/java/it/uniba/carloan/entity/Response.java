package it.uniba.carloan.entity;


import java.util.List;

public class Response {
    private boolean operationResult = false;
    private List<?> list;
    private TransferObject transferObject;

    public Response() {}

    public Response(boolean operationResult) {
        this.operationResult = operationResult;
    }

    public Response(List<?> list) {
        this.list = list;
    }

    public Response(TransferObject transferObject) {
        this.transferObject = transferObject;
    }

    public boolean getOperationResult() {
        return operationResult;
    }

    public List<?> getList() {
        return list;
    }

    public TransferObject getTransferObject() {
        return transferObject;
    }

}
