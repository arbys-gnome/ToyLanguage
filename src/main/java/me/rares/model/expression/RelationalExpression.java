package me.rares.model.expression;

import me.rares.model.state.SymbolTable;
import me.rares.model.type.Type;
import me.rares.model.value.BoolValue;
import me.rares.model.value.IntValue;
import me.rares.model.value.Value;

public record RelationalExpression(Expression left, String operator, Expression right) implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable) {
        Value resultLeft = left.evaluate(symbolTable);
        Value resultRight = right.evaluate(symbolTable);

        if (!(resultLeft.type().equals(Type.INT))) {
            throw new RuntimeException("RelationalExpression: left value is not a integer value");
        }
        if (!(resultRight.type().equals(Type.INT))) {
            throw new RuntimeException("RelationalExpression: right value is not a integer value");
        }

        int leftValue = ((IntValue)resultLeft).value();
        int rightValue = ((IntValue)resultLeft).value();

        boolean result = getResult(leftValue, rightValue, operator);
        return new BoolValue(result);
    }

    private boolean getResult(int leftValue, int rightValue, String operator) {
        return switch (operator) {
            case "<" -> leftValue < rightValue;
            case "<=" -> leftValue <= rightValue;
            case "==" -> leftValue == rightValue;
            case "!=" -> leftValue != rightValue;
            case ">" -> leftValue > rightValue;
            case ">=" -> leftValue >= rightValue;
            default -> throw new RuntimeException("RelationalExpression: invalid operator");
        };
    }

}
