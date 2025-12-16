package io.github.arbys_gnome.model.expression;

import io.github.arbys_gnome.model.exception.InvalidTypeException;
import io.github.arbys_gnome.model.state.Heap;
import io.github.arbys_gnome.model.state.SymbolTable;
import io.github.arbys_gnome.model.type.RefType;
import io.github.arbys_gnome.model.type.Type;
import io.github.arbys_gnome.model.value.RefValue;
import io.github.arbys_gnome.model.value.Value;

import java.util.HashMap;

public record ReadHeapExpression(Expression expression) implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws Exception {
        Value val = expression.evaluate(symbolTable, heap);
        if (!val.type().isReference()) {
            throw new InvalidTypeException("ReadHeapExpression: the expression does not evaluate to a reference.");
        }
        RefValue ref = (RefValue) val;
        return heap.read(ref.address());
    }

    @Override
    public Type typecheck(HashMap<String, Type> typeEnv) throws Exception {
        Type typ = expression.typecheck(typeEnv);
        if (!typ.isReference()) {
            throw new InvalidTypeException("ReadHeapExpression: the expression does not evaluate to a reference.");
        }
        return ((RefType)typ).innerType();
    }

    @Override
    public String toString() {
        return "rH(" + expression.toString() + ")";
    }
}
