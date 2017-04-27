package com.robertkiszelirk.musicalstructure.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.robertkiszelirk.musicalstructure.R;

public class SongActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_song);

        Intent intent = getIntent();
        String songName = intent.getStringExtra(getString(R.string.song_name_string));

        final Toolbar songToolbar = (Toolbar) findViewById(R.id.song_toolbar);
        songToolbar.setTitle("" + songName);
        setSupportActionBar(songToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView songArtistText = (TextView) findViewById(R.id.song_artist_single_song);
        TextView songAlbumText = (TextView) findViewById(R.id.song_album_single_song);
        TextView songTitleText = (TextView) findViewById(R.id.song_title_single_song);

        ContentResolver contentResolver = this.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(songUri, null, null, null, null);

        if(cursor != null && cursor.moveToFirst()){

            int songTitleColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songAlbumColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int songArtistColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

            do{
                if(cursor.getString(songTitleColumnIndex).equals(songName)) {
                    String artist = cursor.getString(songArtistColumnIndex);
                    songArtistText.setText(artist);
                    String album = cursor.getString(songAlbumColumnIndex);
                    songAlbumText.setText(album);
                    String title = cursor.getString(songTitleColumnIndex);
                    songTitleText.setText(title);
                }

            }while(cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
    }
}
