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

import com.example.submission4.LoadTvFavCallback;
import com.example.submission4.R;
import com.example.submission4.adapter.TVShowFavoriteAdapter;
import com.example.submission4.database.TVShowFavoriteHelper;
import com.example.submission4.model.TVShowFavoriteModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.submission4.activity.MovieShowActivity.REQUEST_UPDATE;

public class TVShowFavoriteActivity extends AppCompatActivity
        implements View.OnClickListener, LoadTvFavCallback {

    private RecyclerView rvTvFav;
    private ProgressBar progressBar;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private TVShowFavoriteAdapter adapter;
    private TVShowFavoriteHelper tvShowFavoriteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_favorite);

        rvTvFav = findViewById(R.id.rv_tv_fav);
        rvTvFav.setLayoutManager(new LinearLayoutManager(this));
        rvTvFav.setHasFixedSize(true);

        tvShowFavoriteHelper = TVShowFavoriteHelper.getInstance(getApplicationContext());

        tvShowFavoriteHelper.open();

        progressBar = findViewById(R.id.progressbar);
        adapter = new TVShowFavoriteAdapter(this);
        rvTvFav.setAdapter(adapter);


        if (savedInstanceState == null) {
            new LoadTvAsync(tvShowFavoriteHelper, this).execute();
        } else {
            ArrayList<TVShowFavoriteModel> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListTvShowFav(list);
            }

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListTvShowFav());
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
    public void postExecute(ArrayList<TVShowFavoriteModel> tvShowFavorites) {
        progressBar.setVisibility(View.INVISIBLE);
        adapter.setListTvShowFav(tvShowFavorites);
    }

    private static class LoadTvAsync
            extends AsyncTask<Void, Void, ArrayList<TVShowFavoriteModel>> {

        private final WeakReference<TVShowFavoriteHelper> weakReference;
        private final WeakReference<LoadTvFavCallback> weakCallback;

        private LoadTvAsync(TVShowFavoriteHelper movieFavoriteHelper,
                            LoadTvFavCallback callback) {
            weakReference = new WeakReference<>(movieFavoriteHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<TVShowFavoriteModel> doInBackground(Void... voids) {
            return weakReference.get().getAllTvFavorite();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<TVShowFavoriteModel> tvShowFavorites) {
            super.onPostExecute(tvShowFavorites);

            weakCallback.get().postExecute(tvShowFavorites);

        }
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {

            if (requestCode == REQUEST_UPDATE) {

                if (resultCode == TVShowShowActivity.RESULT_DELETE) {
                    int position = data.getIntExtra(TVShowShowActivity.EXTRA_POSITION, 0);

                    adapter.removeItem(position);

                    showSnackbarMessage(getString(R.string.notif_delete_movie));
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvShowFavoriteHelper.close();
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvTvFav, message, Snackbar.LENGTH_SHORT).show();
    }
}
