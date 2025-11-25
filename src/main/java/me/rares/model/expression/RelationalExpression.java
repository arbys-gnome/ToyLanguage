package me.rares.model.expression;

import me.rares.model.exception.InvalidHeapAddressException;
import me.rares.model.exception.InvalidOperatorException;
import me.rares.model.exception.InvalidTypeException;
import me.rares.model.state.Heap;
import me.rares.model.state.SymbolTable;
import me.rares.model.type.Type;
import me.rares.model.value.BoolValue;
import me.rares.model.value.IntValue;
import me.rares.model.value.Value;

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
