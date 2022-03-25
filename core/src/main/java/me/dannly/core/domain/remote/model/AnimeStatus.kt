package me.dannly.core.domain.remote.model

enum class AnimeStatus {

    /**
     * Has completed and is no longer being released
     */
    FINISHED,

    /**
     * Currently releasing
     */
    RELEASING,

    /**
     * To be released at a later date
     */
    NOT_YET_RELEASED,

    /**
     * Ended before the work could be finished
     */
    CANCELLED,

    /**
     * Version 2 only. Is currently paused from releasing and will resume at a later date
     */
    HIATUS
}