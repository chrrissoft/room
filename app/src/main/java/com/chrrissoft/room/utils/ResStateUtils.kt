package com.chrrissoft.room.utils

import androidx.compose.runtime.Composable
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Error
import com.chrrissoft.room.shared.app.ResState.Loading
import com.chrrissoft.room.shared.app.ResState.None
import com.chrrissoft.room.shared.app.ResState.Success

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

    fun<T> ResState<T>.getSuccess(): T? {
        return (this as? Success)?.data
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
