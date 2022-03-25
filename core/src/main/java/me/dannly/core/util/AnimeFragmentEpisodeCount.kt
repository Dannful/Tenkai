package me.dannly.core.util

import me.dannly.graphql.fragment.AnimeFragment

val AnimeFragment.episodeCount get() = nextAiringEpisode?.episode?.minus(1) ?: episodes ?: 0