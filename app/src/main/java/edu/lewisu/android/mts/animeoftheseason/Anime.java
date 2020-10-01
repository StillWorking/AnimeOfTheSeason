package edu.lewisu.android.mts.animeoftheseason;

import android.os.Parcel;
import android.os.Parcelable;

public class Anime implements Parcelable {
    private String title;
    private String img;
    private String type;
    private String episodes;
    private String producers;
    private String rating;
    private String synopsis;

    public Anime(String title, String img, String type, String episodes, String producers, String rating, String synopsis) {
        this.title = title;
        this.img = img;
        this.type = type;
        this.episodes = episodes;
        this.producers = producers;
        this.rating = rating;
        this.synopsis = synopsis;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

    public String getProducers() {
        return producers;
    }

    public void setProducers(String producers) {
        this.producers = producers;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    protected Anime(Parcel in){
        title = in.readString();
        img = in.readString();
        type = in.readString();
        episodes = in.readString();
        producers = in.readString();
        rating = in.readString();
        synopsis = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(title);
        dest.writeString(img);
        dest.writeString(type);
        dest.writeString(episodes);
        dest.writeString(producers);
        dest.writeString(rating);
        dest.writeString(synopsis);
    }

    public static final Parcelable.Creator<Anime> CREATOR = new Parcelable.Creator<Anime>(){

        @Override
        public Anime createFromParcel(Parcel source) {
            return new Anime(source);
        }

        @Override
        public Anime[] newArray(int size) {
            return new Anime[size];
        }
    };
}
