package com.chrrissoft.room.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.work.Operation
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Error
import com.chrrissoft.room.shared.app.ResState.Loading
import com.chrrissoft.room.shared.app.ResState.None
import com.chrrissoft.room.shared.app.ResState.Success
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

object FlowUtils {
    @Suppress("FunctionName")
    fun <T> ResFlow(
        context: CoroutineContext = IO,
        block: suspend FlowCollector<ResState<T>>.() -> Unit
    ): Flow<ResState<T>> {
        return flow { emit(Loading).apply { block() } }.flowOn(context).catch { emit(Error(it)) }
    }

    @Suppress("FunctionName")
    fun <T> ResCallbackFlow(
        context: CoroutineContext = IO,
        block: suspend ProducerScope<ResState<T>>.(Mutex) -> Unit
    ): Flow<ResState<T>> {
        return callbackFlow { send(Loading).apply { block(Mutex()) } }
            .flowOn(context)
            .catch { emit(Error(it)) }
    }


    fun <T> ProducerScope<T>.trySendBlockingClose(data: T, delay: Long = 3000) {
        trySendBlocking(data)
        launch {
            delay(delay)
            close()
        }
    }

    private suspend fun <T> ProducerScope<T>.delayClose(delay: Long = 3000) {
        delay(delay)
        close()
    }


    fun <T> LiveData<T>.asCallbackFlow(closeWhen: (T) -> Boolean = { false }): Flow<T> {
        return callbackFlow {
            val observer = Observer<T> {
                if (closeWhen(it)) trySendBlockingClose(it) else trySendBlocking(it)
            }
            withContext(Main) { observeForever(observer) }
            awaitClose { launch(Main) { withContext(Main) { removeObserver(observer) } } }
        }
    }

    fun Flow<Operation.State>.toResState(): Flow<ResState<Any>> {
        return map {
            when (it) {
                is Operation.State.SUCCESS -> Success(0)
                is Operation.State.FAILURE -> Error(null)
                is Operation.State.IN_PROGRESS -> Loading
                else -> Error(null)
            }
        }
    }


    fun<T, R> Flow<ResState<T>>.resTransform(
        ctx: CoroutineContext = IO,
        block: suspend FlowCollector<ResState<R>>.(T) -> Unit
    ): Flow<ResState<R>> {
        return transform {
            when (it) {
                None -> emit(None)
                Loading -> emit(Loading)
                is Error -> emit(Error(it))
                is Success -> block(it.data)
            }
        }.flowOn(ctx).catch { emit(Error(it)) }
    }
}
