package me.dannly.tenkai.worker

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import me.dannly.core.R
import me.dannly.core.util.*
import me.dannly.core_ui.navigation.Destination
import me.dannly.notifications_data.paging.NotificationsPager
import me.dannly.notifications_domain.repository.NotificationsRepository
import me.dannly.tenkai.MainActivity
import kotlin.random.Random

@HiltWorker
class MediaNotificationsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val notificationsRepository: NotificationsRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val unreadNotificationCount =
            notificationsRepository.retrieveUnreadNotificationCount().getOrElse {
                return Result.failure(workDataOf(MEDIA_WORKER_ERROR_MESSAGE_PARAM_NAME to it.localizedMessage))
            }.takeUnless { it == 0 } ?: return Result.success()
        val perPage =
            unreadNotificationCount.coerceAtMost(NotificationsPager.NOTIFICATIONS_PER_PAGE)
        val retrievedNotifications =
            (1..(perPage / unreadNotificationCount)).map {
                notificationsRepository.retrieveNotifications(page = it, perPage = perPage).getOrElse { throwable ->
                    return Result.failure(workDataOf(MEDIA_WORKER_ERROR_MESSAGE_PARAM_NAME to throwable.localizedMessage))
                }
            }.flatten().mapNotNull { it.airingNotification }.takeUnless { it.isEmpty() }
                ?: return Result.success()
        retrievedNotifications.forEach { airingNotification ->
            startForegroundService(
                airingNotification.episode,
                airingNotification.mediaTitle,
                airingNotification.mediaId
            )
        }
        return Result.success(
            workDataOf(
                MEDIA_WORKER_SUCCESS_MEDIA_TITLE to retrievedNotifications.joinToString { it.mediaTitle },
                MEDIA_WORKER_SUCCESS_AIRED_EPISODE to retrievedNotifications.joinToString { it.episode.toString() },
                MEDIA_WORKER_SUCCESS_MEDIA_ID to retrievedNotifications.joinToString { it.mediaId.toString() }
            )
        )
    }

    private suspend fun startForegroundService(
        episode: Int,
        showTitle: String,
        showId: Int
    ) {
        setForeground(
            ForegroundInfo(
                Random.nextInt(),
                NotificationCompat.Builder(context, MEDIA_NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.drawable.tenkai_round)
                    .setContentText(context.getString(R.string.episode_out, episode, showTitle))
                    .setContentIntent(TaskStackBuilder.create(context).run {
                        addNextIntentWithParentStack(
                            Intent(
                                Intent.ACTION_VIEW,
                                Destination.AnimeActions.Info.route.withDeepLinkArguments(showId.toString())
                                    .toUri(),
                                context,
                                MainActivity::class.java
                            )
                        )
                        getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
                    })
                    .build()
            )
        )
    }
}