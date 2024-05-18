package com.github.dannful.core_ui

import android.content.Context
import android.widget.Toast

fun Context.makeErrorToast() {
    Toast.makeText(
        this,
        getString(R.string.failed_to_process_information_please_verify_your_internet_connection_and_try_again_later),
        Toast.LENGTH_SHORT
    ).show()
}