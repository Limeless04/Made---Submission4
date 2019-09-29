package com.example.submission4.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.submission4.R;
import com.example.submission4.activity.MovieFavoriteActivity;
import com.example.submission4.adapter.MovieAdapter;
import com.example.submission4.model.MovieModel;
import com.example.submission4.viewmodel.MovieViewModel;

import java.util.ArrayList;

public class MovieFragment extends Fragment {
    private MovieAdapter adapter;
    private ProgressBar progressMovie;
    private MovieViewModel movieViewModel;

    public MovieFragment() {
        // Construct
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        adapter = new MovieAdapter();
        View view = inflater.inflate(R.layout.activity_movie_fragment,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        progressMovie = view.findViewById(R.id.progressMovie);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovie().observe(this, getMovies);
        movieViewModel.setMovie("EXTRA_MOVIE");

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MovieFavoriteActivity.class);
                startActivity(intent);
            }
        });
        showLoading(true);

        return view;
    }

    private Observer<ArrayList<MovieModel>> getMovies = new Observer<ArrayList<MovieModel>>() {
        @Override
        public void onChanged(ArrayList<MovieModel> movie) {
            if (movie != null) {
                adapter.setData(movie);
            }

            showLoading(false);

        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressMovie.setVisibility(View.VISIBLE);
        } else {
            progressMovie.setVisibility(View.GONE);
        }
    }

}
