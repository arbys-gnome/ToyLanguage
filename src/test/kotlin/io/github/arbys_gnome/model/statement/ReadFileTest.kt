package io.github.arbys_gnome.model.statement

import io.github.arbys_gnome.model.exception.InvalidTypeException
import io.github.arbys_gnome.model.expression.Expression
import io.github.arbys_gnome.model.type.Type
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class ReadFileTest : ShouldSpec({
    lateinit var expressionMock: Expression

    beforeTest {
        expressionMock = mockk()
    }

    context("ReadFile.typecheck") {
        should("return typeEnv when variable is int and expression is string") {
            val typeEnv = hashMapOf("v" to Type.INT)
            every { expressionMock.typecheck(any()) } returns Type.STRING

            val stmt = ReadFile(expressionMock, "v")
            stmt.typecheck(typeEnv) shouldBe typeEnv
        }

        should("throw InvalidTypeException when variable is not defined") {
            val typeEnv = hashMapOf("x" to Type.INT)
            every { expressionMock.typecheck(any()) } returns Type.STRING

            val stmt = ReadFile(expressionMock, "v")
            shouldThrow<InvalidTypeException> {
                stmt.typecheck(typeEnv)
            }
        }

        should("throw InvalidTypeException when variable is not int") {
            val typeEnv = hashMapOf("v" to Type.BOOL)
            every { expressionMock.typecheck(any()) } returns Type.STRING

            val stmt = ReadFile(expressionMock, "v")
            shouldThrow<InvalidTypeException> {
                stmt.typecheck(typeEnv)
            }
        }

        should("throw InvalidTypeException when expression is not string") {
            val typeEnv = hashMapOf("v" to Type.INT)
            every { expressionMock.typecheck(any()) } returns Type.INT

            val stmt = ReadFile(expressionMock, "v")
            shouldThrow<InvalidTypeException> {
                stmt.typecheck(typeEnv)
            }
        }
    }

    context("ReadFile.toString") {
        should("return the readFile statement string representation") {
            every { expressionMock.toString() } returns "\"file.txt\""

            val stmt = ReadFile(expressionMock, "v")
            stmt.toString() shouldBe "readFile(\"file.txt\", v)"
        }
    }
})
