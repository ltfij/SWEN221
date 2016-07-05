package com.bytebach.model;

/**
 * An invalid operation exception is used to signal the user attempted something which is not
 * permitted.
 * 
 * @author djp
 * 
 */
public class InvalidOperation extends RuntimeException {

    private static final long serialVersionUID = 6496821114802671534L;

    public InvalidOperation(String msg) {
        super(msg);
    }
}
