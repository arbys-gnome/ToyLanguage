package io.github.arbys_gnome.model.expression

import io.github.arbys_gnome.model.exception.InvalidTypeException
import io.github.arbys_gnome.model.operators.ArithmeticOperator
import io.github.arbys_gnome.model.type.Type
import io.github.arbys_gnome.model.value.BoolValue
import io.github.arbys_gnome.model.value.IntValue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class ArithmeticExpressionTest : ShouldSpec({
    lateinit var leftExprMock: Expression
    lateinit var rightExprMock: Expression

    beforeTest {
        leftExprMock = mockk()
        rightExprMock = mockk()
    }

    context("ArithmeticExpression.evaluate") {
        should("return the correct resulting value") {
            val testCases = table(
                headers("leftVal", "rightVal", "operation", "expected"),
                row(IntValue(10), IntValue(20), ArithmeticOperator.ADD, IntValue(30)),
                row(IntValue(22), IntValue(10), ArithmeticOperator.SUBTRACT, IntValue(12)),
                row(IntValue(10), IntValue(20), ArithmeticOperator.MULTIPLY, IntValue(200)),
                row(IntValue(20), IntValue(10), ArithmeticOperator.DIVIDE, IntValue(2))
            )

            forAll(testCases) { leftVal, rightVal, operation, expected ->
                every { leftExprMock.evaluate(null, null) } returns leftVal
                every { rightExprMock.evaluate(null, null) } returns rightVal

                val expr = ArithmeticExpression(leftExprMock, operation, rightExprMock)

                expr.evaluate(null, null).value() shouldBe expected.value()
            }
        }

        should("throw InvalidTypeException") {
            every { leftExprMock.evaluate(null, null) } returns BoolValue(false)
            every { rightExprMock.evaluate(null, null) } returns IntValue(11)

            val expr1 = ArithmeticExpression(leftExprMock, ArithmeticOperator.ADD, rightExprMock)
            shouldThrow<InvalidTypeException> {
                expr1.evaluate(null, null)
            }

            every { leftExprMock.evaluate(null, null) } returns IntValue(11)
            every { rightExprMock.evaluate(null, null) } returns BoolValue(true)

            val expr2 = ArithmeticExpression(leftExprMock, ArithmeticOperator.ADD, rightExprMock)
            shouldThrow<InvalidTypeException> {
                expr2.evaluate(null, null)
            }
        }
    }

    context("ArithmeticExpression.typecheck") {
        should("return Type.INT when both operands are int") {
            every { leftExprMock.typecheck(any()) } returns Type.INT
            every { rightExprMock.typecheck(any()) } returns Type.INT

            val expr = ArithmeticExpression(leftExprMock, ArithmeticOperator.ADD, rightExprMock)
            expr.typecheck(hashMapOf()) shouldBe Type.INT
        }

        should("throw InvalidTypeException if left operand is not int") {
            every { leftExprMock.typecheck(any()) } returns Type.BOOL
            every { rightExprMock.typecheck(any()) } returns Type.INT

            val expr = ArithmeticExpression(leftExprMock, ArithmeticOperator.ADD, rightExprMock)
            shouldThrow<InvalidTypeException> {
                expr.typecheck(hashMapOf())
            }
        }

        should("throw InvalidTypeException if right operand is not int") {
            every { leftExprMock.typecheck(any()) } returns Type.INT
            every { rightExprMock.typecheck(any()) } returns Type.BOOL

            val expr = ArithmeticExpression(leftExprMock, ArithmeticOperator.ADD, rightExprMock)
            shouldThrow<InvalidTypeException> {
                expr.typecheck(hashMapOf())
            }
        }
    }

    context("ArithmeticExpression.toString") {
        should("return the arithmetic expression according to the documentation") {
            every { leftExprMock.toString() } returns "a"
            every { rightExprMock.toString() } returns "b"

            val op1 = ArithmeticOperator.ADD
            val expr1 = ArithmeticExpression(leftExprMock, op1, rightExprMock)
            expr1.toString() shouldBe "a ${op1.toString()} b"

            val op2 = ArithmeticOperator.SUBTRACT
            val expr2 = ArithmeticExpression(leftExprMock, op2, rightExprMock)
            expr2.toString() shouldBe "a ${op2.toString()} b"

            val op3 = ArithmeticOperator.MULTIPLY
            val expr3 = ArithmeticExpression(leftExprMock, op3, rightExprMock)
            expr3.toString() shouldBe "a ${op3.toString()} b"

            val op4 = ArithmeticOperator.DIVIDE
            val expr4 = ArithmeticExpression(leftExprMock, op4, rightExprMock)
            expr4.toString() shouldBe "a ${op4.toString()} b"
        }
    }
})
