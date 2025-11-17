package me.rares.examples;

import me.rares.model.expression.ArithmeticExpression;
import me.rares.model.expression.ValueExpression;
import me.rares.model.expression.VariableExpression;
import me.rares.model.statement.*;
import me.rares.model.type.Type;
import me.rares.model.value.BoolValue;
import me.rares.model.value.IntValue;
import me.rares.model.value.StringValue;

public class Examples {
    // Example 1: int v; v=2; Print(v)
    public static final Statement ex1 = new CompoundStatement(
            new VariableDeclarationStatement(Type.INTEGER, "v"),
            new CompoundStatement(
                    new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                    new PrintStatement(new VariableExpression("v"))
            )
    );

    // Example 2: int a; int b; a=2+3*5; b=a+1; Print(b)
    public static final Statement ex2 = new CompoundStatement(
            new VariableDeclarationStatement(Type.INTEGER, "a"),
            new CompoundStatement(
                    new VariableDeclarationStatement(Type.INTEGER, "b"),
                    new CompoundStatement(
                            new AssignmentStatement("a",
                                    new ArithmeticExpression(
                                            new ValueExpression(new IntValue(2)),
                                            '+',
                                            new ArithmeticExpression(
                                                    new ValueExpression(new IntValue(3)),
                                                    '*',
                                                    new ValueExpression(new IntValue(5))
                                            )
                                    )
                            ),
                            new CompoundStatement(
                                    new AssignmentStatement("b",
                                            new ArithmeticExpression(
                                                    new VariableExpression("a"),
                                                    '+',
                                                    new ValueExpression(new IntValue(1))
                                            )
                                    ),
                                    new PrintStatement(new VariableExpression("b"))
                            )
                    )
            )
    );

    // Example 3: bool a; int v; a=true; If a Then v=2 Else v=3; Print(v)
    public static final Statement ex3 = new CompoundStatement(
            new VariableDeclarationStatement(Type.BOOLEAN, "a"),
            new CompoundStatement(
                    new VariableDeclarationStatement(Type.INTEGER, "v"),
                    new CompoundStatement(
                            new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                            new CompoundStatement(
                                    new IfStatement(
                                            new VariableExpression("a"),
                                            new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                            new AssignmentStatement("v", new ValueExpression(new IntValue(3)))
                                    ),
                                    new PrintStatement(new VariableExpression("v"))
                            )
                    )
            )
    );

    // Example 4:
    // string varf; varf="/tmp/test.in"; openRFile(varf);
    // int varc; readFile(varf,varc); print(varc); closeRFile(varf)
    public static final Statement ex4 = new CompoundStatement(
            new VariableDeclarationStatement(Type.STRING, "varf"),
            new CompoundStatement(
                    new AssignmentStatement("varf", new ValueExpression(new StringValue("/tmp/test.in"))),
                    new CompoundStatement(
                            new OpenFileRead(new VariableExpression("varf")),
                            new CompoundStatement(
                                    new VariableDeclarationStatement(Type.INTEGER, "varc"),
                                    new CompoundStatement(
                                            new ReadFile(new VariableExpression("varf"), "varc"),
                                            new CompoundStatement(
                                                    new PrintStatement(new VariableExpression("varc")),
                                                    new CompoundStatement(
                                                            new ReadFile(new VariableExpression("varf"), "varc"),
                                                            new CompoundStatement(
                                                                    new PrintStatement(new VariableExpression("varc")),
                                                                    new CloseFileRead(new VariableExpression("varf"))
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            )
    );
}
