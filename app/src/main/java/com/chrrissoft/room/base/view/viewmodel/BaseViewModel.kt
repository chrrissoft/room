package com.chrrissoft.room.base.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Error
import com.chrrissoft.room.shared.app.ResState.Loading
import com.chrrissoft.room.shared.app.ResState.None
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.ui.entities.SnackbarData.MessageType
import com.chrrissoft.room.utils.ComposeUtils.show
import com.chrrissoft.room.utils.ResStateUtils.toMessageType
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Suppress("UNCHECKED_CAST")
abstract class BaseViewModel<H : BaseEventHandler, S : BaseState> : ViewModel() {
    private val handler = CoroutineExceptionHandler { _, e -> showSnackbar(Error(e)) }

    protected abstract val eventHandler: BaseEventHandler

    protected abstract val mutableState: MutableStateFlow<S>
    abstract val stateFlow: StateFlow<S>
    protected val state get() = run { stateFlow.value }

    protected val scope = viewModelScope.plus(handler)

    fun handleEvent(event: BaseEvent<H>) {
        scope.launch { event.resolve(eventHandler as H) }
    }

    protected fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ) = scope.launch(context, start, block)

    protected fun showSnackbar(
        res: ResState<*>,
        loading: String = "Saving",
        success: String = "Saved",
        error: String = (res as? Error)?.message ?: "Error"
    ) {
        updateSnackbarType(res.toMessageType())
        val message = when (res) {
            None -> error
            Loading -> loading
            is Error -> error
            is Success -> success
        }
        state.snackbar.state.show(scope, message)
    }

    abstract fun updateSnackbarType(messageType: MessageType)
}
