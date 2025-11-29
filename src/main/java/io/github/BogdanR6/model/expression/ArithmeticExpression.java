package io.github.BogdanR6.model.expression;

import io.github.BogdanR6.model.exception.InvalidOperatorException;
import io.github.BogdanR6.model.exception.InvalidTypeException;
import io.github.BogdanR6.model.state.Heap;
import io.github.BogdanR6.model.state.SymbolTable;
import io.github.BogdanR6.model.type.Type;
import io.github.BogdanR6.model.value.IntValue;
import io.github.BogdanR6.model.value.Value;

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
