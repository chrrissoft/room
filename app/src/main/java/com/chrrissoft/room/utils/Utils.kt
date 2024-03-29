package com.chrrissoft.room.utils

import android.content.Context
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.utils.ContextUtils.toast
import java.util.UUID

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

    val uuid get() = UUID.randomUUID().toString()

    val Page.floatingIcon
        @Composable get() = remember(this) {
            when (this) {
                Page.LIST -> Icons.Rounded.Add
                Page.DETAIL -> Icons.Rounded.Save
            }
        }

    val List<*>.count get() = if (size == 1) "" else " (${size})"
}
