package me.rares.model.expression;

import me.rares.model.exception.InvalidTypeException;
import me.rares.model.state.SymbolTable;
import me.rares.model.value.IntegerValue;
import me.rares.model.value.Value;

public record ArithmeticExpression(Expression left, char operator, Expression right) implements Expression {
    @Override
    public Value evaluate(SymbolTable symbolTable) {
        Value resultLeft = left.evaluate(symbolTable);
        Value resultRight = right.evaluate(symbolTable);

        if (!(resultLeft instanceof IntegerValue(int leftValue))) {
            throw new InvalidTypeException("ArithmeticExpression: left value is not an integer");
        }

        if (!(resultRight instanceof IntegerValue(int rightValue))) {
            throw new InvalidTypeException("ArithmeticExpression: right value is not an integer");
        }

        int result = getResult(leftValue, rightValue, operator);
        return new IntegerValue(result);
    }

    private int getResult(int leftValue, int rightValue, char operator) {
        return switch (operator) {
            case '+' -> leftValue + rightValue;
            case '-' -> leftValue - rightValue;
            case '*' -> leftValue * rightValue;
            case '/' -> leftValue / rightValue;
            default -> throw new RuntimeException("ArithmeticExpression: invalid operator");
        };
    }
}
