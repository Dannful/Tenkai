package me.dannly.profile_presentation.activity.components

import androidx.compose.foundation.clickable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.paging.compose.LazyPagingItems
import me.dannly.core.R
import me.dannly.core_ui.components.ImageCard
import me.dannly.profile_domain.model.activity.ListActivity
import me.dannly.profile_domain.model.activity.RetrievedActivity
import me.dannly.profile_presentation.activity.util.SwipeDirection

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListActivity(
    listActivity: ListActivity?,
    lazyPagingItems: LazyPagingItems<RetrievedActivity>,
    onListActivityClick: (Int) -> Unit
) {
    if (listActivity != null) {
        val swipeableState = rememberSwipeableState(initialValue = SwipeDirection.CENTER)
        ActivityDelete(
            swipeableState = swipeableState,
            activityId = listActivity.id,
            lazyPagingItems = lazyPagingItems
        )
        ImageCard(
            rowModifier = Modifier.getSwipeableModifier(swipeableState),
            modifier = Modifier.clickable {
                onListActivityClick(listActivity.mediaId)
            },
            imageURL = listActivity.mediaImage,
            title = listActivity.mediaTitle
        ) {
            listActivity.status?.let {
                Text(
                    textAlign = TextAlign.Justify,
                    text =
                    when {
                        it.contains("watched") -> stringResource(
                            id = R.string.watched_episode,
                            listActivity.progress.orEmpty(),
                            listActivity.mediaTitle
                        )
                        it.contains("plans") -> stringResource(
                            id =
                            R.string.activity_plans_to_watch, listActivity.mediaTitle
                        )
                        it.contains("completed") -> stringResource(
                            id = R.string.finished_watching, listActivity.mediaTitle
                        )
                        it.contains("paused") -> stringResource(
                            id = R.string.paused_watching,
                            listActivity.mediaTitle
                        )
                        it.contains("dropped") -> stringResource(
                            id = R.string.dropped_show,
                            listActivity.mediaTitle
                        )
                        it.contains("rewatching") -> stringResource(
                            id = R.string.rewatching_show,
                            listActivity.mediaTitle
                        )
                        else -> ""
                    }
                )
            }
        }
    }
}