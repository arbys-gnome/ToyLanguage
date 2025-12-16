package io.github.arbys_gnome.model.statement

import io.github.arbys_gnome.model.expression.Expression
import io.github.arbys_gnome.model.type.Type
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class PrintStatementTest : ShouldSpec({
    lateinit var expressionMock: Expression

    beforeTest {
        expressionMock = mockk()
    }

    context("PrintStatement.typecheck") {
        should("return typeEnv unchanged after typechecking expression") {
            val typeEnv = hashMapOf("x" to Type.INT)
            every { expressionMock.typecheck(any()) } returns Type.INT

            val stmt = PrintStatement(expressionMock)
            stmt.typecheck(typeEnv) shouldBe typeEnv

            verify { expressionMock.typecheck(typeEnv) }
        }

        should("typecheck expression with given typeEnv") {
            val typeEnv = hashMapOf("x" to Type.INT, "y" to Type.BOOL)
            every { expressionMock.typecheck(any()) } returns Type.STRING

            val stmt = PrintStatement(expressionMock)
            stmt.typecheck(typeEnv)

            verify { expressionMock.typecheck(typeEnv) }
        }
    }

    context("PrintStatement.toString") {
        should("return the print statement string representation") {
            every { expressionMock.toString() } returns "x + 5"

            val stmt = PrintStatement(expressionMock)
            stmt.toString() shouldBe "print(x + 5)"
        }
    }
})
