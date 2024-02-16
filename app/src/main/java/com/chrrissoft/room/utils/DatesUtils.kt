package com.chrrissoft.room.utils

import org.threeten.bp.Instant.ofEpochMilli
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneId.systemDefault

object DatesUtils {
    fun Long.asLocalDate(id: ZoneId = systemDefault()): LocalDate = run {
        val instant = ofEpochMilli((this))
        instant.atZone(id).toLocalDate()
    }
}
