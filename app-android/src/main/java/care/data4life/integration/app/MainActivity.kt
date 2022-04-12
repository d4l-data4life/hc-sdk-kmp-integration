/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // TODO
        }
    }

    // TODO remove from test!!
    override fun finish() {
        // ignore so that Android test runner can't kill activity after each test
    }

    // TODO remove from test!!
    fun explicitFinish() {
        super.finish()
    }
}
