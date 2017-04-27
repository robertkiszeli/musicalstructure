package com.robertkiszelirk.musicalstructure;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private ArrayList<Song> songsList;

    private  Context context;

    SongAdapter(Context context, ArrayList<Song> songs){
        this.songsList = songs;
        this.context = context;
    }

    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View songsView = layoutInflater.inflate(R.layout.recycler_item_song,parent,false);

        return new ViewHolder(songsView);
    }

    @Override
    public void onBindViewHolder(SongAdapter.ViewHolder holder, int position) {

        holder.artistName.setText(songsList.get(position).getSongArtist());
        holder.albumName.setText(songsList.get(position).getSongAlbum());
        holder.songName.setText(songsList.get(position).getSongTitle());

    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView artistName;
        TextView albumName;
        TextView songName;

        ViewHolder(View itemView) {
            super(itemView);

            artistName = (TextView) itemView.findViewById(R.id.song_artist);
            albumName = (TextView) itemView.findViewById(R.id.song_album);
            songName = (TextView) itemView.findViewById(R.id.song_title);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            MainActivity.setCurrentSong(songsList.get(getAdapterPosition()).getSongTitle());
        }
    }

}
