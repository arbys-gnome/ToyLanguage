package me.rares.model.expression;

import me.rares.model.exception.InvalidOperatorException;
import me.rares.model.exception.InvalidTypeException;
import me.rares.model.state.Heap;
import me.rares.model.state.SymbolTable;
import me.rares.model.type.Type;
import me.rares.model.value.IntValue;
import me.rares.model.value.Value;

public record ArithmeticExpression(Expression left, char operator, Expression right) implements Expression {

    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws Exception {
        Value resultLeft = left.evaluate(symbolTable, heap);
        Value resultRight = right.evaluate(symbolTable, heap);

        if (!(resultLeft.type().equals(Type.INT))) {
            throw new InvalidTypeException("ArithmeticExpression: left value is not an integer");
        }

        if (!(resultRight.type().equals(Type.INT))) {
            throw new InvalidTypeException("ArithmeticExpression: right value is not an integer");
        }

        int leftValue = ((IntValue)resultLeft).value();
        int rightValue = ((IntValue)resultRight).value();

        int result = getResult(leftValue, rightValue, operator);
        return new IntValue(result);
    }

    private int getResult(int leftValue, int rightValue, char operator) throws InvalidOperatorException {
        // TODO: replace char with enum
        return switch (operator) {
            case '+' -> leftValue + rightValue;
            case '-' -> leftValue - rightValue;
            case '*' -> leftValue * rightValue;
            case '/' -> leftValue / rightValue;
            default -> throw new InvalidOperatorException("ArithmeticExpression: invalid operator");
        };
    }

    @Override
    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }
}
