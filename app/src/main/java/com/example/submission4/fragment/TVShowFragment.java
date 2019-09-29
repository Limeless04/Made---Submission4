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
import com.example.submission4.activity.TVShowFavoriteActivity;
import com.example.submission4.adapter.TVShowAdapter;
import com.example.submission4.model.TVShowModel;
import com.example.submission4.viewmodel.TVShowViewModel;

import java.util.ArrayList;

public class TVShowFragment extends Fragment {

    private TVShowAdapter adapter;
    private ProgressBar progressBar;
    private TVShowViewModel tvShowViewModel;

    public TVShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adapter = new TVShowAdapter();
        View view = inflater.inflate(R.layout.activity_tvshow_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_tvShow);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);


        progressBar = view.findViewById(R.id.progressTVShow);

        tvShowViewModel = ViewModelProviders.of(this).get(TVShowViewModel.class);
        tvShowViewModel.getTvShow().observe(this, getTvShow);
        tvShowViewModel.setTvShow("EXTRA_TV_SHOW");

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TVShowFavoriteActivity.class);
                startActivity(intent);
            }
        });
        showLoading(true);

        return view;
    }

    private Observer<ArrayList<TVShowModel>> getTvShow = new Observer<ArrayList<TVShowModel>>() {
        @Override
        public void onChanged(ArrayList<TVShowModel> tvShows) {
            if (tvShows != null) {
                adapter.setTvData(tvShows);
            }

            showLoading(false);
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
