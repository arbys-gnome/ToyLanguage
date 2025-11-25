package me.rares.model.state;

import me.rares.model.exception.InvalidHeapAddressException;
import me.rares.model.value.Value;

import java.util.*;

public class MapHeap implements Heap {
    private final HashMap<Integer, Value> heap = new HashMap<>();
    private int nextFree = 1;

    @Override
    public boolean isAllocated(Integer address) {
        return heap.containsKey(address);
    }

    @Override
    public int allocate(Value value) {
        int address = nextFree;
        heap.put(nextFree++, value);
        return address;
    }

    @Override
    public Value read(Integer address) throws InvalidHeapAddressException {
        if (!heap.containsKey(address)) {
            throw new InvalidHeapAddressException(address + " is not a valid address.");
        }
        return heap.get(address);
    }

    @Override
    public void write(Integer address, Value value) throws InvalidHeapAddressException {
        if (!heap.containsKey(address)) {
            throw new InvalidHeapAddressException(address + " is not a valid address.");
        }
        heap.replace(address, value);
    }

    @Override
    public Iterable<Map.Entry<Integer, Value>> entrySet() {
        return heap.entrySet();
    }

    @Override
    public void setContent(Map<Integer, Value> newContent) {
        heap.clear();
        heap.putAll(newContent);
    }

    @Override
    public String toString() {
        return "MapHeap";
    }

    @Override
    public void clear() {
        heap.clear();
    }
}
