/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.test.compose.junit5

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.junit4.createEmptyComposeRule
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

fun createEmptyComposeExtension(): AndroidComposeExtension {
    return AndroidComposeExtension(
        ruleFactory = { createEmptyComposeRule() }
    )
}

@SuppressLint("NewApi")
class AndroidComposeExtension @JvmOverloads internal constructor(
    private val ruleFactory: () -> ComposeTestRule = { createComposeRule() }
) :
    BeforeEachCallback,
    ParameterResolver,
    ComposeExtension {

    private var description: Description? = null

    override fun beforeEach(context: ExtensionContext) {
        description = Description.createTestDescription(
            context.testClass.orElse(this::class.java),
            context.displayName
        )
    }

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

    override fun runComposeTest(block: ComposeContentContext.() -> Unit) {
        ruleFactory().also { rule ->
            rule.apply(
                object : Statement() {
                    override fun evaluate() {
                        if (rule is ComposeContentContext) {
                            rule.block()
                        }
                    }
                },
                description
            ).evaluate()
        }
    }
}
