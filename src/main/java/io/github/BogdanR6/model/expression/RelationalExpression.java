package io.github.BogdanR6.model.expression;

import io.github.BogdanR6.model.exception.InvalidOperatorException;
import io.github.BogdanR6.model.exception.InvalidTypeException;
import io.github.BogdanR6.model.state.Heap;
import io.github.BogdanR6.model.state.SymbolTable;
import io.github.BogdanR6.model.type.Type;
import io.github.BogdanR6.model.value.BoolValue;
import io.github.BogdanR6.model.value.IntValue;
import io.github.BogdanR6.model.value.Value;

public record RelationalExpression(Expression left, String operator, Expression right) implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws Exception {
        Value resultLeft = left.evaluate(symbolTable, heap);
        Value resultRight = right.evaluate(symbolTable, heap);

        if (!(resultLeft.type().equals(Type.INT))) {
            throw new InvalidTypeException("RelationalExpression: left value is not a integer value");
        }
        if (!(resultRight.type().equals(Type.INT))) {
            throw new InvalidTypeException("RelationalExpression: right value is not a integer value");
        }

        int leftValue = ((IntValue)resultLeft).value();
        int rightValue = ((IntValue)resultLeft).value();

        boolean result = getResult(leftValue, rightValue, operator);
        return new BoolValue(result);
    }

    private boolean getResult(int leftValue, int rightValue, String operator) throws InvalidOperatorException {
        return switch (operator) {
            case "<" -> leftValue < rightValue;
            case "<=" -> leftValue <= rightValue;
            case "==" -> leftValue == rightValue;
            case "!=" -> leftValue != rightValue;
            case ">" -> leftValue > rightValue;
            case ">=" -> leftValue >= rightValue;
            default -> throw new InvalidOperatorException("RelationalExpression: invalid operator");
        };
    }
}
