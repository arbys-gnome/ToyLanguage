package me.rares.model.state;

import me.rares.model.exception.InvalidHeapAddressException;
import me.rares.model.value.Value;

import java.util.HashMap;

public class MapHeap implements Heap {
    private HashMap<Integer, Value> map = new HashMap<>();
    private int nextFree = 1;

    @Override
    public boolean isAllocated(Integer address) {
        return map.containsKey(address);
    }

    @Override
    public int allocate(Value value) {
        int address = nextFree;
        map.put(nextFree++, value);
        return address;
    }

    @Override
    public Value read(Integer address) {
        return map.get(address);
    }

    @Override
    public void write(Integer address, Value value) throws InvalidHeapAddressException {
        if (!map.containsKey(address)) {
            throw new InvalidHeapAddressException(address + " is not a valid address.");
        }
        map.replace(address, value);
    }

    @Override
    public String toString() {
        return "MapHeap";
    }
}
