package me.rares.model.expression;

import me.rares.model.exception.InvalidTypeException;
import me.rares.model.state.Heap;
import me.rares.model.state.SymbolTable;
import me.rares.model.value.RefValue;
import me.rares.model.value.Value;

public record ReadHeapExpression(Expression expression) implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws Exception {
        Value val = expression.evaluate(symbolTable, heap);
        if (!val.type().isReference()) {
            throw new InvalidTypeException("ReadHeapExpression: the value is not a reference");
        }
        RefValue ref = (RefValue) val;
        return heap.read(ref.address());
    }

    @Override
    public String toString() {
        return "rH(" + expression.toString() + ")";
    }
}
