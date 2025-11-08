package me.rares;

import me.rares.controller.Controller;
import me.rares.model.expression.ValueExpression;
import me.rares.model.expression.VariableExpression;
import me.rares.model.statement.*;
import me.rares.model.statement.*;
import me.rares.model.type.Type;
import me.rares.model.value.IntegerValue;

public class Main {
    void main() {
        Statement ex1 = new CompoundStatement(
                new VariableDeclarationStatement(Type.INTEGER, "v"),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                        new PrintStatement(new VariableExpression("v"))));

        var controller = new Controller();
        controller.execute(ex1);
    }
}
