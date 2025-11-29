package io.github.BogdanR6.model.expression;

import io.github.BogdanR6.model.exception.UndefinedVariableException;
import io.github.BogdanR6.model.state.SymbolTable;
import io.github.BogdanR6.model.value.Value;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.Mock;

import static org.mockito.quality.Strictness.STRICT_STUBS;

@ExtendWith(MockitoExtension.class)
class VariableExpressionTest {
    // @Rule public MockitoRule mockito = MockitoJUnit.rule().strictness(STRICT_STUBS);

    private static Expression expression;
    private static String variableName;
    private @Spy SymbolTable symbolTableMock;
    private @Mock Value valueMock;

    @BeforeAll
    static void setUpAll() {
        variableName = "a";
        expression = new VariableExpression(variableName);
    }

    @Test
    void testEvaluateNullSymbolTable() {
        Assertions.assertThrows(NullPointerException.class, () -> expression.evaluate(null, null));
    }

    @Test
    void testEvaluateDoesNotThrow() throws UndefinedVariableException {
        Mockito.lenient().doReturn(valueMock).when(symbolTableMock).getValue(variableName);

        Assertions.assertDoesNotThrow(
                () -> Assertions.assertEquals(valueMock, expression.evaluate(symbolTableMock, null))
        );
    }

    @Test
    void testEvaluateThrowsUndefinedVariableExceptionOnUndefinedVariable() throws UndefinedVariableException {
        Mockito.doThrow(UndefinedVariableException.class)
                .when(symbolTableMock)
                .getValue(variableName);

        Assertions.assertThrows(
                UndefinedVariableException.class,
                () -> expression.evaluate(symbolTableMock, null)
        );
    }
}
