package com.github.dannful.core_ui.util

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

val <T : Any> LazyPagingItems<T>.isLoading
    get() = loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading || loadState.prepend is LoadState.Loading