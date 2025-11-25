package me.rares.model.expression;

import me.rares.model.exception.InvalidHeapAddressException;
import me.rares.model.exception.InvalidTypeException;
import me.rares.model.state.Heap;
import me.rares.model.state.SymbolTable;
import me.rares.model.type.RefType;
import me.rares.model.value.RefValue;
import me.rares.model.value.Value;

public record ReadHeapExpression(String refVariableName) implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) {
        Value val = symbolTable.getValue(refVariableName);
        if (!val.type().isReference()) {
            throw new InvalidTypeException("ReadHeapExpression: the value is not a reference");
        }
        RefValue ref = (RefValue) val;
        try {
            return heap.read(ref.address());
        } catch (InvalidHeapAddressException e) {
            throw new RuntimeException(e); // TODO: implement custom exception
        }
    }
}
