package io.github.arbys_gnome.model.statement

import io.github.arbys_gnome.model.exception.InvalidTypeException
import io.github.arbys_gnome.model.expression.Expression
import io.github.arbys_gnome.model.type.Type
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class OpenFileReadTest : ShouldSpec({
    lateinit var expressionMock: Expression

    beforeTest {
        expressionMock = mockk()
    }

    context("OpenFileRead.typecheck") {
        should("return typeEnv when expression is string") {
            val typeEnv = hashMapOf("x" to Type.INT)
            every { expressionMock.typecheck(any()) } returns Type.STRING

            val stmt = OpenFileRead(expressionMock)
            stmt.typecheck(typeEnv) shouldBe typeEnv
        }

        should("throw InvalidTypeException when expression is not string") {
            val typeEnv = hashMapOf("x" to Type.INT)
            every { expressionMock.typecheck(any()) } returns Type.INT

            val stmt = OpenFileRead(expressionMock)
            shouldThrow<InvalidTypeException> {
                stmt.typecheck(typeEnv)
            }
        }
    }

    context("OpenFileRead.toString") {
        should("return the openRFile statement string representation") {
            every { expressionMock.toString() } returns "\"file.txt\""

            val stmt = OpenFileRead(expressionMock)
            stmt.toString() shouldBe "openRFile(\"file.txt\")"
        }
    }
})
