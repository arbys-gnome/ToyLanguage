package io.github.arbys_gnome.model.expression;

import io.github.arbys_gnome.model.exception.InvalidOperatorException;
import io.github.arbys_gnome.model.exception.InvalidTypeException;
import io.github.arbys_gnome.model.state.Heap;
import io.github.arbys_gnome.model.state.SymbolTable;
import io.github.arbys_gnome.model.type.Type;
import io.github.arbys_gnome.model.value.BoolValue;
import io.github.arbys_gnome.model.value.Value;

import java.util.HashMap;

public record BooleanExpression(Expression left, String operator, Expression right) implements Expression{
    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws Exception {
        Value resultLeft = left.evaluate(symbolTable, heap);
        Value resultRight = right.evaluate(symbolTable, heap);

        if (!(resultLeft.type().equals(Type.BOOL))){
            throw new InvalidTypeException("BooleanExpression: left value is not a boolean value");
        }
        if (!(resultRight.type().equals(Type.BOOL))){
            throw new InvalidTypeException("BooleanExpression: right value is not a boolean value");
        }

        boolean leftValue = ((BoolValue)resultLeft).value();
        boolean rightValue = ((BoolValue)resultLeft).value();

        boolean result = getResult(leftValue, rightValue, operator);
        return new BoolValue(result);
    }

    @Override
    public Type typecheck(HashMap<String, Type> typeEnv) throws Exception {
        Type typ1, typ2;
        typ1 = left.typecheck(typeEnv);
        typ2 = right.typecheck(typeEnv);
        if (!typ1.equals(Type.BOOL)) {
            throw new InvalidTypeException("BooleanExpression: left value is not a boolean.");
        }
        if (!typ2.equals(Type.BOOL)) {
            throw new InvalidTypeException("BooleanExpression: right value is not a boolean.");
        }
        return Type.BOOL;
    }

    private boolean getResult(boolean leftValue, boolean rightValue, String operator) throws InvalidOperatorException {
        return switch (operator) {
            case "&&" -> leftValue && rightValue;
            case "||" -> leftValue || rightValue;
            default -> throw new InvalidOperatorException("BooleanExpression: invalid operator");
        };
    }
}
