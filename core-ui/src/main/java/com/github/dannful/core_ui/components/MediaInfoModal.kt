package com.github.dannful.core_ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.github.dannful.core.data.model.QueryInput
import com.github.dannful.core.domain.model.Media
import com.github.dannful.core.domain.model.MediaDate
import com.github.dannful.core.domain.model.UserMediaStatus
import com.github.dannful.core.domain.model.UserMediaUpdate
import com.github.dannful.core.domain.model.coroutines.UserScoreFormat
import com.github.dannful.core_ui.LocalSpacingProvider
import com.github.dannful.core_ui.R
import com.github.dannful.core_ui.util.UiConstants
import com.github.dannful.core_ui.util.domainUserStatus
import kotlin.math.roundToInt

data class MediaInfo(
    val media: Media,
    val mediaUpdate: UserMediaUpdate,
    val userScoreFormat: UserScoreFormat
)

@Composable
fun MediaInfoModal(
    modifier: Modifier = Modifier,
    mediaInfo: MediaInfo,
    onDismiss: () -> Unit,
    onEditInfo: (UserMediaUpdate) -> Unit,
    onSend: () -> Unit
) {
    var editing by rememberSaveable {
        mutableStateOf(false)
    }
    if (editing)
        EditEntry(
            mediaInfo = mediaInfo,
            onDismissEditDialog = {
                editing = false
            },
            editInfo = onEditInfo,
            onSend = onSend,
            onDismissDialog = onDismiss,
        )
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = modifier
                    .height(UiConstants.MODAL_HEIGHT)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                mediaInfo.media.bannerUrl?.let { bannerUrl ->
                    AsyncImage(
                        model = bannerUrl,
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    )
                } ?: Image(
                    painter = painterResource(id = R.drawable.not_found),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                )
                IconButton(onClick = {
                    editing = true
                }, modifier = Modifier.align(Alignment.End)) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = stringResource(R.string.edit)
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .padding(LocalSpacingProvider.current.medium),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(LocalSpacingProvider.current.small)
                ) {
                    OutlinedTextField(
                        value = HtmlCompat.fromHtml(
                            mediaInfo.media.description
                                ?: stringResource(R.string.no_description_provided),
                            HtmlCompat.FROM_HTML_MODE_COMPACT
                        ).toString(),
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.weight(1f),
                        label = {
                            Text(text = stringResource(R.string.description))
                        },
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Justify
                        )
                    )
                    OutlinedTextField(
                        value = mediaInfo.media.genres.joinToString(),
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text(text = stringResource(R.string.genres))
                        },
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Justify
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun EditEntry(
    mediaInfo: MediaInfo,
    onDismissEditDialog: () -> Unit,
    onDismissDialog: () -> Unit,
    editInfo: (UserMediaUpdate) -> Unit,
    onSend: () -> Unit,
) {
    var scoreText by rememberSaveable {
        mutableStateOf(mediaInfo.mediaUpdate.score.value?.toString().orEmpty())
    }
    var progressText by rememberSaveable {
        mutableStateOf(mediaInfo.mediaUpdate.progress.value?.toString().orEmpty())
    }
    Dialog(onDismissRequest = onDismissEditDialog) {
        Surface(
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(LocalSpacingProvider.current.small),
                modifier = Modifier.padding(LocalSpacingProvider.current.small),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StatusSelect(
                    selected = (mediaInfo.mediaUpdate.status.value
                        ?: UserMediaStatus.CURRENT).ordinal,
                    onSelected = {
                        editInfo(
                            mediaInfo.mediaUpdate.copy(
                                status = QueryInput.present(
                                    UserMediaStatus.entries[it]
                                )
                            )
                        )
                        if (it == UserMediaStatus.COMPLETED.ordinal)
                            editInfo(
                                mediaInfo.mediaUpdate.copy(
                                    completedAt = QueryInput.present(
                                        MediaDate.today
                                    )
                                )
                            )
                    }
                )
                ProgressEdit(
                    userMediaUpdate = mediaInfo.mediaUpdate,
                    media = mediaInfo.media,
                    onProgressUpdate = {
                        editInfo(mediaInfo.mediaUpdate.copy(progress = QueryInput.present(it)))
                    },
                    onProgressTextUpdate = {
                        progressText = it
                    },
                    progressText = progressText
                )
                ScoreEdit(
                    scoreFormat = mediaInfo.userScoreFormat,
                    onScoreUpdate = {
                        editInfo(mediaInfo.mediaUpdate.copy(score = QueryInput.present(it)))
                    }, onScoreTextUpdate = {
                        scoreText = it
                    }, scoreText = scoreText
                )
                DateSelector(
                    label = stringResource(id = R.string.started_at),
                    initialValue = mediaInfo.mediaUpdate.startedAt.value?.millis
                ) {
                    if (it == null)
                        return@DateSelector
                    editInfo(
                        mediaInfo.mediaUpdate.copy(
                            startedAt = QueryInput.present(
                                MediaDate.fromMillis(
                                    it
                                )
                            )
                        )
                    )
                }
                DateSelector(
                    label = stringResource(id = R.string.completed_at),
                    initialValue = mediaInfo.mediaUpdate.completedAt.value?.millis
                ) {
                    if (it == null)
                        return@DateSelector
                    editInfo(
                        mediaInfo.mediaUpdate.copy(
                            completedAt = QueryInput.present(
                                MediaDate.fromMillis(
                                    it
                                )
                            )
                        )
                    )
                }
                Button(
                    onClick = {
                        onSend()
                        onDismissEditDialog()
                        onDismissDialog()
                    },
                    enabled = mediaInfo.media.isProgressValid(progressText) && mediaInfo.isScoreValid(
                        scoreText
                    )
                ) {
                    Text(text = stringResource(R.string.send))
                }
            }
        }
    }
}

