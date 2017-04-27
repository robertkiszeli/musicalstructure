package com.robertkiszelirk.musicalstructure.adapters;

public class Song {

    private String songTitle;
    private String songArtist;
    private String songAlbum;


    public Song(String artist, String title, String album){
        songArtist = artist;
        songTitle = title;
        songAlbum = album;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public String getSongAlbum() {
        return songAlbum;
    }
}
