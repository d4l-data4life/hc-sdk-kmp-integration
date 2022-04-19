/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.di

import care.data4life.integration.app.data.DataContract.Service
import care.data4life.integration.app.data.DataContract.Wrapper
import care.data4life.integration.app.ui.welcome.WelcomeContract

interface DiContract {

    val ui: Ui
    val data: Data

    interface Ui {
        val welcomeViewModel: WelcomeContract.ViewModel
    }

    interface Data {
        val d4lClient: Wrapper.D4LClient

        val authService: Service.Auth
    }
}