@Composable
private fun ScoreEdit(
    scoreText: String,
    scoreFormat: UserScoreFormat,
    onScoreUpdate: (Double) -> Unit,
    onScoreTextUpdate: (String) -> Unit
) {
    OutlinedTextField(
        value = scoreText,
        onValueChange = {
            onScoreTextUpdate(it.replace(scoreFormat.disallowedRegexFilter, ""))
            val newScore = it.toDoubleOrNull() ?: return@OutlinedTextField
            if (newScore in scoreFormat.valueRange)
                onScoreUpdate(newScore)
        },
        isError = !isScoreValid(scoreText, scoreFormat),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        label = {
            Text(text = stringResource(R.string.score))
        }
    )
}

@Composable
private fun ProgressEdit(
    progressText: String,
    userMediaUpdate: UserMediaUpdate,
    media: Media,
    onProgressUpdate: (Int) -> Unit,
    onProgressTextUpdate: (String) -> Unit
) {
    val range = 0f..(media.nextEpisode?.toFloat()?.minus(1) ?: media.episodes?.toFloat()
    ?: 0f)
    OutlinedTextField(
        value = progressText,
        onValueChange = {
            onProgressTextUpdate(it.replace("\\D".toRegex(), ""))
            val progress = it.toIntOrNull() ?: return@OutlinedTextField
            if (media.isProgressValid(progress))
                onProgressUpdate(progress)
        },
        label = {
            Text(text = stringResource(R.string.progress))
        },
        isError = progressText.toIntOrNull() == null || !media.isProgressValid(progressText.toInt()),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
    )
    Slider(
        onValueChange = {
            val intValue = it.roundToInt()
            onProgressUpdate(intValue)
            onProgressTextUpdate(intValue.toString())
        },
        value = userMediaUpdate.progress.value?.toFloat() ?: 0f,
        valueRange = range,
        steps = (range.endInclusive - range.start).toInt(),
    )
}

@Composable
private fun StatusSelect(
    selected: Int,
    onSelected: (Int) -> Unit
) {
    val statusList = domainUserStatus()
    LazyRow(horizontalArrangement = Arrangement.spacedBy(LocalSpacingProvider.current.small)) {
        items(count = statusList.size, key = {
            it
        }) {
            InputChip(selected = selected == it, onClick = {
                onSelected(it)
            }, label = {
                Text(text = statusList[it])
            })
        }
    }
}

private fun Media.isProgressValid(progress: Int) =
    progress in 0..(nextEpisode?.minus(
        1
    ) ?: episodes ?: 0)

private fun Media.isProgressValid(progress: String) =
    progress.toIntOrNull() != null && isProgressValid(progress.toInt())

private fun MediaInfo.isScoreValid(score: String) =
    isScoreValid(score, userScoreFormat)

fun isScoreValid(score: String, userScoreFormat: UserScoreFormat) =
    (score.matches(userScoreFormat.allowedRegexFilter) && score.toDoubleOrNull() != null &&
            score.toDouble() in userScoreFormat.valueRange)