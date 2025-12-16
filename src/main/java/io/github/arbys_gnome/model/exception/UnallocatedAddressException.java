package io.github.arbys_gnome.model.exception;

public class UnallocatedAddressException extends Exception {
    public UnallocatedAddressException(String address) { super("Address" + address + " is unallocated."); }
}
