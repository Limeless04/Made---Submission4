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
import com.example.submission4.database.TVShowFavoriteHelper;
import com.example.submission4.model.TVShowFavoriteModel;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TVShowShowActivity extends AppCompatActivity
        implements View.OnClickListener {

    public static final String EXTRA_TV_FAVORITE = "extra_tv_favorite";
    public static final String EXTRA_POSITION = "extra_position_tv";

    TextView tvTitle, tvVoteAverage, tvVoteCount, tvLanguage, tvOverview, tvUrlImage;

    private ProgressBar progressBar;
    ImageView imagePhotoFav;
    Button btnRemoveTv;

    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_DELETE = 301;
    private TVShowFavoriteModel tvShowFavorite;
    private int position;

    private TVShowFavoriteHelper tvShowFavoriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_show);

        tvTitle         = findViewById(R.id.tv_item_title);
        tvVoteAverage   = findViewById(R.id.tv_item_voteAverege);
        tvVoteCount     = findViewById(R.id.tv_item_voteCount);
        tvOverview      = findViewById(R.id.tv_item_overview);
        tvLanguage      = findViewById(R.id.tv_item_language);
        imagePhotoFav   = findViewById(R.id.img_item_photo);
        tvUrlImage      = findViewById(R.id.tv_url_image);

        btnRemoveTv     = findViewById(R.id.action_delete);
        btnRemoveTv.setOnClickListener(this);

        tvShowFavoriteHelper = TVShowFavoriteHelper.getInstance(getApplicationContext());
        tvShowFavorite = getIntent().getParcelableExtra(EXTRA_TV_FAVORITE);

        progressBar = findViewById(R.id.progressTvShow);

        if (savedInstanceState != null) {
            progressBar.setVisibility(View.INVISIBLE);
            btnRemoveTv.setVisibility(View.VISIBLE);
            TVShowFavoriteModel tvShowFavorite = getIntent().getParcelableExtra(EXTRA_TV_FAVORITE);

            switch (tvShowFavorite.getTv_original_language()) {
                case "en":
                    tvLanguage.setText(getString(R.string.languange)) ;
                    break;
                case "in":
                    tvLanguage.setText(getString(R.string.language_indonesia)) ;
                    break;
            }

            tvTitle.setText(tvShowFavorite.getTv_title());
            tvVoteAverage.setText(tvShowFavorite.getTv_vote_average());
            tvVoteCount.setText(tvShowFavorite.getTv_vote_count());
            tvOverview.setText(tvShowFavorite.getTv_overview());

            Glide.with(TVShowShowActivity.this)
                    .load(tvShowFavorite.getTv_photo())
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
                            TVShowFavoriteModel tvShowFavorite = getIntent().getParcelableExtra(EXTRA_TV_FAVORITE);

                            switch (tvShowFavorite.getTv_original_language()) {
                                case "en":
                                    tvLanguage.setText(getString(R.string.languange)) ;
                                    break;
                                case "in":
                                    tvLanguage.setText(getString(R.string.language_indonesia)) ;
                                    break;
                            }

                            tvTitle.setText(tvShowFavorite.getTv_title());
                            tvVoteAverage.setText(tvShowFavorite.getTv_vote_average());
                            tvVoteCount.setText(tvShowFavorite.getTv_vote_count());
                            tvOverview.setText(tvShowFavorite.getTv_overview());

                            Glide.with(TVShowShowActivity.this)
                                    .load(tvShowFavorite.getTv_photo())
                                    .placeholder(R.color.colorAccent)
                                    .dontAnimate()
                                    .transform(new RoundedCornersTransformation(30, 10))
                                    .into(imagePhotoFav);
                            progressBar.setVisibility(View.INVISIBLE);
                            btnRemoveTv.setVisibility(View.VISIBLE);
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
                            long result = tvShowFavoriteHelper.deleteTv(tvShowFavorite.getId());
                            if (result > 0) {
                                Intent intent = new Intent();
                                intent.putExtra(EXTRA_POSITION, position);
                                setResult(RESULT_DELETE, intent);
                                finish();
                            } else {
                                Toast.makeText(TVShowShowActivity.this, getString(R.string.notif_failed_delete), Toast.LENGTH_SHORT).show();
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

