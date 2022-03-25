package me.dannly.home_presentation.anime_actions.watch.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

fun launchCaster(context: Context, videoURL: String) {
    val shareVideo = Intent(Intent.ACTION_VIEW)
    shareVideo.setDataAndType(Uri.parse(videoURL), "video/*")
    shareVideo.setPackage("com.instantbits.cast.webvideo")
    try {
        context.startActivity(shareVideo)
    } catch (ex: ActivityNotFoundException) {
        val intent = Intent(Intent.ACTION_VIEW)
        val uriString = "market://details?id=com.instantbits.cast.webvideo"
        intent.data = Uri.parse(uriString)
        context.startActivity(intent)
    }
}