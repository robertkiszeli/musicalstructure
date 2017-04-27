package com.robertkiszelirk.musicalstructure.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.robertkiszelirk.musicalstructure.R;

import java.util.ArrayList;

public class AlbumAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<String> albumsList;

    public AlbumAdapter(Context context, ArrayList<String> albumsList){
        this.context = context;
        this.albumsList = albumsList;
    }

    @Override
    public int getCount() {
        return albumsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(   R.layout.grid_album,null);

            final TextView songAlbum = (TextView) convertView.findViewById(R.id.song_album);
            songAlbum.setText(albumsList.get(position));
        }

        return convertView;
    }
}