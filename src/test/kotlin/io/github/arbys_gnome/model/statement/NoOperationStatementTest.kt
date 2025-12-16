package io.github.arbys_gnome.model.statement

import io.github.arbys_gnome.model.type.Type
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class NoOperationStatementTest : ShouldSpec({
    context("NoOperationStatement.typecheck") {
        should("return the same typeEnv unchanged") {
            val typeEnv = hashMapOf("x" to Type.INT, "y" to Type.BOOL)
            val stmt = NoOperationStatement()

            stmt.typecheck(typeEnv) shouldBe typeEnv
        }

        should("return empty typeEnv when given empty typeEnv") {
            val typeEnv = hashMapOf<String, Type>()
            val stmt = NoOperationStatement()

            stmt.typecheck(typeEnv) shouldBe typeEnv
        }
    }

    context("NoOperationStatement.toString") {
        should("return NoOp") {
            val stmt = NoOperationStatement()
            stmt.toString() shouldBe "NoOp"
        }
    }
})
