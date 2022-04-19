/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.di

import care.data4life.integration.app.data.AuthService
import care.data4life.integration.app.data.DataContract.Service
import care.data4life.integration.app.ui.welcome.WelcomeContract.ViewModel
import care.data4life.integration.app.ui.welcome.WelcomeViewModel

object Di : DiContract {

    object Ui : DiContract.Ui {
        override val welcomeViewModel: ViewModel
            get() = WelcomeViewModel(Data.authService)
    }

    object Data : DiContract.Data {
        override val authService: Service.Auth by lazy { AuthService() }
    }

    override val ui: DiContract.Ui = Ui
    override val data: DiContract.Data = Data
}
