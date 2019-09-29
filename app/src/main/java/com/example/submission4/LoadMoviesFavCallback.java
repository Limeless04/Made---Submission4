package com.example.submission4;

import com.example.submission4.model.MovieFavoriteModel;

import java.util.ArrayList;

public interface LoadMoviesFavCallback {
    void preExecute();

    void postExecute(ArrayList<MovieFavoriteModel> movieFavorites);
}
