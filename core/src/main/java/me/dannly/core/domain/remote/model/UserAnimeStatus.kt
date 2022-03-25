package me.dannly.core.domain.remote.model

enum class UserAnimeStatus {

    /**
     * Currently watching/reading
     */
    CURRENT,

    /**
     * Planning to watch/read
     */
    PLANNING,

    /**
     * Finished watching/reading
     */
    COMPLETED,

    /**
     * Stopped watching/reading before completing
     */
    DROPPED,

    /**
     * Paused watching/reading
     */
    PAUSED,

    /**
     * Re-watching/reading
     */
    REPEATING
}