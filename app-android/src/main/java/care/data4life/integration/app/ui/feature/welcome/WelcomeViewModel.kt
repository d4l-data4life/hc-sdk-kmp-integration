/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.feature.welcome

import care.data4life.integration.app.data.DataContract.Service
import care.data4life.integration.app.ui.feature.welcome.WelcomeContract
import care.data4life.integration.app.ui.feature.welcome.WelcomeContract.Action
import care.data4life.integration.app.ui.feature.welcome.WelcomeContract.Action.LoginClicked
import care.data4life.integration.app.ui.feature.welcome.WelcomeContract.Action.LoginConfirmed
import care.data4life.integration.app.ui.feature.welcome.WelcomeContract.Event
import care.data4life.integration.app.ui.feature.welcome.WelcomeContract.Event.NavigateToHome
import care.data4life.integration.app.ui.feature.welcome.WelcomeContract.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WelcomeViewModel(
    private val service: Service.Auth
) : WelcomeContract.ViewModel {
    private val coroutineScope = MainScope()

    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    override val state = _state.asStateFlow()

    private val _events = Channel<Event>(Channel.BUFFERED)
    override val events = _events.receiveAsFlow()

    override fun onAction(action: Action) {
        when (action) {
            LoginConfirmed -> {
                coroutineScope.launch {
                    _state.value = _state.value.copy(isLoading = true)
                    withContext(Dispatchers.IO) { }

                    _events.send(NavigateToHome)
                    _state.value = _state.value.copy(isLoading = false)
                }
            }
            LoginClicked -> TODO()
        }
    }
}
