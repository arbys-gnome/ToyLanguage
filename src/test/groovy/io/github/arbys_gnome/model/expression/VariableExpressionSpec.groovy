package io.github.arbys_gnome.model.expression

import io.github.arbys_gnome.model.exception.UndefinedVariableException
import io.github.arbys_gnome.model.state.SymbolTable
import io.github.arbys_gnome.model.value.Value
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import spock.lang.Specification
import spock.lang.Subject

class VariableExpressionSpec extends Specification {

    static final String VARIABLE_NAME = "a"

    @Subject def expression = new VariableExpression(VARIABLE_NAME)

    SymbolTable symbolTable
    Value value

    def setup() {
        MockitoAnnotations.openMocks(this) // initialize Mockito
        symbolTable = Mockito.mock(SymbolTable.class) // Mockito mock
        value = Mockito.mock(Value.class)
    }

    def "evaluate should throw NullPointerException when symbolTable is null"() {
        when:
        expression.evaluate(null, null)

        then:
        thrown(NullPointerException)
    }

    def "evaluate should return a value when symbolTable returns one"() {
        given:
        Mockito.when(symbolTable.getValue(VARIABLE_NAME)).thenReturn(value)

        when:
        def result = expression.evaluate(symbolTable, null)

        then:
        result == value
    }

    def "evaluate should throw UndefinedVariableException when variable is undefined"() {
        given:
        Mockito.when(symbolTable.getValue(VARIABLE_NAME))
                .thenThrow(new UndefinedVariableException(VARIABLE_NAME))

        when:
        expression.evaluate(symbolTable, null)

        then:
        thrown(UndefinedVariableException)
    }
}
