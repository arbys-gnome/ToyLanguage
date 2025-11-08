package me.rares.model.state;

import me.rares.model.value.Value;

import java.util.ArrayList;
import java.util.List;

public class ListOutput implements Output {
    private final List<Value> values = new ArrayList<>();
    @Override
    public void add(Value value) {
        values.add(value);
    }

    @Override
    public String toString() {
        return "ListOut{" +
                "values=" + values +
                '}';
    }
}
