package com.github.dannful.core.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.github.dannful.core.domain.model.MediaDate
import com.github.dannful.core.domain.model.UserMedia
import com.github.dannful.core.domain.model.UserMediaStatus
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Entity(tableName = UserMediaEntity.TABLE_NAME)
@TypeConverters(UserMediaConverters::class)
data class UserMediaEntity(
    @PrimaryKey(autoGenerate = false) val userMediaId: Int,
    val title: String,
    val progress: Int,
    val status: UserMediaStatus,
    val userMediaStatus: Double,
    val mediaId: Int,
    val startedAt: MediaDate?,
    val completedAt: MediaDate?,
    val updatedAt: Int?
) {

    companion object {

        const val TABLE_NAME = "user_media"
    }

    fun toUserMedia(mediaEntity: MediaEntity) = UserMedia(
        id = userMediaId,
        title = title,
        progress = progress,
        status = status,
        score = userMediaStatus,
        completedAt = completedAt,
        startedAt = startedAt,
        media = mediaEntity.toMedia(),
        updatedAt = updatedAt
    )
}

class UserMediaConverters {

    @TypeConverter
    fun fromMediaDate(mediaDate: MediaDate?): String? {
        if (mediaDate == null)
            return null
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.set(Calendar.YEAR, mediaDate.year)
        calendar.set(Calendar.MONTH, mediaDate.month)
        calendar.set(Calendar.DAY_OF_MONTH, mediaDate.day)
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
    }

    @TypeConverter
    fun toMediaDate(mediaDate: String?): MediaDate? {
        if (mediaDate == null)
            return null
        val calendar = Calendar.getInstance()
        calendar.time = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(mediaDate)!!
        return MediaDate(
            year = calendar.get(Calendar.YEAR),
            month = calendar.get(Calendar.MONTH),
            day = calendar.get(Calendar.DAY_OF_MONTH)
        )
    }
}