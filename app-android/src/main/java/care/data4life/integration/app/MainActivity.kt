/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import care.data4life.integration.app.ui.IntegrationApp
import care.data4life.integration.app.ui.theme.IntegrationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            IntegrationTheme {
                IntegrationApp()
            }
        }
    }

    // TODO remove from test!!
    fun explicitFinish() {
        super.finish()
    }
}
