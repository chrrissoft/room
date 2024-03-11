package com.chrrissoft.room.shared.app

import com.chrrissoft.room.utils.Utils.debug

sealed interface ResState<out R> {
    object None : ResState<Nothing>

    object Loading : ResState<Nothing>

    data class Success<R>(val data: R) : ResState<R>

    data class Error(val throwable: Throwable?) : ResState<Nothing> {

        init {
            debug(throwable?.printStackTrace())
        }

        val message get() = throwable?.message ?: "Unknown error"
    }
}
