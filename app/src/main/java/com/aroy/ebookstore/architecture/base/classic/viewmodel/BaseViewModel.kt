package com.aroy.ebookstore.architecture.base.classic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * A generic base class for implementing the MVI (Model–View–Intent) pattern in ViewModels.
 *
 * This class provides three generic type parameters:
 * - [VS] : ViewState — represents the immutable state of the UI. Defaults to [Any].
 * - [E]  : Event — represents one-time events such as navigation, toast, or SnackBar. Defaults to [Any].
 *
 * ### Features:
 * - Maintains a [StateFlow] of the current [VS] (ViewState).
 * - Provides a [Channel] for one-time [E] (Events).
 * - Offers helper methods to update state, send events, and process intents.
 *
 * ### Usage:
 * Subclass this ViewModel and:
 * - Define your own [VS] an [E] types.
 * - Use [setState] to update the UI state.
 * - Use [sendEvent] to emit one-time events.
 *
 * @param initialState The initial [VS] (ViewState) to start with.
 */
abstract class BaseViewModel<VS : Any, E : Any>(
    initialState: VS
) : ViewModel() {

    /** Backing state flow holding the current [VS]. */
    private val _viewState = MutableStateFlow(initialState)

    /** Public immutable state flow for observing UI state. */
    val viewState: StateFlow<VS> = _viewState.asStateFlow()

    /** Channel for one-time [E] (Events). */
    private val _eventChannel = Channel<E>(Channel.Factory.BUFFERED)

    /** Flow for observing one-time events. */
    val events: Flow<E> = _eventChannel.receiveAsFlow()

    /**
     * Update the current [VS] (ViewState) by applying a reducer function.
     *
     * @param reducer A function that takes the current state and returns a new state.
     */
    protected fun setState(reducer: VS.() -> VS) {
        _viewState.update { it.reducer() }
    }

    /**
     * Send a one-time [E] (Event) to the UI.
     *
     * @param event The event to send.
     */
    protected fun sendEvent(event: E) {
        viewModelScope.launch {
            _eventChannel.send(event)
        }
    }
}