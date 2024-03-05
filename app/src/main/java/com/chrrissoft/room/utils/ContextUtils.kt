package com.chrrissoft.room.utils

import android.content.Context
import android.widget.Toast
import android.widget.Toast.makeText

object ContextUtils {
    fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        makeText(this, message, duration).show()
    }
}
