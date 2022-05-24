/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.di

import care.data4life.integration.app.data.AuthService
import care.data4life.integration.app.data.DataContract.Service
import care.data4life.integration.app.data.DataContract.Wrapper
import care.data4life.integration.app.data.wrapper.D4LClientWrapper
import care.data4life.integration.app.ui.feature.welcome.WelcomeContract.ViewModel
import care.data4life.integration.app.ui.feature.welcome.WelcomeViewModel

object Di : DiContract {
    object Ui : DiContract.Ui {
        override val welcomeViewModel: ViewModel
            get() = WelcomeViewModel(Data.authService)
    }

    object Data : DiContract.Data {
        override val d4lClient: Wrapper.D4LClient by lazy { D4LClientWrapper() }
        override val authService: Service.Auth by lazy { AuthService(d4lClient) }
    }

    override val ui: DiContract.Ui = Ui
    override val data: DiContract.Data = Data
}
