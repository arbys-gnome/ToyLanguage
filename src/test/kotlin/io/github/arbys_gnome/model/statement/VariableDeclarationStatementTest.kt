package io.github.arbys_gnome.model.statement

import io.github.arbys_gnome.model.type.Type
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class VariableDeclarationStatementTest : ShouldSpec({
    context("VariableDeclarationStatement.typecheck") {
        should("add variable to typeEnv and return updated typeEnv") {
            val typeEnv = hashMapOf("x" to Type.INT)
            val stmt = VariableDeclarationStatement(Type.BOOL, "y")

            val result = stmt.typecheck(typeEnv)

            result["y"] shouldBe Type.BOOL
            result["x"] shouldBe Type.INT
        }

        should("work with empty typeEnv") {
            val typeEnv = hashMapOf<String, Type>()
            val stmt = VariableDeclarationStatement(Type.INT, "x")

            val result = stmt.typecheck(typeEnv)

            result["x"] shouldBe Type.INT
            result.size shouldBe 1
        }

        should("work with RefType") {
            val typeEnv = hashMapOf<String, Type>()
            val refType = io.github.arbys_gnome.model.type.RefType(Type.INT)
            val stmt = VariableDeclarationStatement(refType, "v")

            val result = stmt.typecheck(typeEnv)

            result["v"] shouldBe refType
        }
    }

    context("VariableDeclarationStatement.toString") {
        should("return the variable declaration string representation") {
            val stmt = VariableDeclarationStatement(Type.INT, "x")
            stmt.toString() shouldBe "int x"
        }

        should("work with bool type") {
            val stmt = VariableDeclarationStatement(Type.BOOL, "flag")
            stmt.toString() shouldBe "bool flag"
        }
    }
})
