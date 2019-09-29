package com.example.submission4.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.submission4.LoadMoviesFavCallback;
import com.example.submission4.R;
import com.example.submission4.adapter.MovieFavoriteAdapter;
import com.example.submission4.database.MovieFavoriteHelper;
import com.example.submission4.model.MovieFavoriteModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.submission4.activity.MovieShowActivity.REQUEST_UPDATE;

public class MovieFavoriteActivity extends AppCompatActivity
        implements View.OnClickListener, LoadMoviesFavCallback {

    private RecyclerView rvMovieFav;
    private ProgressBar progressBar;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private MovieFavoriteAdapter adapter;
    private MovieFavoriteHelper movieFavoriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_favorite);

        rvMovieFav = findViewById(R.id.rv_movie_fav);
        rvMovieFav.setLayoutManager(new LinearLayoutManager(this));
        rvMovieFav.setHasFixedSize(true);

        movieFavoriteHelper = MovieFavoriteHelper.getInstance(getApplicationContext());

        movieFavoriteHelper.open();

        progressBar = findViewById(R.id.progressbar);
        adapter = new MovieFavoriteAdapter(this);
        rvMovieFav.setAdapter(adapter);

        if (savedInstanceState == null) {

            new LoadMovieAsync(movieFavoriteHelper, this).execute();
        } else {
            ArrayList<MovieFavoriteModel> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListMoviesFav(list);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getAllMoviesFav());
    }

    @Override
    public void onClick(View view) {
        //
    }

    @Override
    public void preExecute() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<MovieFavoriteModel> movieFavorites) {

        progressBar.setVisibility(View.INVISIBLE);
        adapter.setListMoviesFav(movieFavorites);
    }

    private static class LoadMovieAsync
            extends AsyncTask<Void, Void, ArrayList<MovieFavoriteModel>> {

        private final WeakReference<MovieFavoriteHelper> weakReference;
        private final WeakReference<LoadMoviesFavCallback> weakCallback;

        private LoadMovieAsync(MovieFavoriteHelper movieFavoriteHelper,
                               MovieFavoriteActivity callback) {
            weakReference = new WeakReference<>(movieFavoriteHelper);
            weakCallback = new WeakReference<LoadMoviesFavCallback>(callback);
        }

        @Override
        protected ArrayList<MovieFavoriteModel> doInBackground(Void... voids) {

            return weakReference.get().getAllMoviesFav();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<MovieFavoriteModel> movieFavorites) {
            super.onPostExecute(movieFavorites);

            weakCallback.get().postExecute(movieFavorites);

        }

    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {

            if (requestCode == REQUEST_UPDATE) {

                if (resultCode == MovieShowActivity.RESULT_DELETE) {
                    int position = data.getIntExtra(MovieShowActivity.EXTRA_POSITION, 0);

                    adapter.removeItem(position);

                    showSnackbarMessage(getString(R.string.notif_delete_movie));
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieFavoriteHelper.close();
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvMovieFav, message, Snackbar.LENGTH_SHORT).show();
    }
}

