package com.github.dannful.core.domain.model

import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

data class MediaDate(
    val day: Int,
    val month: Int,
    val year: Int
) {

    companion object {

        val today: MediaDate
            get() {
                val calendar = Calendar.getInstance(Locale.getDefault())
                calendar.timeZone = TimeZone.getTimeZone("UTC")
                return MediaDate(
                    day = calendar.get(Calendar.DAY_OF_MONTH),
                    month = calendar.get(Calendar.MONTH) + 1,
                    year = calendar.get(Calendar.YEAR)
                )
            }
    }

    val millis: Long
        get() {
            val calendar = Calendar.getInstance(Locale.getDefault())
            calendar.timeZone = TimeZone.getTimeZone("UTC")
            calendar.set(Calendar.DAY_OF_MONTH, day)
            calendar.set(Calendar.MONTH, month - 1)
            calendar.set(Calendar.YEAR, year)
            return calendar.timeInMillis
        }
}
