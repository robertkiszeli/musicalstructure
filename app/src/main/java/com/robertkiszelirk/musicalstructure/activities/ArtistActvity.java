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

public class ArtistActvity extends AppCompatActivity{

    private String artistName;

    private ArrayList<String> songsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_artist);

        Intent intent = getIntent();
        artistName = intent.getStringExtra(getString(R.string.artist_name_string));

        final Toolbar artistToolbar = (Toolbar) findViewById(R.id.artist_toolbar);
        artistToolbar.setTitle("" + artistName);
        setSupportActionBar(artistToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        fillSongsList();

        ListView listView = (ListView) findViewById(R.id.songs_list_of_artist);

        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,songsList);

        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.setCurrentSong(songsList.get(position));
                NavUtils.navigateUpFromSameTask(ArtistActvity.this);
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
            int songArtistColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

            do{
                if(cursor.getString(songArtistColumnIndex).equals(artistName)) {
                    songsList.add(cursor.getString(songTitleColumnIndex));
                }

            }while(cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
    }

}
