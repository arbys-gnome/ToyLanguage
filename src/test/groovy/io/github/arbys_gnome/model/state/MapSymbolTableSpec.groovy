package io.github.arbys_gnome.model.state

import io.github.arbys_gnome.model.exception.InvalidVariableNameException
import io.github.arbys_gnome.model.exception.UndefinedVariableException
import io.github.arbys_gnome.model.exception.VariableRedefinitionException
import io.github.arbys_gnome.model.type.Type
import io.github.arbys_gnome.model.value.Value
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class MapSymbolTableSpec extends Specification {

    @Subject def mapSymbolTable = new MapSymbolTable()

    Value valueMock;

    def setup() {
        MockitoAnnotations.openMocks(this)
        valueMock = Mockito.mock(Value.class)
    }

    def "isDefined should return true if the map contains the variable name"() {
        given:
        def variableName = "a"

        // make the private content field accessible for the test
        def field = mapSymbolTable.class.getDeclaredField("content")
        field.accessible = true
        def innerMap = field.get(mapSymbolTable)

        // insert a value for the variableName
        innerMap[variableName] = valueMock;

        when:
        def result = mapSymbolTable.isDefined(variableName)

        then:
        result == true
    }

    def "isDefined should return false if the map does not contain the variable name"() {
        given:
        def variableName = "a"

        when:
        def result = mapSymbolTable.isDefined(variableName)

        then:
        result == false
    }

    @Unroll
    def "declareVariable should store the new variable and name in the map"() {
        given:
        def variableName = "a"

        // make the private content field accessible for the test
        def field = mapSymbolTable.class.getDeclaredField("content")
        field.accessible = true

        when:
        mapSymbolTable.declareVariable(type, variableName)

        then:
        def innerMap = field.get(mapSymbolTable) as Map
        innerMap.containsKey(variableName)

        where:
        type << [Type.INT, Type.BOOL, Type.STRING]
    }

    def "declareVariable should throw NullPointerException if the type is null"() {
        given:
        def variableName = "a"

        when:
        mapSymbolTable.declareVariable(null, variableName)

        then:
        thrown(NullPointerException)
    }

    def "declareVariable should throw NullPointerException if the variable name is null"() {
        given:
        def typeMock = Mockito.mock(Type)

        when:
        mapSymbolTable.declareVariable(typeMock, null)

        then:
        thrown(NullPointerException)
    }

    def "declareVariable should throw InvalidVariableNameException if the variable name is empty"() {
        given:
        def typeMock = Mockito.mock(Type)

        when:
        mapSymbolTable.declareVariable(typeMock, "")

        then:
        thrown(InvalidVariableNameException)
    }

    @Unroll
    def "declareVariable should throw VariableRedefinitionException when redefining variable"() {
        given:
        def variableName = "a"

        when:
        mapSymbolTable.declareVariable(firstType, variableName)
        mapSymbolTable.declareVariable(secondType, variableName)

        then:
        thrown(VariableRedefinitionException)

        where:
        firstType   | secondType
        Type.INT    | Type.INT
        Type.INT    | Type.STRING
        Type.INT    | Type.BOOL
        Type.STRING | Type.STRING
        Type.STRING | Type.BOOL
        Type.BOOL   | Type.BOOL

    }

    def "getType should throw UndefinedVariableException if the variable is not in the map"() {
        given:
        def variableName = "a"

        when:
        mapSymbolTable.getType(variableName)

        then:
        thrown(UndefinedVariableException)
    }

    @Unroll
    def "getType should return the type of the variable stored in the map"() {
        given:
        def variableName = "a"
        // make the private content field accessible for the test
        def field = mapSymbolTable.class.getDeclaredField("content")
        field.accessible = true
        def innerMap = field.get(MapSymbolTable)

        innerMap[variableName] = variableType

        when:
        def res = mapSymbolTable.getType(variableName)

        then:
        res == variableType

        where:
        variableType << [Type.INT, Type.STRING, Type.BOOL]
    }

    def "SetValue"() {
    }

    def "GetValue"() {
    }
}
