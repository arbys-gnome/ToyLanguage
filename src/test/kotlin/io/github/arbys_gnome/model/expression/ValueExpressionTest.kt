package io.github.arbys_gnome.model.expression

import io.github.arbys_gnome.model.type.RefType
import io.github.arbys_gnome.model.type.Type
import io.github.arbys_gnome.model.value.BoolValue
import io.github.arbys_gnome.model.value.IntValue
import io.github.arbys_gnome.model.value.RefValue
import io.github.arbys_gnome.model.value.StringValue
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

class ValueExpressionTest : ShouldSpec({
    context("ValueExpression.evaluate") {
        should("return the correct value") {
            val testCases = table(
                headers("value"),
                row(IntValue(10)),
                row(StringValue("str")),
                row(BoolValue(true)),
                row(RefValue(1, Type.INT)),
            )
            forAll(testCases) { value ->
                val expr = ValueExpression(value)
                expr.evaluate(null, null) shouldBe value
            }
        }
    }

    context("ValueExpression.typecheck") {
        should("return the correct type") {
            val testCases = table(
                headers("value", "expectedType"),
                row(IntValue(10), Type.INT),
                row(StringValue("str"), Type.STRING),
                row(BoolValue(true), Type.BOOL),
                row(RefValue(1, Type.INT), RefType(Type.INT)),
            )
            forAll(testCases) { value, expectedType ->
                val expr = ValueExpression(value)
                expr.typecheck(null) shouldBe expectedType
            }
        }
    }

    context("ValueExpression.toString") {
        should("return the value as a string") {
            val testCases = table(
                headers("value"),
                row(IntValue(10)),
                row(StringValue("str")),
                row(BoolValue(true)),
                row(RefValue(1, Type.INT)),
            )
            forAll(testCases) { value ->
                val expr = ValueExpression(value)
                expr.toString() shouldBe value.toString()
            }
        }
    }
})
