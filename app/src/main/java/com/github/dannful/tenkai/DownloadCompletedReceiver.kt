package com.github.dannful.tenkai

import android.app.DownloadManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.os.Build
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File


class DownloadCompletedReceiver : BroadcastReceiver() {

    private lateinit var downloadManager: DownloadManager

    override fun onReceive(context: Context?, intent: Intent?) {
        downloadManager = context?.getSystemService(DownloadManager::class.java) ?: return
        if (intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
            if (id != -1L) {
                val file = File(
                    Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS
                    ), context.getString(R.string.new_apk_name)
                )
                /*if (android.os.Build.VERSION.SDK_INT >= 29) {
                    val newIntent = Intent(Intent.ACTION_INSTALL_PACKAGE);
                    newIntent.setData(
                        FileProvider.getUriForFile(
                            context, context.applicationContext.packageName + ".provider", file
                        )
                    )
                    newIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(newIntent)
                } else {
                    val newIntent = Intent(Intent.ACTION_VIEW)
                    newIntent.setDataAndType(
                        FileProvider.getUriForFile(
                            context, context.applicationContext.packageName + ".provider", file
                        ), "application/vnd.android.package-archive"
                    )
                    newIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    context.startActivity(newIntent);
                }*/
                val packageInstaller = context.packageManager?.packageInstaller ?: return
                val sessionParams =
                    PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL)
                val sessionId = packageInstaller.createSession(sessionParams)
                val session = packageInstaller.openSession(sessionId)
                context.contentResolver?.openInputStream(
                    FileProvider.getUriForFile(
                        context, context.applicationContext.packageName + ".provider", file
                    )
                )?.use { apkStream ->
                    val sessionStream = session.openWrite(file.name, 0, -1)
                    sessionStream.buffered().use { bufferedStream ->
                        apkStream.copyTo(bufferedStream)
                        bufferedStream.flush()
                        session.fsync(sessionStream)
                    }
                }
                val receiverIntent = Intent(context, PackageInstallerReceiver::class.java)
                val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                } else {
                    PendingIntent.FLAG_UPDATE_CURRENT
                }
                val receiverPendingIntent = PendingIntent.getBroadcast(context, 0, receiverIntent, flags)
                session.commit(receiverPendingIntent.intentSender)
                session.close()
            }
        }
    }
}