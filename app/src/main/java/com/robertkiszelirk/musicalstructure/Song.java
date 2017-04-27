package com.robertkiszelirk.musicalstructure;

class Song {

    private String songTitle;
    private String songArtist;
    private String songAlbum;


    Song(String artist, String title, String album){
        songArtist = artist;
        songTitle = title;
        songAlbum = album;
    }

    String getSongTitle() {
        return songTitle;
    }

    String getSongArtist() {
        return songArtist;
    }

    String getSongAlbum() {
        return songAlbum;
    }
}
