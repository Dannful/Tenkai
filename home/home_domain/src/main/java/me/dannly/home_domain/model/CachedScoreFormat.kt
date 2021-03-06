package me.dannly.home_domain.model

enum class CachedScoreFormat {

    POINT_100,

    /**
     * A float from 0-10 with 1 decimal place
     */
    POINT_10_DECIMAL,

    /**
     * An integer from 0-10
     */
    POINT_10,

    /**
     * An integer from 0-5. Should be represented in Stars
     */
    POINT_5,

    /**
     * An integer from 0-3. Should be represented in Smileys. 0 => No Score, 1 => :(, 2 => :|, 3 => :)
     */
    POINT_3;
}