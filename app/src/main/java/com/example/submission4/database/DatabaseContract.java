package com.example.submission4.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    static final class MovieFavoriteColumns implements BaseColumns {
        static final String TABLE_MOVIE              = "movie_favorites";

        static final String TITLE                   = "title";
        static final String VOTE_COUNT              = "vote_count";
        static final String ORIGINAL_LANGUAGE       = "original_language";
        static final String OVERVIEW                = "overview";
        static final String RELEASE_DATE            = "release_date";
        static final String VOTE_AVERAGE            = "vote_average";
        static final String PHOTO                   = "photo";
    }

    static final class TVFavoriteColumns implements BaseColumns {
        static final String TABLE_TV_SHOW           = "tv_show_favorites";

        static final String TV_TITLE                = "tv_title";
        static final String TV_VOTE_COUNT           = "tv_vote_count";
        static final String TV_ORIGINAL_LANGUAGE    = "tv_original_language";
        static final String TV_OVERVIEW             = "tv_overview";
        static final String TV_RELEASE_DATE         = "tv_release_date";
        static final String TV_VOTE_AVERAGE         = "tv_vote_average";
        static final String TV_PHOTO                = "tv_photo";
    }
}

