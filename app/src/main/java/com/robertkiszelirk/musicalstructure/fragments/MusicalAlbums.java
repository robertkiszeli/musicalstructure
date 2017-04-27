package com.robertkiszelirk.musicalstructure.fragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.robertkiszelirk.musicalstructure.R;
import com.robertkiszelirk.musicalstructure.activities.AlbumActivity;
import com.robertkiszelirk.musicalstructure.adapters.AlbumAdapter;
import com.robertkiszelirk.musicalstructure.adapters.Song;

import java.util.ArrayList;

public class MusicalAlbums extends Fragment {

    private ArrayList<Song> songsList;

    private ArrayList<String> albumsList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        songsList = new ArrayList<>();

        albumsList = new ArrayList<>();

        fillSongsList();

        fillAlbumsList();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View baseView = inflater.inflate(R.layout.musical_albums, container,false);

        GridView gridView = (GridView) baseView.findViewById(R.id.musical_albums_grid_view);
        gridView.setAdapter(new AlbumAdapter(this.getContext(),albumsList));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(),AlbumActivity.class);
                intent.putExtra(getString(R.string.album_name_string),albumsList.get(position));
                startActivity(intent);
            }
        });

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

    private void fillAlbumsList() {

        for(Song song : songsList){
            if(!albumsList.contains(song.getSongAlbum())){
                albumsList.add(song.getSongAlbum());
            }
        }

    }

}
