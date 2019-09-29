package com.example.submission4.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.submission4.database.DatabaseContract.MovieFavoriteColumns;
import com.example.submission4.database.DatabaseContract.TVFavoriteColumns;

import static com.example.submission4.database.DatabaseContract.MovieFavoriteColumns.TABLE_MOVIE;
import static com.example.submission4.database.DatabaseContract.TVFavoriteColumns.TABLE_TV_SHOW;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moviecatalogues";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE_FAVORITE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT UNIQUE," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_MOVIE,
            MovieFavoriteColumns._ID,
            MovieFavoriteColumns.TITLE,
            MovieFavoriteColumns.VOTE_COUNT,
            MovieFavoriteColumns.ORIGINAL_LANGUAGE,
            MovieFavoriteColumns.OVERVIEW,
            MovieFavoriteColumns.RELEASE_DATE,
            MovieFavoriteColumns.VOTE_AVERAGE,
            MovieFavoriteColumns.PHOTO
    );

    private static final String SQL_CREATE_TABLE_TV_SHOW_FAVORITE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT UNIQUE," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_TV_SHOW,
            TVFavoriteColumns._ID,
            TVFavoriteColumns.TV_TITLE,
            TVFavoriteColumns.TV_VOTE_COUNT,
            TVFavoriteColumns.TV_ORIGINAL_LANGUAGE,
            TVFavoriteColumns.TV_OVERVIEW,
            TVFavoriteColumns.TV_RELEASE_DATE,
            TVFavoriteColumns.TV_VOTE_AVERAGE,
            TVFavoriteColumns.TV_PHOTO
    );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MOVIE_FAVORITE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TV_SHOW_FAVORITE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TV_SHOW);
        onCreate(sqLiteDatabase);
    }
}
