package com.example.submission4.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TVShowFavoriteModel implements Parcelable {
    private int id;
    private String tv_title;
    private String tv_vote_count;
    private String tv_original_language;
    private String tv_overview;
    private String tv_release_date;
    private String tv_vote_average;
    private String tv_photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTv_title() {
        return tv_title;
    }

    public void setTv_title(String tv_title) {
        this.tv_title = tv_title;
    }

    public String getTv_vote_count() {
        return tv_vote_count;
    }

    public void setTv_vote_count(String tv_vote_count) {
        this.tv_vote_count = tv_vote_count;
    }

    public String getTv_original_language() {
        return tv_original_language;
    }

    public void setTv_original_language(String tv_original_language) {
        this.tv_original_language = tv_original_language;
    }

    public String getTv_overview() {
        return tv_overview;
    }

    public void setTv_overview(String tv_overview) {
        this.tv_overview = tv_overview;
    }

    public String getTv_release_date() {
        return tv_release_date;
    }

    public void setTv_release_date(String tv_release_date) {
        this.tv_release_date = tv_release_date;
    }

    public String getTv_vote_average() {
        return tv_vote_average;
    }

    public void setTv_vote_average(String tv_vote_average) {
        this.tv_vote_average = tv_vote_average;
    }

    public String getTv_photo() {
        return tv_photo;
    }

    public void setTv_photo(String tv_photo) {
        this.tv_photo = tv_photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.tv_title);
        dest.writeString(this.tv_vote_count);
        dest.writeString(this.tv_original_language);
        dest.writeString(this.tv_overview);
        dest.writeString(this.tv_release_date);
        dest.writeString(this.tv_vote_average);
        dest.writeString(this.tv_photo);
    }

    public TVShowFavoriteModel() {
    }

    protected TVShowFavoriteModel(Parcel in) {
        this.id = in.readInt();
        this.tv_title = in.readString();
        this.tv_vote_count = in.readString();
        this.tv_original_language = in.readString();
        this.tv_overview = in.readString();
        this.tv_release_date = in.readString();
        this.tv_vote_average = in.readString();
        this.tv_photo = in.readString();
    }

    public static final Parcelable.Creator<TVShowFavoriteModel> CREATOR = new Parcelable.Creator<TVShowFavoriteModel>() {
        @Override
        public TVShowFavoriteModel createFromParcel(Parcel source) {
            return new TVShowFavoriteModel(source);
        }

        @Override
        public TVShowFavoriteModel[] newArray(int size) {
            return new TVShowFavoriteModel[size];
        }
    };
}
