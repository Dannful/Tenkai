package me.dannly.core_ui.components.paging

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

val <T : Any> LazyPagingItems<T>.loading get() = loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading
val <T : Any> LazyPagingItems<T>.error get() = loadState.refresh is LoadState.Error || loadState.append is LoadState.Error