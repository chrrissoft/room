package com.chrrissoft.room.utils

import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Error
import com.chrrissoft.room.shared.app.ResState.Loading
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

object FlowUtils {
    @Suppress("FunctionName")
    fun <T> ResFlow(
        context: CoroutineContext = IO,
        block: suspend FlowCollector<ResState<T>>.() -> Unit
    ): Flow<ResState<T>> {
        return flow { emit(Loading).apply { block() } }.flowOn(context).catch { emit(Error(it)) }
    }
}
