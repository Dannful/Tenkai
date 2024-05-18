package com.github.dannful.tenkai

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import com.github.dannful.core_ui.makeErrorToast

class PackageInstallerReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.getIntExtra(PackageInstaller.EXTRA_STATUS, -1)) {
            PackageInstaller.STATUS_PENDING_USER_ACTION -> {
                val confirmationIntent =
                    intent.getParcelableExtra(Intent.EXTRA_INTENT, Intent::class.java) ?: return
                context.startActivity(confirmationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }
            else -> {
                context.makeErrorToast()
            }
        }
    }
}