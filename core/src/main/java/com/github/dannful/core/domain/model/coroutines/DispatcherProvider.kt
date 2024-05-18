package com.github.dannful.core.domain.model.coroutines

import kotlin.coroutines.CoroutineContext

interface DispatcherProvider {

    val Default: CoroutineContext
    val Main: CoroutineContext
    val IO: CoroutineContext
    val Unconfined: CoroutineContext
}