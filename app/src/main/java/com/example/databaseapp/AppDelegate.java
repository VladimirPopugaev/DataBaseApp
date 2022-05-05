package com.example.databaseapp;

import android.app.Application;

import androidx.room.Room;

import com.example.databaseapp.Database.MusicDatabase;

public class AppDelegate extends Application {

    private MusicDatabase musicDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        musicDatabase = Room.databaseBuilder(getApplicationContext(),MusicDatabase.class,"music_database")
                .allowMainThreadQueries()
                .build();
    }

    public MusicDatabase getMusicDatabase() {
        return musicDatabase;
    }
}
