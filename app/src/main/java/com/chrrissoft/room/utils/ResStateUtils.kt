package com.chrrissoft.room.utils

import androidx.compose.runtime.Composable
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Error
import com.chrrissoft.room.shared.app.ResState.Loading
import com.chrrissoft.room.shared.app.ResState.None
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.ui.entities.SnackbarData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.mapNotNull

object ResStateUtils {
    inline fun<T, T2> ResState<T>.map(success: (T) -> T2) : ResState<T2> {
        return when (this) {
            None -> None
            Loading -> Loading
            is Error -> Error(throwable)
            is Success -> {
                try {
                    Success(success(data))
                } catch (e: Throwable) {
                    Error(e)
                }
            }
        }
    }

    fun ResState<*>.asSnackbar() : SnackbarData.MessageType {
        return when (this) {
            None -> SnackbarData.MessageType.Success
            is Error -> SnackbarData.MessageType.Error
            Loading -> SnackbarData.MessageType.Success
            is Success -> SnackbarData.MessageType.Success
        }
    }

    fun<T> ResState<T>.getSuccess(): T? {
        return (this as? Success)?.data
    }

    suspend fun<T> Flow<ResState<T>>.lastSuccess(): T? {
        return (last() as Success).data
    }

    suspend fun<T> Flow<ResState<T>>.firstSuccess(): T {
        return (filterLoading().mapNotNull { (it as? Success)?.data }).first()
    }

    suspend fun<T> Flow<ResState<T>>.lastIsSuccess(): Boolean {
        return (last() is Success)
    }

    fun<T> Flow<ResState<T>>.filterLoading(): Flow<ResState<T>> {
        return filterNot { it is Loading }
    }

    @Composable
    fun <R> ResState<R>.text(prefix: String? = null, action: (R) -> String): String {
        val pre = if (prefix==null) "" else "$prefix: "
        return pre + when (this) {
            is Error -> "Error"
            Loading -> "Loading"
            None -> "None"
            is Success -> action(data)
        }
    }

}
