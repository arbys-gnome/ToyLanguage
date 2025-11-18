package me.rares.model.state;

import me.rares.model.value.Value;

import java.util.HashMap;

public class MapHeap implements Heap {
    private HashMap<Integer, Value> map = new HashMap<>();
    private int nextFree = 1;

    @Override
    public int allocate(Value value) {
        int address = nextFree;
        map.put(nextFree++, value);
        return address;
    }

    @Override
    public String toString() {
        return "MapHeap";
    }
}
