package com.github.dannful.onboarding_domain.use_case

import android.net.Uri
import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import kotlinx.coroutines.withContext

class GetCodeFromUrl(
    private val dispatcherProvider: DispatcherProvider
) {

    suspend operator fun invoke(url: String): String? = withContext(dispatcherProvider.IO) {
        val parameterName = "code"
        val uri = Uri.parse(url)
        val argument = uri.getQueryParameter(parameterName)
        argument
    }
}