package com.example.submission4;

import com.example.submission4.model.TVShowFavoriteModel;

import java.util.ArrayList;

public interface LoadTvFavCallback {
    void preExecute();

    void postExecute(ArrayList<TVShowFavoriteModel> tvShowFavorites);
}
