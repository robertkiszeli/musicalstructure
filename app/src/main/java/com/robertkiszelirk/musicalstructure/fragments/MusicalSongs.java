package com.robertkiszelirk.musicalstructure.fragments;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robertkiszelirk.musicalstructure.R;
import com.robertkiszelirk.musicalstructure.adapters.Song;
import com.robertkiszelirk.musicalstructure.adapters.SongAdapter;

import java.util.ArrayList;

public class MusicalSongs extends Fragment {

    private ArrayList<Song> songsList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        songsList = new ArrayList<>();

        fillSongsList();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View baseView = inflater.inflate(R.layout.musical_songs, container,false);

        RecyclerView recyclerView = (RecyclerView) baseView.findViewById(R.id.musical_songs_recycler_view);
        SongAdapter songAdapter = new SongAdapter(getContext(),songsList);
        recyclerView.setAdapter(songAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);



        return baseView;
    }

    private void fillSongsList() {

        ContentResolver contentResolver = getContext().getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(songUri, null, null, null, null);

        if(cursor != null && cursor.moveToFirst()){

            int songArtistColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songTitleColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songAlbumColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);


            do{
                songsList.add(new Song(cursor.getString(songArtistColumnIndex),
                        cursor.getString(songTitleColumnIndex),
                        cursor.getString(songAlbumColumnIndex)));

            }while(cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
    }

}
