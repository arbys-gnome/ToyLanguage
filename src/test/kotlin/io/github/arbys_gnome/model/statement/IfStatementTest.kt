package io.github.arbys_gnome.model.statement

import io.github.arbys_gnome.model.exception.InvalidTypeException
import io.github.arbys_gnome.model.expression.Expression
import io.github.arbys_gnome.model.type.Type
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class IfStatementTest : ShouldSpec({
    lateinit var conditionMock: Expression
    lateinit var thenStmtMock: Statement
    lateinit var elseStmtMock: Statement

    beforeTest {
        conditionMock = mockk()
        thenStmtMock = mockk()
        elseStmtMock = mockk()
    }

    context("IfStatement.typecheck") {
        should("return typeEnv when condition is bool") {
            val typeEnv = hashMapOf("x" to Type.INT)
            every { conditionMock.typecheck(any()) } returns Type.BOOL
            every { thenStmtMock.typecheck(any()) } returns hashMapOf()
            every { elseStmtMock.typecheck(any()) } returns hashMapOf()

            val stmt = IfStatement(conditionMock, thenStmtMock, elseStmtMock)
            stmt.typecheck(typeEnv) shouldBe typeEnv
        }

        should("throw InvalidTypeException when condition is not bool") {
            val typeEnv = hashMapOf("x" to Type.INT)
            every { conditionMock.typecheck(any()) } returns Type.INT

            val stmt = IfStatement(conditionMock, thenStmtMock, elseStmtMock)
            shouldThrow<InvalidTypeException> {
                stmt.typecheck(typeEnv)
            }
        }

        should("typecheck both branches") {
            val typeEnv = hashMapOf("x" to Type.INT)
            var thenChecked = false
            var elseChecked = false

            every { conditionMock.typecheck(any()) } returns Type.BOOL
            every { thenStmtMock.typecheck(any()) } answers {
                thenChecked = true
                hashMapOf()
            }
            every { elseStmtMock.typecheck(any()) } answers {
                elseChecked = true
                hashMapOf()
            }

            val stmt = IfStatement(conditionMock, thenStmtMock, elseStmtMock)
            stmt.typecheck(typeEnv)

            thenChecked shouldBe true
            elseChecked shouldBe true
        }
    }

    context("IfStatement.toString") {
        should("return the if statement string representation") {
            every { conditionMock.toString() } returns "x > 5"
            every { thenStmtMock.toString() } returns "print(x)"
            every { elseStmtMock.toString() } returns "print(0)"

            val stmt = IfStatement(conditionMock, thenStmtMock, elseStmtMock)
            stmt.toString() shouldBe "if(x > 5) {print(x)} else {print(0)}"
        }
    }
})
