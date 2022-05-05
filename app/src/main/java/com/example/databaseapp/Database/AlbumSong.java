package com.example.databaseapp.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Album.class, parentColumns = "id", childColumns = "album_id"),
        @ForeignKey(entity = Song.class, parentColumns = "id", childColumns = "song_id")})
public class AlbumSong {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "album_id")
    private int mAlbumId;

    @ColumnInfo(name = "song_id")
    private int mSongId;

    public AlbumSong() {
    }

    public AlbumSong(int id, int albumId, int songId) {
        this.mId = id;
        this.mAlbumId = albumId;
        this.mSongId = songId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getAlbumId() {
        return mAlbumId;
    }

    public void setAlbumId(int mAlbumId) {
        this.mAlbumId = mAlbumId;
    }

    public int getSongId() {
        return mSongId;
    }

    public void setSongId(int mSongId) {
        this.mSongId = mSongId;
    }

    @Override
    public String toString() {
        return "AlbumSong{" + "mId=" + mId +
                ", mAlbumId=" + mAlbumId +
                ", mSongId=" + mSongId + '}';
    }
}
