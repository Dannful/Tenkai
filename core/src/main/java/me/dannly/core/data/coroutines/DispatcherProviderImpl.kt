package me.dannly.core.data.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import me.dannly.core.domain.coroutines.DispatcherProvider

class DispatcherProviderImpl : DispatcherProvider {

    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
    override val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
}