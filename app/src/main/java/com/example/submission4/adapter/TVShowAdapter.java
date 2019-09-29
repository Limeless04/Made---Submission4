package com.example.submission4.adapter;

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
import com.example.submission4.activity.TVShowDetailActivity;
import com.example.submission4.model.TVShowModel;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder> {

    private ArrayList<TVShowModel> tvData = new ArrayList<>();

    public void setTvData(ArrayList<TVShowModel> items) {
        tvData.clear();
        tvData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View tvView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview_tvshow, viewGroup, false);
        return new TVShowViewHolder(tvView);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder tvShowViewHolder, int position) {
        tvShowViewHolder.bind(tvData.get(position));
    }

    @Override
    public int getItemCount() {
        return tvData.size();
    }

    class TVShowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imgPhoto;
        TextView textViewTitle, textViewDateReleased, textViewVoteAverage, textViewVoteCount;

        TVShowViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.tv_item_title);
            textViewDateReleased = itemView.findViewById(R.id.tv_item_dateReleased);
            textViewVoteAverage = itemView.findViewById(R.id.tv_item_voteAverege);
            textViewVoteCount = itemView.findViewById(R.id.tv_item_voteCount);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);

            itemView.setOnClickListener(this);
        }

        void bind(TVShowModel tvShow) {
            String vote_average = Double.toString(tvShow.getVote_average());
            String url_image = "https://image.tmdb.org/t/p/w185" + tvShow.getPoster_path();


            textViewTitle.setText(tvShow.getName());
            textViewDateReleased.setText(tvShow.getFirst_air_date());
            textViewVoteCount.setText(tvShow.getVote_count());
            textViewVoteAverage.setText(vote_average);

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .transform(new RoundedCornersTransformation(40, 30))
                    .dontAnimate()
                    .into(imgPhoto);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            TVShowModel tvShow = tvData.get(position);

            tvShow.setName(tvShow.getName());
            tvShow.setOverview(tvShow.getOverview());

            Intent moveWithObjectIntent = new Intent(itemView.getContext(), TVShowDetailActivity.class);
            moveWithObjectIntent.putExtra(TVShowDetailActivity.EXTRA_TV_SHOW, tvShow);
            itemView.getContext().startActivity(moveWithObjectIntent);

        }
    }
}
