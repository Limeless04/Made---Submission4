package com.example.submission4.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.submission4.R;
import com.example.submission4.activity.MovieShowActivity;
import com.example.submission4.model.MovieFavoriteModel;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.MovieViewHolder> {
    private ArrayList<MovieFavoriteModel> listMoviesFav = new ArrayList<>();
    public void setData(ArrayList<MovieFavoriteModel> items) {
        listMoviesFav.clear();
        listMoviesFav.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_favorite, viewGroup, false);
        return new MovieFavoriteAdapter.MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        movieViewHolder.bind(listMoviesFav.get(position));
    }

    @Override
    public int getItemCount() {
        return listMoviesFav.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvTitle, tvDescription;
        ImageView imgPhoto;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvDescription = itemView.findViewById(R.id.tv_item_dateReleased);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);


            itemView.setOnClickListener(this);
        }

        void bind(MovieFavoriteModel movieFavorite) {
            String url_image =  movieFavorite.getPhoto();

            tvTitle.setText(movieFavorite.getTitle());
            tvDescription.setText(movieFavorite.getRelease_date());

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .transform(new RoundedCornersTransformation(40, 30))
                    .dontAnimate()
                    .into(imgPhoto);

        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            MovieFavoriteModel movie = listMoviesFav.get(position);

            Intent moveWithObjectIntent = new Intent(itemView.getContext(), MovieShowActivity.class);
            moveWithObjectIntent.putExtra(MovieShowActivity.EXTRA_POSITION, position);
            moveWithObjectIntent.putExtra(MovieShowActivity.EXTRA_MOVIE_FAVORITE, listMoviesFav.get(position));
            moveWithObjectIntent.putExtra(MovieShowActivity.EXTRA_MOVIE_FAVORITE, movie);
            ((Activity) itemView.getContext()).startActivityForResult(moveWithObjectIntent, MovieShowActivity.REQUEST_UPDATE);
        }
    }


    private final Activity activity;

    public MovieFavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<? extends Parcelable> getAllMoviesFav() {
        return listMoviesFav;
    }

    public void setListMoviesFav(ArrayList<MovieFavoriteModel> listMoviesFav) {

        if (listMoviesFav.size() > 0) {
            this.listMoviesFav.clear();
        }
        this.listMoviesFav.addAll(listMoviesFav);

        notifyDataSetChanged();
    }

    public void addItem(MovieFavoriteModel movieFavorite) {
        this.listMoviesFav.add(movieFavorite);
        notifyItemInserted(listMoviesFav.size() - 1);
    }

    public void removeItem(int position) {
        this.listMoviesFav.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listMoviesFav.size());
    }
}
