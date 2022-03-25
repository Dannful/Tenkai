package me.dannly.core.util

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {

    data class StringResource(@StringRes val stringResource: Int): UiText()
    data class DynamicString(val text: String): UiText()

    fun asString(context: Context, vararg args: Any) = when(this) {
        is DynamicString -> text
        is StringResource -> context.getString(stringResource, args)
    }
}
