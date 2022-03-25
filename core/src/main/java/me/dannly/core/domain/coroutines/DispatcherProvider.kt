package me.dannly.core.domain.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
    val main: CoroutineDispatcher
}