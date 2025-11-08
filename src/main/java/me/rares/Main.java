package me.rares;

import me.rares.controller.Controller;
import me.rares.model.expression.BooleanExpression;
import me.rares.model.expression.ValueExpression;
import me.rares.model.expression.VariableExpression;
import me.rares.model.statement.*;
import me.rares.model.statement.*;
import me.rares.model.type.Type;
import me.rares.model.value.BooleanValue;
import me.rares.model.value.IntegerValue;

public class Main {
    void main() {
        var controller = new Controller();
        // ex1:
        // int v; v=2; Print(v)
        Statement ex1 = new CompoundStatement(
                new VariableDeclarationStatement(Type.INTEGER, "v"),
                new CompoundStatement(
                        new AssignmentStatement(
                                "v", new ValueExpression(new IntegerValue(2))
                        ),
                        new PrintStatement(new VariableExpression("v"))
                )
        );
        controller.execute(ex1);

        // ex2:
        // bool a; int v; a=false; If a Then v=2 Else v=3; Print(v)
        Statement ex2 = new CompoundStatement(
                new VariableDeclarationStatement(Type.BOOLEAN, "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(Type.INTEGER, "v"),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ValueExpression(new BooleanValue(false))),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                                                new AssignmentStatement("v", new ValueExpression(new IntegerValue(3)))
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );
        controller.execute(ex2);

        // ex3:
        // string varf; varf="test.in"; openRFile(varf);
        // int varc; readFile(varf,varc); print(varc);
        // closeRFile(varf)
    }
}
