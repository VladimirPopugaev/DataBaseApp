package com.example.databaseapp.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Song {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "duration")
    private String mDuration;

    public Song() {
    }

    public Song(int mId, String mName, String mDuration) {
        this.mId = mId;
        this.mName = mName;
        this.mDuration = mDuration;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        this.mDuration = duration;
    }

    @Override
    public String toString() {
        return "Song{" + "mId=" + mId +
                ", mName='" + mName +
                ", mDuration='" + mDuration + '}';
    }
}
