package com.github.dannful.core.data.model

import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

internal class DefaultDispatcherProvider : DispatcherProvider {
    override val Default: CoroutineContext
        get() = Dispatchers.Default
    override val Main: CoroutineContext
        get() = Dispatchers.Main
    override val IO: CoroutineContext
        get() = Dispatchers.IO
    override val Unconfined: CoroutineContext
        get() = Dispatchers.Unconfined
}