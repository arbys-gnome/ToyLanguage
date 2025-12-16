package io.github.arbys_gnome.model.statement

import io.github.arbys_gnome.model.exception.InvalidTypeException
import io.github.arbys_gnome.model.expression.Expression
import io.github.arbys_gnome.model.type.Type
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class WhileStatementTest : ShouldSpec({
    lateinit var conditionMock: Expression
    lateinit var bodyMock: Statement

    beforeTest {
        conditionMock = mockk()
        bodyMock = mockk()
    }

    context("WhileStatement.typecheck") {
        should("return typeEnv when condition is bool") {
            val typeEnv = hashMapOf("x" to Type.INT)
            every { conditionMock.typecheck(any()) } returns Type.BOOL
            every { bodyMock.typecheck(any()) } returns hashMapOf()

            val stmt = WhileStatement(conditionMock, bodyMock)
            stmt.typecheck(typeEnv) shouldBe typeEnv
        }

        should("throw InvalidTypeException when condition is not bool") {
            val typeEnv = hashMapOf("x" to Type.INT)
            every { conditionMock.typecheck(any()) } returns Type.INT

            val stmt = WhileStatement(conditionMock, bodyMock)
            shouldThrow<InvalidTypeException> {
                stmt.typecheck(typeEnv)
            }
        }

        should("typecheck body with cloned typeEnv") {
            val typeEnv = hashMapOf("x" to Type.INT)
            var bodyChecked = false

            every { conditionMock.typecheck(any()) } returns Type.BOOL
            every { bodyMock.typecheck(any()) } answers {
                bodyChecked = true
                hashMapOf()
            }

            val stmt = WhileStatement(conditionMock, bodyMock)
            stmt.typecheck(typeEnv)

            bodyChecked shouldBe true
        }
    }

    context("WhileStatement.toString") {
        should("return the while statement string representation") {
            every { conditionMock.toString() } returns "x > 0"
            every { bodyMock.toString() } returns "x = x - 1"

            val stmt = WhileStatement(conditionMock, bodyMock)
            stmt.toString() shouldBe "while(x > 0) { x = x - 1 }"
        }
    }
})
