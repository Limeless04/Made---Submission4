package com.example.submission4.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieShowActivity extends AppCompatActivity
        implements View.OnClickListener {

    public static final String EXTRA_MOVIE_FAVORITE = "extra_movie_favorite";
    public static final String EXTRA_POSITION = "extra_position";

    TextView tvTitle, tvVoteAverage, tvVoteCount, tvLanguage, tvOverview, tvUrlImage;

    private ProgressBar progressBar;
    ImageView imagePhotoFav;
    Button btnRemoveMovie;

    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_DELETE = 301;
    private MovieFavoriteModel movieFavorite;
    private int position;

    private MovieFavoriteHelper movieFavoriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_show);

        tvTitle         = findViewById(R.id.tv_item_title);
        tvVoteAverage   = findViewById(R.id.tv_item_voteAverege);
        tvVoteCount     = findViewById(R.id.tv_item_voteCount);
        tvOverview      = findViewById(R.id.tv_item_overview);
        tvLanguage      = findViewById(R.id.tv_item_language);
        imagePhotoFav   = findViewById(R.id.img_item_photo);
        tvUrlImage      = findViewById(R.id.tv_url_image);

        btnRemoveMovie  = findViewById(R.id.action_delete);
        btnRemoveMovie.setOnClickListener(this);

        movieFavoriteHelper = MovieFavoriteHelper.getInstance(getApplicationContext());
        movieFavorite = getIntent().getParcelableExtra(EXTRA_MOVIE_FAVORITE);

        progressBar = findViewById(R.id.progressMovieShow);

        if (savedInstanceState != null) {
            progressBar.setVisibility(View.INVISIBLE);
            btnRemoveMovie.setVisibility(View.VISIBLE);
            MovieFavoriteModel movieFavorite = getIntent().getParcelableExtra(EXTRA_MOVIE_FAVORITE);

            switch (movieFavorite.getOriginal_language()) {
                case "en":
                    tvLanguage.setText(getString(R.string.languange)) ;
                    break;
                case "in":
                    tvLanguage.setText(getString(R.string.language_indonesia)) ;
                    break;
            }

            tvTitle.setText(movieFavorite.getTitle());
            tvVoteAverage.setText(movieFavorite.getVote_average());
            tvVoteCount.setText(movieFavorite.getVote_count());
            tvOverview.setText(movieFavorite.getOverview());

            Glide.with(MovieShowActivity.this)
                    .load(movieFavorite.getPhoto())
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .transform(new RoundedCornersTransformation(30, 10))
                    .into(imagePhotoFav);

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
                            MovieFavoriteModel movieFavorite = getIntent().getParcelableExtra(EXTRA_MOVIE_FAVORITE);

                            switch (movieFavorite.getOriginal_language()) {
                                case "en":
                                    tvLanguage.setText(getString(R.string.languange)) ;
                                    break;
                                case "in":
                                    tvLanguage.setText(getString(R.string.language_indonesia)) ;
                                    break;
                            }

                            tvTitle.setText(movieFavorite.getTitle());
                            tvVoteAverage.setText(movieFavorite.getVote_average());
                            tvVoteCount.setText(movieFavorite.getVote_count());
                            tvOverview.setText(movieFavorite.getOverview());

                            Glide.with(MovieShowActivity.this)
                                    .load(movieFavorite.getPhoto())
                                    .placeholder(R.color.colorAccent)
                                    .dontAnimate()
                                    .transform(new RoundedCornersTransformation(30, 10))
                                    .into(imagePhotoFav);
                            progressBar.setVisibility(View.INVISIBLE);
                            btnRemoveMovie.setVisibility(View.VISIBLE);
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
        if (view.getId() == R.id.action_delete) {
            showAlertDialog(ALERT_DIALOG_DELETE);
        }
    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        if (!isDialogClose) {
            dialogMessage = getString(R.string.notif_question_delete);
            dialogTitle   = getString(R.string.delete_movie);

            alertDialogBuilder.setTitle(dialogTitle);
            alertDialogBuilder
                    .setMessage(dialogMessage)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            long result = movieFavoriteHelper.deleteMovie(movieFavorite.getId());
                            if (result > 0) {
                                Intent intent = new Intent();
                                intent.putExtra(EXTRA_POSITION, position);
                                setResult(RESULT_DELETE, intent);
                                finish();
                            } else {
                                Toast.makeText(MovieShowActivity.this, getString(R.string.notif_failed_delete), Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            finish();
        }
    }
}
