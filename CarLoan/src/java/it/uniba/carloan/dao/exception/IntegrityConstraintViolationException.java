package it.uniba.carloan.dao.exception;


public class IntegrityConstraintViolationException extends Exception {

    public IntegrityConstraintViolationException(Throwable cause) {
        super(cause);
    }
}
