package com.example.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.databaseapp.Database.Album;
import com.example.databaseapp.Database.AlbumSong;
import com.example.databaseapp.Database.MusicDao;
import com.example.databaseapp.Database.MusicDatabase;
import com.example.databaseapp.Database.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button mAddBtn;
    private Button mGetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MusicDao musicDao = ((AppDelegate) getApplicationContext()).getMusicDatabase().getMusicDao();

        mAddBtn = findViewById(R.id.add);
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Album> albums = createAlbums();
                musicDao.insertAlbums(albums);

                List<Song> songs = createSongs();
                musicDao.insertSongs(songs);

                musicDao.insertAlbumsSong(createAlbumSong(albums,songs));
            }
        });

        mGetBtn = findViewById(R.id.get);
        mGetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showToast(musicDao.getAlbums(), musicDao.getSongs(), musicDao.getAlbumSong());
            }
        });
    }



    private List<Album> createAlbums() {
        List<Album> albums = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            albums.add(new Album(i,"album " + i, "release: " + System.currentTimeMillis()));
        }
        return albums;
    }

    private List<Song> createSongs() {
        Random random = new Random();
        List<Song> songs = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            songs.add(new Song(i, "song "+ i, "duration " + random.nextInt(500)));
        }
        return songs;
    }

    private List<AlbumSong> createAlbumSong(List<Album> albums, List<Song> songs) {
        List<AlbumSong> albumSongs = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            albumSongs.add(new AlbumSong(i, albums.get(i).getId(), songs.get(i).getId()));
        }
        return albumSongs;
    }

    private void showToast(List<Album> albums, List<Song> songs, List<AlbumSong> albumSongs) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            stringBuilder.append(albums.get(i).toString()).append("\n");
        }
        stringBuilder.append("\n");

        for (int i = 0; i < 3; i++) {
            stringBuilder.append(songs.get(i).toString()).append("\n");
        }
       stringBuilder.append("\n");

        for (int i = 0; i < 3; i++) {
            stringBuilder.append(albumSongs.get(i).toString()).append("\n");
        }

        Toast.makeText(this, stringBuilder.toString() , Toast.LENGTH_LONG).show();
    }
}