package io.github.BogdanR6.model.exception;

public class UnallocatedAddressException extends Exception {
    public UnallocatedAddressException(String address) { super("Address" + address + " is unallocated."); }
}
