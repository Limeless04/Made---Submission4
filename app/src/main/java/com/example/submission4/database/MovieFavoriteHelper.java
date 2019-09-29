package com.example.submission4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.submission4.model.MovieFavoriteModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.submission4.database.DatabaseContract.MovieFavoriteColumns.ORIGINAL_LANGUAGE;
import static com.example.submission4.database.DatabaseContract.MovieFavoriteColumns.OVERVIEW;
import static com.example.submission4.database.DatabaseContract.MovieFavoriteColumns.PHOTO;
import static com.example.submission4.database.DatabaseContract.MovieFavoriteColumns.RELEASE_DATE;
import static com.example.submission4.database.DatabaseContract.MovieFavoriteColumns.TABLE_MOVIE;
import static com.example.submission4.database.DatabaseContract.MovieFavoriteColumns.TITLE;
import static com.example.submission4.database.DatabaseContract.MovieFavoriteColumns.VOTE_AVERAGE;
import static com.example.submission4.database.DatabaseContract.MovieFavoriteColumns.VOTE_COUNT;

public class MovieFavoriteHelper {
    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static DatabaseHelper dataBaseHelper;
    private static MovieFavoriteHelper INSTANCE;

    private static SQLiteDatabase database;

    private MovieFavoriteHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static MovieFavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieFavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<MovieFavoriteModel> getAllMoviesFav() {
        ArrayList<MovieFavoriteModel> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        MovieFavoriteModel movieFavorite;
        if (cursor.getCount() > 0) {
            do {
                movieFavorite = new MovieFavoriteModel();
                movieFavorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieFavorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movieFavorite.setVote_count(cursor.getString(cursor.getColumnIndexOrThrow(VOTE_COUNT)));
                movieFavorite.setOriginal_language(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINAL_LANGUAGE)));
                movieFavorite.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movieFavorite.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movieFavorite.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(PHOTO)));
                movieFavorite.setVote_average(cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));

                arrayList.add(movieFavorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovie(MovieFavoriteModel movieFavorite) {
        ContentValues args = new ContentValues();
        args.put(TITLE, movieFavorite.getTitle());
        args.put(VOTE_COUNT, movieFavorite.getVote_count());
        args.put(ORIGINAL_LANGUAGE, movieFavorite.getOriginal_language());
        args.put(OVERVIEW, movieFavorite.getOverview());
        args.put(RELEASE_DATE, movieFavorite.getRelease_date());
        args.put(VOTE_AVERAGE, movieFavorite.getVote_average());
        args.put(PHOTO, movieFavorite.getPhoto());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteMovie(int id) {
        return database.delete(TABLE_MOVIE, _ID + " = '" + id + "'", null);
    }

}
