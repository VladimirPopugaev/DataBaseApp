package com.example.databaseapp.Database;


import static androidx.room.OnConflictStrategy.REPLACE;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MusicDao {

    //------- FOR TABLE "ALBUM" --------

    // insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbums(List<Album> albums);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbum(Album albums);

    //getters
    @Query("SELECT * FROM album")
    List<Album> getAlbums();

    @Query("SELECT * FROM album")
    Cursor getAlbumsCursor();

    @Query("SELECT * FROM album WHERE id = :albumId")
    Cursor getAlbumWithIdCursor(int albumId);

    // update
    @Update
    int updateAlbumInfo(Album album);

    // delete
    @Query("DELETE FROM album WHERE id = :albumId")
    int deleteAlbumById(int albumId);

    @Delete
    void deleteAlbum(Album album);


    //------- FOR TABLE "SONG" --------

    // insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSongs(List<Song> songs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSong(Song song);

    // getters
    @Query("SELECT * FROM song")
    List<Song> getSongs();

    @Query("SELECT * FROM song")
    Cursor getSongsCursor();

    @Query("SELECT * FROM song WHERE id = :songId")
    Cursor getSongWithIdCursor(int songId);

    @Query("SELECT * FROM song INNER JOIN albumsong ON  song.id = albumsong.song_id WHERE album_id = :albumId")
    List<Song> getSongsFromAlbum(int albumId);

    // update
    @Update
    int updateSongInfo(Song song);

    // delete
    @Query("DELETE FROM song WHERE id = :songId")
    int deleteSongsById(int songId);

    @Delete
    void deleteSong(Song song);

    //------- FOR TABLE "ALBUMSONG" --------

    // insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbumsSong(List<AlbumSong> albumSongs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbumSong(AlbumSong albumSong);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void setLinksAlbumSongs(List<AlbumSong> linksAlbumSongs);

    // getters
    @Query("SELECT * FROM albumsong")
    List<AlbumSong> getAlbumSong();

    @Query("SELECT * FROM albumsong")
    Cursor getAlbumSongsCursor();

    @Query("SELECT * FROM albumsong WHERE id = :albumSongId")
    Cursor getAlbumSongWithIdCursor(int albumSongId);

    // update
    @Update
    int updateAlbumSongInfo(AlbumSong albumSong);

    // delete
    @Query("DELETE FROM albumsong WHERE id = :albumSongId")
    int deleteAlbumSongsById(int albumSongId);

    @Delete
    void deleteAlbumSong(AlbumSong albumSong);

}
