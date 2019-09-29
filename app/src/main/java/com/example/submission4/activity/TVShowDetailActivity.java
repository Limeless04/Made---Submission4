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
import com.example.submission4.database.TVShowFavoriteHelper;
import com.example.submission4.model.TVShowFavoriteModel;
import com.example.submission4.model.TVShowModel;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TVShowDetailActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_TV_SHOW = "extra_movie";

    TextView tvTitle, tvVoteAverage, tvVoteCount, tvLanguage, tvOverview, tvUrlImage;
    ImageView imagePhoto;
    Button btnSaveTv;

    public static final String EXTRA_TV_FAVORITE = "extra_tv_favorite";
    public static final String EXTRA_POSITION = "extra_position";

    private ProgressBar progressBar;

    private boolean isEdit = false;
    public static final int RESULT_ADD  = 101;

    private TVShowFavoriteModel tvShowFavorite;
    private int position;

    private TVShowFavoriteHelper tvShowFavoriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_detail);

        tvTitle         = findViewById(R.id.tv_item_title);
        tvVoteAverage   = findViewById(R.id.tv_item_voteAverege);
        tvVoteCount     = findViewById(R.id.tv_item_voteCount);
        tvOverview      = findViewById(R.id.tv_item_overview);
        tvLanguage      = findViewById(R.id.tv_item_language);
        tvUrlImage      = findViewById(R.id.tv_url_image);
        imagePhoto      = findViewById(R.id.img_item_photo);
        progressBar     = findViewById(R.id.progressTVShowDetail);

        btnSaveTv    = findViewById(R.id.btn_submit);
        btnSaveTv.setOnClickListener(this);

        tvShowFavoriteHelper = TVShowFavoriteHelper.getInstance(getApplicationContext());
        tvShowFavoriteHelper.open();
        tvShowFavorite = getIntent().getParcelableExtra(EXTRA_TV_FAVORITE);
        if (tvShowFavorite != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isEdit = true;
            btnSaveTv.setVisibility(View.GONE);

        } else {
            tvShowFavorite = new TVShowFavoriteModel();
        }

        if (savedInstanceState != null){

            progressBar.setVisibility(View.INVISIBLE);
            btnSaveTv.setVisibility(View.VISIBLE);
            TVShowModel tvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

            String vote_average = Double.toString(tvShow.getVote_average());
            String url_image = "https://image.tmdb.org/t/p/w185" + tvShow.getPoster_path();

            switch (tvShow.getOriginal_language()) {
                case "en":
                    tvLanguage.setText(getString(R.string.languange)) ;
                    break;
                case "in":
                    tvLanguage.setText(getString(R.string.language_indonesia)) ;
                    break;
            }

            tvTitle.setText(tvShow.getName());
            tvVoteAverage.setText(vote_average);
            tvVoteCount.setText(tvShow.getVote_count());
            tvOverview.setText(tvShow.getOverview());
            tvUrlImage.setText(url_image);

            Glide.with(TVShowDetailActivity.this)
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .transform(new RoundedCornersTransformation(40, 10))
                    .dontAnimate()
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
                            TVShowModel tvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

                            String vote_average = Double.toString(tvShow.getVote_average());
                            String url_image = "https://image.tmdb.org/t/p/w185" + tvShow.getPoster_path();

                            switch (tvShow.getOriginal_language()) {
                                case "en":
                                    tvLanguage.setText(getString(R.string.languange)) ;
                                    break;
                                case "in":
                                    tvLanguage.setText(getString(R.string.language_indonesia)) ;
                                    break;
                            }

                            tvTitle.setText(tvShow.getName());
                            tvVoteAverage.setText(vote_average);
                            tvVoteCount.setText(tvShow.getVote_count());
                            tvOverview.setText(tvShow.getOverview());
                            tvUrlImage.setText(url_image);

                            Glide.with(TVShowDetailActivity.this)
                                    .load(url_image)
                                    .placeholder(R.color.colorAccent)
                                    .transform(new RoundedCornersTransformation(30, 10))
                                    .dontAnimate()
                                    .into(imagePhoto);

                            progressBar.setVisibility(View.INVISIBLE);
                            btnSaveTv.setVisibility(View.VISIBLE);
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

            tvShowFavorite.setTv_title(title);
            tvShowFavorite.setTv_vote_count(vote_count);
            tvShowFavorite.setTv_original_language(language);
            tvShowFavorite.setTv_overview(overview);
            tvShowFavorite.setTv_release_date(release_date);
            tvShowFavorite.setTv_vote_average(vote_average);
            tvShowFavorite.setTv_photo(url_image);

            Intent intent = new Intent();
            intent.putExtra(EXTRA_TV_FAVORITE, tvShowFavorite);
            intent.putExtra(EXTRA_POSITION, position);

            if (!isEdit) {

                long result = tvShowFavoriteHelper.insertTv(tvShowFavorite);

                if (result > 0) {
                    tvShowFavorite.setId((int) result);
                    setResult(RESULT_ADD, intent);
                    Toast.makeText(TVShowDetailActivity.this, getString(R.string.succes_add_data), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(TVShowDetailActivity.this,  getString(R.string.failed_add_data), Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}
