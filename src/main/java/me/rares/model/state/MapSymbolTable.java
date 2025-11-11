package me.rares.model.state;

import me.rares.model.exception.InvalidTypeException;
import me.rares.model.exception.InvalidVariableNameException;
import me.rares.model.type.Type;
import me.rares.model.value.Value;

import java.util.HashMap;
import java.util.Map;

public class MapSymbolTable implements  SymbolTable {
    Map<String, Value> symbolTable = new HashMap<>();

    @Override
    public void setValue(String variableName, Value value) {
        symbolTable.put(variableName, value);
    }

    @Override
    public boolean isDefined(String variableName) {
        return symbolTable.containsKey(variableName);
    }

    @Override
    public Type getType(String variableName) {
        return getValue(variableName).getType();
    }

    @Override
    public void declareVariable(Type type, String variableName) {
        symbolTable.put(variableName, type.getDefaultValue());
    }

    @Override
    public Value getValue(String variableName) {
        return symbolTable.get(variableName);
    }

    @Override
    public Value lookup(String variableName) throws InvalidVariableNameException {
        if (!symbolTable.containsKey(variableName)){
            throw new InvalidVariableNameException("Variable " + variableName + " is not defined!");
        }
        return symbolTable.get(variableName);
    }

    @Override
    public String toString() {
        return "MapSymbolTable{" +
                "symbolTable=" + symbolTable +
                '}';
    }
}
