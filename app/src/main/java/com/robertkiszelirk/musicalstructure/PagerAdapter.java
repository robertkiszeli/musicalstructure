package com.robertkiszelirk.musicalstructure;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

class PagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;

    PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                return new MusicalArtists();
            case 1:
                return new MusicalAlbums();
            case 2:
                return new MusicalSongs();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
