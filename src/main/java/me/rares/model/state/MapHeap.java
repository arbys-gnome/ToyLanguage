package me.rares.model.state;

import me.rares.model.exception.InvalidHeapAddressException;
import me.rares.model.value.Value;

import java.util.*;
import java.util.stream.Collectors;

public class MapHeap implements Heap {
    private final HashMap<Integer, Value> content = new HashMap<>();
    private int nextFree = 1;

    @Override
    public boolean isAllocated(Integer address) {
        return content.containsKey(address);
    }

    @Override
    public int allocate(Value value) {
        int address = nextFree;
        content.put(nextFree++, value);
        return address;
    }

    @Override
    public Value read(Integer address) throws InvalidHeapAddressException {
        if (!content.containsKey(address)) {
            throw new InvalidHeapAddressException(address + " is not a valid address.");
        }
        return content.get(address);
    }

    @Override
    public void write(Integer address, Value value) throws InvalidHeapAddressException {
        if (!content.containsKey(address)) {
            throw new InvalidHeapAddressException(address + " is not a valid address.");
        }
        content.replace(address, value);
    }

    @Override
    public Iterable<Map.Entry<Integer, Value>> entrySet() {
        return content.entrySet();
    }

    @Override
    public void setContent(Map<Integer, Value> newContent) {
        content.clear();
        content.putAll(newContent);
    }

    @Override
    public String toString() {
        if (content.isEmpty()) {
            return "MapHeap:\n(empty)";
        }

        String entries = content.entrySet().stream()
                .map(e -> e.getKey() + " -> " + e.getValue().toString())
                .collect(Collectors.joining("\n"));

        return "MapHeap:\n" + entries;
    }

    @Override
    public void clear() {
        content.clear();
    }
}
