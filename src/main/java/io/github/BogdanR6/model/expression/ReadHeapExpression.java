package io.github.BogdanR6.model.expression;

import io.github.BogdanR6.model.exception.InvalidTypeException;
import io.github.BogdanR6.model.state.Heap;
import io.github.BogdanR6.model.state.SymbolTable;
import io.github.BogdanR6.model.value.RefValue;
import io.github.BogdanR6.model.value.Value;

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
