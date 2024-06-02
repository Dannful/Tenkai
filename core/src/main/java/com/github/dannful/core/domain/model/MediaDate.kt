package com.github.dannful.core.domain.model

import kotlinx.serialization.Serializable
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@Serializable
data class MediaDate(
    val day: Int,
    val month: Int,
    val year: Int
) {

    companion object {

        val today: MediaDate
            get() {
                val calendar = Calendar.getInstance(Locale.getDefault())
                return MediaDate(
                    day = calendar.get(Calendar.DAY_OF_MONTH),
                    month = calendar.get(Calendar.MONTH),
                    year = calendar.get(Calendar.YEAR)
                )
            }

        fun fromMillis(millis: Long): MediaDate {
            val calendar = Calendar.getInstance(Locale.getDefault())
            calendar.timeZone = TimeZone.getTimeZone("UTC")
            calendar.timeInMillis = millis
            return MediaDate(
                day = calendar.get(Calendar.DAY_OF_MONTH),
                month = calendar.get(Calendar.MONTH),
                year = calendar.get(Calendar.YEAR)
            )
        }
    }

    val millis: Long
        get() {
            val calendar = Calendar.getInstance(Locale.getDefault())
            calendar.set(Calendar.DAY_OF_MONTH, day)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.YEAR, year)
            calendar.timeZone = TimeZone.getTimeZone("UTC")
            return calendar.timeInMillis
        }
}