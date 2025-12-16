package io.github.arbys_gnome.model.statement

import io.github.arbys_gnome.model.exception.InvalidTypeException
import io.github.arbys_gnome.model.expression.Expression
import io.github.arbys_gnome.model.type.RefType
import io.github.arbys_gnome.model.type.Type
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class HeapWriteStatementTest : ShouldSpec({
    lateinit var expressionMock: Expression

    beforeTest {
        expressionMock = mockk()
    }

    context("HeapWriteStatement.typecheck") {
        should("return typeEnv when variable is RefType and expression matches inner type") {
            val typeEnv = hashMapOf<String, Type>("v" to RefType(Type.INT))
            every { expressionMock.typecheck(any()) } returns Type.INT

            val stmt = HeapWriteStatement("v", expressionMock)
            stmt.typecheck(typeEnv) shouldBe typeEnv
        }

        should("throw InvalidTypeException when variable is not defined") {
            val typeEnv = hashMapOf("x" to Type.INT)
            every { expressionMock.typecheck(any()) } returns Type.INT

            val stmt = HeapWriteStatement("v", expressionMock)
            shouldThrow<InvalidTypeException> {
                stmt.typecheck(typeEnv)
            }
        }

        should("throw InvalidTypeException when variable is not a reference type") {
            val typeEnv = hashMapOf("v" to Type.INT)
            every { expressionMock.typecheck(any()) } returns Type.INT

            val stmt = HeapWriteStatement("v", expressionMock)
            shouldThrow<InvalidTypeException> {
                stmt.typecheck(typeEnv)
            }
        }

        should("throw InvalidTypeException when expression type does not match reference inner type") {
            val typeEnv = hashMapOf<String, Type>("v" to RefType(Type.INT))
            every { expressionMock.typecheck(any()) } returns Type.BOOL

            val stmt = HeapWriteStatement("v", expressionMock)
            shouldThrow<InvalidTypeException> {
                stmt.typecheck(typeEnv)
            }
        }
    }

    context("HeapWriteStatement.toString") {
        should("return the heap write statement string representation") {
            val stmt = HeapWriteStatement("v", expressionMock)
            stmt.toString() shouldBe "HeapWriteStatement"
        }
    }
})
