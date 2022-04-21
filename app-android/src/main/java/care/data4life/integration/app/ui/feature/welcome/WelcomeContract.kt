/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.feature.welcome

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface WelcomeContract {

    interface ViewModel {
        val events: Flow<Event>
        val state: StateFlow<State>

        fun onAction(action: Action)
    }

    data class State(
        val isLoading: Boolean = false,
    ) : care.data4life.integration.app.ui.mvi.MviContract.State

    sealed class Event {
        object NavigateToLogin : Event()
        object NavigateToHome : Event()
    }

    sealed class Action {
        object LoginClicked : Action()
        object LoginConfirmed : Action()
    }
}
