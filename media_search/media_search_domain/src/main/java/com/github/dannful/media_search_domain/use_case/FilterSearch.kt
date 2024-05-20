package com.github.dannful.media_search_domain.use_case

import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import kotlinx.coroutines.withContext

class FilterSearch(
    private val dispatcherProvider: DispatcherProvider
) {

    suspend operator fun invoke(items: List<String>, query: String) =
        withContext(dispatcherProvider.Default) {
            if (query.isBlank()) items else items.filter { it.contains(query, ignoreCase = true) }
        }
}