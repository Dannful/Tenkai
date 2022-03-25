package me.dannly.browse_data.paging

import me.dannly.browse_domain.model.MediaSearch
import me.dannly.browse_domain.model.SearchedMedia
import me.dannly.browse_domain.repository.BrowseRepository
import me.dannly.core.data.paging.BasePagingSource

class AnimeSearchPagingSource(
    private val browseRepository: BrowseRepository,
    private val mediaSearch: MediaSearch
) : BasePagingSource<SearchedMedia>() {

    override suspend fun loadData(page: Int): List<SearchedMedia> =
        browseRepository.searchMedia(mediaSearch, page).getOrThrow()

    override val perPage: Int
        get() = MEDIAS_PER_PAGE

    companion object {

        const val MEDIAS_PER_PAGE = 10
    }
}