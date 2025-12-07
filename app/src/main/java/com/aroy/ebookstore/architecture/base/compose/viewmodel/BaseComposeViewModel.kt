package com.aroy.ebookstore.architecture.base.compose.viewmodel

import androidx.lifecycle.viewModelScope
import com.aroy.ebookstore.architecture.base.classic.viewmodel.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

/**
 * Created by Amit Roy on Date : 04/12/25
 *
 * A generic base class for implementing the MVI (Model–View–Intent) pattern in ViewModels.
 *
 * This class provides three generic type parameters:
 * - [VS] : ViewState — represents the immutable state of the UI. Defaults to [Any].
 * - [E]  : Event — represents one-time events such as navigation, toast, or SnackBar. Defaults to [Any].
 * - [I]  : Intent — represents user actions or process intents that drive business logic. Defaults to [Any].
 *
 * ### Features:
 * - Maintains a [StateFlow] of the current [VS] (ViewState).
 * - Provides a [Channel] for one-time [E] (Events).
 * - Provides a [Channel] for processing [I] (Intents).
 * - Offers helper methods to update state, send events, and process intents.
 *
 * ### Usage:
 * Subclass this ViewModel and:
 * - Define your own [VS], [E], and [I] types.
 * - Call [processIntent] from the UI layer to dispatch user actions.
 *
 * @param initialState The initial [VS] (ViewState) to start with.
 */
abstract class BaseComposeViewModel<VS : Any, E : Any, I : Any>(
    initialState: VS
): BaseViewModel<VS, E>(initialState = initialState) {

    /** Channel for processing [I] (Intents). */
    private val _intentChannel = Channel<I>(Channel.Factory.UNLIMITED)

    init {
        // Collect intents and process them
        viewModelScope.launch {
            _intentChannel.consumeAsFlow().collect { intent ->
                handleIntent(intent)
            }
        }
    }

    /**
     * Dispatch a user [I] (Intent) to be processed by [handleIntent].
     *
     * @param intent The intent to process.
     */
    fun processIntent(intent: I) {
        viewModelScope.launch {
            _intentChannel.send(intent)
        }
    }

    /**
     * Handle incoming [I] (Intents).
     *
     * Subclasses must implement this to define how each intent should be processed.
     *
     * @param intent The intent to handle.
     */
    protected abstract suspend fun handleIntent(intent: I)
}