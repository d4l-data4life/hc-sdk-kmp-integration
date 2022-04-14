/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.test.compose

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.junit.runner.Description
import org.junit.runners.model.Statement

fun createComposeExtension(): ComposeExtension = createAndroidComposeExtension<ComponentActivity>()

inline fun <reified A : ComponentActivity> createAndroidComposeExtension(): AndroidComposeExtension {
    return createAndroidComposeExtension(A::class.java)
}

fun <A : ComponentActivity> createAndroidComposeExtension(
    activityClass: Class<A>
): AndroidComposeExtension {
    return AndroidComposeExtension(
        ruleFactory = { createAndroidComposeRule(activityClass) }
    )
}

@SuppressLint("NewApi")
class AndroidComposeExtension
@JvmOverloads
internal constructor(
    private val ruleFactory: () -> ComposeContentTestRule = { createComposeRule() }
) :
    BeforeEachCallback,
    ParameterResolver,
    ComposeExtension {

    private var description: Description? = null

    /* BeforeEachCallback */

    override fun beforeEach(context: ExtensionContext) {
        description = Description.createTestDescription(
            context.testClass.orElse(this::class.java),
            context.displayName
        )
    }

    /* ParameterResolver */

    override fun supportsParameter(
        parameterContext: ParameterContext,
        extensionContext: ExtensionContext
    ): Boolean {
        return ComposeExtension::class.java.isAssignableFrom(parameterContext.parameter.type)
    }

    override fun resolveParameter(
        parameterContext: ParameterContext,
        extensionContext: ExtensionContext
    ): Any {
        return this
    }

    /* ComposeExtension */

    override fun runComposeTest(block: ComposeContext.() -> Unit) {
        ruleFactory().also { rule ->
            rule.apply(object : Statement() {
                override fun evaluate() {
                    rule.block()
                }
            }, description).evaluate()
        }
    }
}
