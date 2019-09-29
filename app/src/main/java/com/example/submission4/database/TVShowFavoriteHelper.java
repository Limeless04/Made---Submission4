package com.example.submission4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.submission4.model.TVShowFavoriteModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.submission4.database.DatabaseContract.TVFavoriteColumns.TABLE_TV_SHOW;
import static com.example.submission4.database.DatabaseContract.TVFavoriteColumns.TV_ORIGINAL_LANGUAGE;
import static com.example.submission4.database.DatabaseContract.TVFavoriteColumns.TV_OVERVIEW;
import static com.example.submission4.database.DatabaseContract.TVFavoriteColumns.TV_PHOTO;
import static com.example.submission4.database.DatabaseContract.TVFavoriteColumns.TV_RELEASE_DATE;
import static com.example.submission4.database.DatabaseContract.TVFavoriteColumns.TV_TITLE;
import static com.example.submission4.database.DatabaseContract.TVFavoriteColumns.TV_VOTE_AVERAGE;
import static com.example.submission4.database.DatabaseContract.TVFavoriteColumns.TV_VOTE_COUNT;

public class TVShowFavoriteHelper {
    private static final String DATABASE_TABLE = TABLE_TV_SHOW;
    private static DatabaseHelper databaseHelper;
    private static TVShowFavoriteHelper INSTANCE;

    private static SQLiteDatabase database;

    private TVShowFavoriteHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static TVShowFavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TVShowFavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<TVShowFavoriteModel> getAllTvFavorite() {
        ArrayList<TVShowFavoriteModel> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        TVShowFavoriteModel tvShowFavorite;
        if (cursor.getCount() > 0) {
            do {
                tvShowFavorite = new TVShowFavoriteModel();
                tvShowFavorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tvShowFavorite.setTv_title(cursor.getString(cursor.getColumnIndexOrThrow(TV_TITLE)));
                tvShowFavorite.setTv_vote_count(cursor.getString(cursor.getColumnIndexOrThrow(TV_VOTE_COUNT)));
                tvShowFavorite.setTv_original_language(cursor.getString(cursor.getColumnIndexOrThrow(TV_ORIGINAL_LANGUAGE)));
                tvShowFavorite.setTv_overview(cursor.getString(cursor.getColumnIndexOrThrow(TV_OVERVIEW)));
                tvShowFavorite.setTv_release_date(cursor.getString(cursor.getColumnIndexOrThrow(TV_RELEASE_DATE)));
                tvShowFavorite.setTv_photo(cursor.getString(cursor.getColumnIndexOrThrow(TV_PHOTO)));
                tvShowFavorite.setTv_vote_average(cursor.getString(cursor.getColumnIndexOrThrow(TV_VOTE_AVERAGE)));

                arrayList.add(tvShowFavorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertTv(TVShowFavoriteModel tvShowFavorite) {
        ContentValues args = new ContentValues();
        args.put(TV_TITLE, tvShowFavorite.getTv_title());
        args.put(TV_VOTE_COUNT, tvShowFavorite.getTv_vote_count());
        args.put(TV_ORIGINAL_LANGUAGE, tvShowFavorite.getTv_original_language());
        args.put(TV_OVERVIEW, tvShowFavorite.getTv_overview());
        args.put(TV_RELEASE_DATE, tvShowFavorite.getTv_release_date());
        args.put(TV_VOTE_AVERAGE, tvShowFavorite.getTv_vote_average());
        args.put(TV_PHOTO, tvShowFavorite.getTv_photo());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteTv(int id) {
        return database.delete(TABLE_TV_SHOW, _ID + " = '" + id + "'", null);
    }
}
