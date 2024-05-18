package com.github.dannful.tenkai

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import java.io.File


class DownloadCompletedReceiver : BroadcastReceiver() {

    private lateinit var downloadManager: DownloadManager



    override fun onReceive(context: Context?, intent: Intent?) {
        downloadManager = context?.getSystemService(DownloadManager::class.java) ?: return
        if (intent?.action == "android.intent.action.DOWNLOAD_COMPLETE") {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
            if (id != -1L) {
                if (android.os.Build.VERSION.SDK_INT >= 29) {
                    val newIntent = Intent(Intent.ACTION_INSTALL_PACKAGE);
                    newIntent.setData(
                        Uri.fromFile(
                            File(
                                Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_DOWNLOADS
                                ), "tenkai-new.apk"
                            )
                        )
                    );
                    newIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    context.startActivity(newIntent);
                } else {
                    val newIntent = Intent (Intent.ACTION_VIEW);
                    newIntent.setDataAndType(
                        Uri.fromFile(
                            File(
                                Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_DOWNLOADS
                                ), "tenkai-new.apk"
                            )
                        ), "application/vnd.android.package-archive "
                    );
                    context.startActivity(newIntent);
                }
            }
        }
    }
}