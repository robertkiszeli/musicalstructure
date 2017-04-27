package com.robertkiszelirk.musicalstructure;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static TextView nowPlayingSongText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(ContextCompat.checkSelfPermission(getBaseContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showExplanation(getString(R.string.permission_needed), getString(R.string.read_external_storage), Manifest.permission.READ_EXTERNAL_STORAGE, 1);
            }
            else {
                requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,1);
            }

        }else {

            setData();

        }
    }

    private void setData() {

        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.artists));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.albums));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.songs));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.setCurrentItem(1);

        nowPlayingSongText = (TextView) findViewById(R.id.now_playing_song_text);

        LinearLayout nowPlayingSongLayout = (LinearLayout) findViewById(R.id.now_playing_song_layout);
        nowPlayingSongLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nowPlayingSongText.getText().equals("")){
                    Intent intent = new Intent(getApplication(),SongActivity.class);
                    intent.putExtra(getString(R.string.song_name_string),nowPlayingSongText.getText());
                    startActivity(intent);
                }
            }
        });

    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults){
            switch (requestCode) {
                case 1:
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(MainActivity.this, R.string.permission_granted, Toast.LENGTH_SHORT).show();
                        setData();
                    } else {
                        Toast.makeText(MainActivity.this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
                    }
            }
    }

    public static void setCurrentSong(String song){
        nowPlayingSongText.setText(song);
    }
}
