package com.github.dannful.core.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.github.dannful.core.domain.model.Media
import com.github.dannful.core.domain.model.MediaStatus
import com.github.dannful.core.domain.model.MediaTitle
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Entity(tableName = MediaEntity.TABLE_NAME)
@TypeConverters(MediaConverters::class)
data class MediaEntity(
    @PrimaryKey(autoGenerate = false) val mediaId: Int,
    val description: String?,
    val genres: List<String>,
    val bannerUrl: String?,
    val coverImageUrl: String?,
    val episodes: Int?,
    val nextEpisode: Int?,
    val timeUntilNextEpisode: Int?,
    val mediaStatus: MediaStatus,
    val titles: MediaTitle,
    val synonyms: List<String>
) {

    companion object {
        const val TABLE_NAME = "media"
    }

    fun toMedia() = Media(
        id = mediaId,
        description = description,
        genres = genres,
        bannerUrl = bannerUrl,
        coverImageUrl = coverImageUrl,
        episodes = episodes,
        nextEpisode = nextEpisode,
        timeUntilNextEpisode = timeUntilNextEpisode,
        status = mediaStatus,
        titles = titles,
        synonyms = synonyms
    )
}

fun Media.toEntity() = MediaEntity(
    mediaId = id,
    description = description,
    genres = genres,
    bannerUrl = bannerUrl,
    coverImageUrl = coverImageUrl,
    episodes = episodes,
    nextEpisode = nextEpisode,
    timeUntilNextEpisode = timeUntilNextEpisode,
    mediaStatus = status,
    titles = titles,
    synonyms = synonyms
)

class MediaConverters {

    @TypeConverter
    fun fromStringList(genres: List<String>) = Json.encodeToString(genres)

    @TypeConverter
    fun toStringList(genres: String) = Json.decodeFromString<List<String>>(genres)

    @TypeConverter
    fun fromTitle(title: MediaTitle) = Json.encodeToString(title)

    @TypeConverter
    fun toTitle(title: String) = Json.decodeFromString<MediaTitle>(title)
}
