package io.github.arbys_gnome.model.expression

import io.github.arbys_gnome.model.type.Type
import io.github.arbys_gnome.model.state.Heap
import io.github.arbys_gnome.model.value.IntValue
import io.github.arbys_gnome.model.state.SymbolTable
import io.github.arbys_gnome.model.exception.UndefinedVariableException
import io.github.arbys_gnome.model.type.RefType

import io.mockk.mockk
import io.mockk.every
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.matchers.shouldBe
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.assertions.throwables.shouldThrow

class VariableExpressionTest : ShouldSpec({
    lateinit var symbolTable: SymbolTable
    lateinit var heap: Heap

    beforeEach {
        symbolTable = mockk()
        heap = mockk()
    }

    context("VariableExpression.evaluate") {
        should("return the correct value") {
            val value = IntValue(42)

            every { symbolTable.isDefined("x") } returns true
            every { symbolTable.getValue("x") } returns value

            val expr = VariableExpression("x")

            expr.evaluate(symbolTable, heap) shouldBe value
        }
        should("throw UndefinedVariableException") {
            every { symbolTable.getValue("x") } throws UndefinedVariableException("x")

            val expr = VariableExpression("x")

            shouldThrow<UndefinedVariableException> {
                expr.evaluate(symbolTable, heap)
            }
        }
    }
    context("VariableExpression.typecheck") {
        should("return the correct type") {
            val testCases = table(
                headers("variableName", "type", "expectedType"),
                row("x", Type.INT, Type.INT),
                row("y", Type.BOOL, Type.BOOL),
                row("z", Type.STRING, Type.STRING)
            )

            forAll(testCases) { variableName, type, expectedType ->
                val typeEnv = hashMapOf(variableName to type)
                val expr = VariableExpression(variableName)

                expr.typecheck(typeEnv) shouldBe expectedType
            }
        }
        should("throw UndefinedVariableException") {
            val typeEnv = hashMapOf<String, Type>()

            val expr = VariableExpression("x")

            shouldThrow<UndefinedVariableException> {
                expr.typecheck(typeEnv)
            }
        }
    }
    context("VariableExpression.toString") {
        should("return the variable name") {
            val expr = VariableExpression("x")

            expr.toString() shouldBe "x"
        }
    }
})
