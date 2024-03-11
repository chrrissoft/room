package com.chrrissoft.room.utils

import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Error
import com.chrrissoft.room.shared.app.ResState.Loading
import com.chrrissoft.room.shared.app.ResState.None
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.ui.entities.SnackbarData

object ResStateUtils {
    inline fun <T, T2> ResState<T>.map(success: (T) -> T2): ResState<T2> {
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

    fun <T> ResState<T>.getSuccess(): T? {
        return (this as? Success)?.data
    }

    fun ResState<*>.toMessageType(): SnackbarData.MessageType {
        return if (this is Error) SnackbarData.MessageType.Error
        else SnackbarData.MessageType.Success
    }
}
