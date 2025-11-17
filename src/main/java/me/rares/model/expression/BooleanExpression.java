package me.rares.model.expression;

import me.rares.model.state.SymbolTable;
import me.rares.model.type.Type;
import me.rares.model.value.BoolValue;
import me.rares.model.value.Value;

public record BooleanExpression(Expression left, String operator, Expression right) implements Expression{
    @Override
    public Value evaluate(SymbolTable symbolTable) {
        Value resultLeft = left.evaluate(symbolTable);
        Value resultRight = right.evaluate(symbolTable);

        if (!(resultLeft.type().equals(Type.BOOL))){
            throw new RuntimeException("BooleanExpression: left value is not a boolean value");
        }
        if (!(resultRight.type().equals(Type.BOOL))){
            throw new RuntimeException("BooleanExpression: right value is not a boolean value");
        }

        boolean leftValue = ((BoolValue)resultLeft).value();
        boolean rightValue = ((BoolValue)resultLeft).value();

        boolean result = getResult(leftValue, rightValue, operator);
        return new BoolValue(result);
    }

    private boolean getResult(boolean leftValue, boolean rightValue, String operator) {
        return switch (operator) {
            case "&&" -> leftValue && rightValue;
            case "||" -> leftValue || rightValue;
            default -> throw new RuntimeException("BooleanExpression: invalid operator");
        };
    }
}
