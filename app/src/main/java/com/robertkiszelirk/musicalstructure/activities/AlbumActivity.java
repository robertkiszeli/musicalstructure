package com.robertkiszelirk.musicalstructure.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.robertkiszelirk.musicalstructure.R;

import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {

    private String albumName;

    private ArrayList<String> songsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_album);

        Intent intent = getIntent();
        albumName = intent.getStringExtra(getString(R.string.album_name_string));

        final Toolbar albumToolbar = (Toolbar) findViewById(R.id.album_toolbar);
        albumToolbar.setTitle("" + albumName);
        setSupportActionBar(albumToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        fillSongsList();

        ListView listView = (ListView) findViewById(R.id.songs_list_of_album);

        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songsList);

        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.setCurrentSong(songsList.get(position));
                NavUtils.navigateUpFromSameTask(AlbumActivity.this);
            }
        });
    }

    private void fillSongsList() {

        songsList = new ArrayList<>();

        ContentResolver contentResolver = this.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(songUri, null, null, null, null);

        if(cursor != null && cursor.moveToFirst()){

            int songTitleColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songAlbumColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

            do{
                if(cursor.getString(songAlbumColumnIndex).equals(albumName)) {
                    songsList.add(cursor.getString(songTitleColumnIndex));
                }

            }while(cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
    }
}
