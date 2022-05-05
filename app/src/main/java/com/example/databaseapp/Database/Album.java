package com.example.databaseapp.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Album {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "release")
    private String mReleaseDate;

    public Album() {
    }

    public Album(int id, String name, String releaseDate) {
        this.mId = id;
        this.mName = name;
        this.mReleaseDate = releaseDate;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.mReleaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Album{" + "mId=" + mId +
                ", mName='" + mName +
                ", mReleaseDate='" + mReleaseDate + '}';
    }
}
