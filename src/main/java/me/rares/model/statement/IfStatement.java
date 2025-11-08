package me.rares.model.statement;

import me.rares.model.expression.Expression;
import me.rares.model.state.ProgramState;
import me.rares.model.type.Type;
import me.rares.model.value.BooleanValue;
import me.rares.model.value.Value;

public class IfStatement  implements Statement {
    private final Expression condition;
    private final Statement thenStatement;
    private final Statement elseStatement;

    public IfStatement(Expression condition, Statement thenStatement, Statement elseStatement) {
        this.condition = condition;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        Value value = condition.evaluate(state.symbolTable());
        if(value.getType() != Type.BOOLEAN) {
            throw new RuntimeException();
        }

        var booleanValue = (BooleanValue) value;
        if(booleanValue.value()) {
            state.executionStack().push(thenStatement);
        }
        else {
            state.executionStack().push(elseStatement);
        }
        return null;
    }
}
