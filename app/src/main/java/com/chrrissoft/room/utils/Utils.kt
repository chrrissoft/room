package com.chrrissoft.room.utils

import android.content.Context
import android.util.Log
import com.chrrissoft.room.utils.ContextUtils.toast

object Utils {
    @Suppress("FunctionName")
    inline fun <R> Try(
        ctx: Context? = null,
        block: () -> R?
    ): R? {
        return try {
            block()
        } catch (e: Throwable) {
            e.printStackTrace()
            val error = e.message ?: "Unknown Error"
            ctx?.toast(message = "Error $error")
            null
        }
    }

    fun Any.debug(message: Any?, tag: Any = this) {
        Log.d(tag::class.java.simpleName, message.toString())
    }
}
