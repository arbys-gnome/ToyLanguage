package me.rares.model.expression;

import me.rares.model.state.ProgramState;
import me.rares.model.state.SymbolTable;
import me.rares.model.value.BooleanValue;
import me.rares.model.value.IntegerValue;
import me.rares.model.value.Value;

public record RelationalExpression(Expression left, String operator, Expression right) implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable) {
        Value resultLeft = left.evaluate(symbolTable);
        Value resultRight = right.evaluate(symbolTable);
        if (!(resultLeft instanceof IntegerValue(int leftValue))){
            throw new RuntimeException("RelationalExpression: left value is not a boolean value");
        }
        if (!(resultRight instanceof IntegerValue(int rightValue))){
            throw new RuntimeException("RelationalExpression: right value is not a boolean value");
        }
        boolean result = getResult(leftValue, rightValue, operator);
        return new BooleanValue(result);
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
