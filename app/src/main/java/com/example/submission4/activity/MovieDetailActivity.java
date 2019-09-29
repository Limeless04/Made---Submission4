package com.example.submission4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.submission4.R;
import com.example.submission4.database.MovieFavoriteHelper;
import com.example.submission4.model.MovieFavoriteModel;
import com.example.submission4.model.MovieModel;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieDetailActivity extends AppCompatActivity
        implements View.OnClickListener {

    public static final String EXTRA_MOVIE = "extra_movie";

    TextView tvTitle, tvVoteAverage, tvVoteCount, tvLanguage, tvOverview, tvUrlImage;

    Button btnSaveMovie;

    public static final String EXTRA_MOVIE_FAVORITE = "extra_movie_favorite";
    public static final String EXTRA_POSITION = "extra_position";

    private ProgressBar progressBar;
    ImageView imagePhoto;

    private boolean isEdit = false;
    public static final int RESULT_ADD = 101;

    private MovieFavoriteModel movieFavorite;
    private int position;

    private MovieFavoriteHelper movieFavoriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        tvTitle         = findViewById(R.id.tv_item_title);
        tvVoteAverage   = findViewById(R.id.tv_item_voteAverege);
        tvVoteCount     = findViewById(R.id.tv_item_voteCount);
        tvOverview      = findViewById(R.id.tv_item_overview);
        tvLanguage      = findViewById(R.id.tv_item_language);
        imagePhoto      = findViewById(R.id.img_item_photo);
        tvUrlImage      = findViewById(R.id.tv_url_image);
        progressBar     = findViewById(R.id.progressMovieDetail);
        btnSaveMovie    = findViewById(R.id.btn_submit);

        btnSaveMovie.setOnClickListener(this);

        movieFavoriteHelper = MovieFavoriteHelper.getInstance(getApplicationContext());
        movieFavoriteHelper.open();
        movieFavorite = getIntent().getParcelableExtra(EXTRA_MOVIE_FAVORITE);
        if (movieFavorite != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isEdit = true;
            btnSaveMovie.setVisibility(View.GONE);

        } else {
            movieFavorite = new MovieFavoriteModel();
        }

        if (savedInstanceState != null){
            progressBar.setVisibility(View.INVISIBLE);
            btnSaveMovie.setVisibility(View.VISIBLE);
            MovieModel movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

            String vote_average = Double.toString(movie.getVote_average());
            String url_image = "https://image.tmdb.org/t/p/w185" + movie.getPhoto();

            switch (movie.getOriginal_language()) {
                case "en":
                    tvLanguage.setText(getString(R.string.languange)) ;
                    break;
                case "in":
                    tvLanguage.setText(getString(R.string.language_indonesia)) ;
                    break;
            }

            tvTitle.setText(movie.getTitle());
            tvVoteAverage.setText(vote_average);
            tvVoteCount.setText(movie.getVote_count());
            tvOverview.setText(movie.getOverview());
            tvUrlImage.setText(url_image);

            Glide.with(MovieDetailActivity.this)
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .transform(new RoundedCornersTransformation(30, 10))
                    .into(imagePhoto);

        } else {
            progressBar.setVisibility(View.VISIBLE);


            final Handler handler = new Handler();

            new Thread(new Runnable() {
                public void run() {
                    try{
                        Thread.sleep(5000);
                    }
                    catch (Exception e) { }

                    handler.post(new Runnable() {
                        public void run() {
                            MovieModel movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

                            String vote_average = Double.toString(movie.getVote_average());
                            String url_image = "https://image.tmdb.org/t/p/w185" + movie.getPhoto();

                            switch (movie.getOriginal_language()) {
                                case "en":
                                    tvLanguage.setText(getString(R.string.languange)) ;
                                    break;
                                case "in":
                                    tvLanguage.setText(getString(R.string.language_indonesia)) ;
                                    break;
                            }

                            tvTitle.setText(movie.getTitle());
                            tvVoteAverage.setText(vote_average);
                            tvVoteCount.setText(movie.getVote_count());
                            tvOverview.setText(movie.getOverview());
                            tvUrlImage.setText(url_image);

                            Glide.with(MovieDetailActivity.this)
                                    .load(url_image)
                                    .placeholder(R.color.colorAccent)
                                    .dontAnimate()
                                    .transform(new RoundedCornersTransformation(30, 10))
                                    .into(imagePhoto);
                            progressBar.setVisibility(View.INVISIBLE);
                            btnSaveMovie.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }).start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_submit) {
            String title        = tvTitle.getText().toString().trim();
            String vote_count   = tvVoteCount.getText().toString().trim();
            String language     = tvLanguage.getText().toString().trim();
            String overview     = tvOverview.getText().toString().trim();
            String release_date = tvTitle.getText().toString().trim();

            String vote_average = tvVoteAverage.getText().toString().trim();
            String url_image    = tvUrlImage.getText().toString().trim();

            movieFavorite.setTitle(title);
            movieFavorite.setVote_count(vote_count);
            movieFavorite.setOriginal_language(language);
            movieFavorite.setOverview(overview);
            movieFavorite.setRelease_date(release_date);
            movieFavorite.setVote_average(vote_average);
            movieFavorite.setPhoto(url_image);

            Intent intent = new Intent();
            intent.putExtra(EXTRA_MOVIE_FAVORITE, movieFavorite);
            intent.putExtra(EXTRA_POSITION, position);

            if (!isEdit) {

                long result = movieFavoriteHelper.insertMovie(movieFavorite);

                if (result > 0) {
                    movieFavorite.setId((int) result);
                    setResult(RESULT_ADD, intent);
                    Toast.makeText(MovieDetailActivity.this, getString(R.string.succes_add_data), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(MovieDetailActivity.this, getString(R.string.failed_add_data), Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}

