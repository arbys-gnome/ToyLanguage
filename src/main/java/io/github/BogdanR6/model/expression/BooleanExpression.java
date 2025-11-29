package io.github.BogdanR6.model.expression;

import io.github.BogdanR6.model.exception.InvalidOperatorException;
import io.github.BogdanR6.model.exception.InvalidTypeException;
import io.github.BogdanR6.model.state.Heap;
import io.github.BogdanR6.model.state.SymbolTable;
import io.github.BogdanR6.model.type.Type;
import io.github.BogdanR6.model.value.BoolValue;
import io.github.BogdanR6.model.value.Value;

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

    private boolean getResult(boolean leftValue, boolean rightValue, String operator) throws InvalidOperatorException {
        return switch (operator) {
            case "&&" -> leftValue && rightValue;
            case "||" -> leftValue || rightValue;
            default -> throw new InvalidOperatorException("BooleanExpression: invalid operator");
        };
    }
}
