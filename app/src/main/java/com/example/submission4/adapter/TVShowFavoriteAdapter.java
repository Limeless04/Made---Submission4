package com.example.submission4.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.submission4.R;
import com.example.submission4.activity.TVShowShowActivity;
import com.example.submission4.model.TVShowFavoriteModel;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TVShowFavoriteAdapter extends RecyclerView.Adapter<TVShowFavoriteAdapter.TvShowViewHolder> {

    private ArrayList<TVShowFavoriteModel> listTvShowFav = new ArrayList<>();

    private void setData(ArrayList<TVShowFavoriteModel>items) {
        listTvShowFav.clear();
        listTvShowFav.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
                                               int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tvshow_favorite, viewGroup, false);
        return new TvShowViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder tvShowViewHolder, int position) {
        tvShowViewHolder.bind(listTvShowFav.get(position));
    }

    @Override
    public int getItemCount() {
        return listTvShowFav.size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvTitle, tvDescription;
        ImageView imgPhoto;

        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvDescription = itemView.findViewById(R.id.tv_item_dateReleased);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);


            itemView.setOnClickListener(this);
        }

        void bind(TVShowFavoriteModel tvShowFavorite) {
            String url_image =  tvShowFavorite.getTv_photo();

            tvTitle.setText(tvShowFavorite.getTv_title());
            tvDescription.setText(tvShowFavorite.getTv_release_date());

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

            TVShowFavoriteModel tvShowFavorite = listTvShowFav.get(position);

            Intent moveWithObjectIntent = new Intent(itemView.getContext(), TVShowShowActivity.class);
            moveWithObjectIntent.putExtra(TVShowShowActivity.EXTRA_POSITION, position);
            moveWithObjectIntent.putExtra(TVShowShowActivity.EXTRA_TV_FAVORITE, listTvShowFav.get(position));
            moveWithObjectIntent.putExtra(TVShowShowActivity.EXTRA_TV_FAVORITE, tvShowFavorite);
            ((Activity) itemView.getContext()).startActivityForResult(moveWithObjectIntent, TVShowShowActivity.REQUEST_UPDATE);
        }
    }

    private final Activity activity;

    public TVShowFavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<TVShowFavoriteModel> getListTvShowFav() {
        return listTvShowFav;
    }

    public void setListTvShowFav(ArrayList<TVShowFavoriteModel> listTvShowFav) {

        if (listTvShowFav.size() > 0) {
            this.listTvShowFav.clear();
        }
        this.listTvShowFav.addAll(listTvShowFav);

        notifyDataSetChanged();
    }

    public void addItem(TVShowFavoriteModel tvShowFavorite) {
        this.listTvShowFav.add(tvShowFavorite);
        notifyItemInserted(listTvShowFav.size() - 1);
    }

    public void removeItem(int position) {
        this.listTvShowFav.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listTvShowFav.size());
    }


}
