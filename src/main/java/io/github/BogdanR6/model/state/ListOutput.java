package io.github.BogdanR6.model.state;

import io.github.BogdanR6.model.value.Value;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListOutput implements Output {
    private final List<Value> values = new ArrayList<>();
    @Override
    public void add(Value value) {
        values.add(value);
    }

    @Override
    public void clear() {
        values.clear();
    }

    @Override
    public String toString() {
        return "ListOut = " + values;
    }

    @Override
    public Iterator<Value> iterator() {
        return values.iterator();
    }
}
