package com.example.databaseapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.Room;

import com.example.databaseapp.Database.Album;
import com.example.databaseapp.Database.AlbumSong;
import com.example.databaseapp.Database.MusicDao;
import com.example.databaseapp.Database.MusicDatabase;
import com.example.databaseapp.Database.Song;

public class MusicProvider extends ContentProvider {
    private static final String TAG = MusicProvider.class.getSimpleName();
    private static final String AUTHORITY = "example.com.databaseapp.musicprovider";
    private static final String TABLE_ALBUM = "album";
    private static final String TABLE_SONG = "song";
    private static final String TABLE_ALBUMSONG = "albumsong";

    private static final int ALBUM_TABLE_CODE = 100;
    private static final int ALBUM_ROW_CODE = 101;
    private static final int SONG_TABLE_CODE = 102;
    private static final int SONG_ROW_CODE = 103;
    private static final int ALBUMSONG_TABLE_CODE = 104;
    private static final int ALBUMSONG_ROW_CODE = 105;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    // To compare the returned URI with codes
    static {
        URI_MATCHER.addURI(AUTHORITY, TABLE_ALBUM, ALBUM_TABLE_CODE);
        URI_MATCHER.addURI(AUTHORITY, TABLE_ALBUM + "/*", ALBUM_ROW_CODE);
        URI_MATCHER.addURI(AUTHORITY, TABLE_SONG, SONG_TABLE_CODE);
        URI_MATCHER.addURI(AUTHORITY, TABLE_SONG + "/*", SONG_ROW_CODE);
        URI_MATCHER.addURI(AUTHORITY, TABLE_ALBUMSONG, ALBUMSONG_TABLE_CODE);
        URI_MATCHER.addURI(AUTHORITY, TABLE_ALBUMSONG + "/*", ALBUMSONG_ROW_CODE);
    }

    private MusicDao mMusicDao;

    public MusicProvider() {
    }

    @Override
    public boolean onCreate() {
        if (getContext() != null) {
            mMusicDao = Room.databaseBuilder(getContext().getApplicationContext(), MusicDatabase.class, "music_database")
                    .build()
                    .getMusicDao();
            return true;
        }

        return false;
    }

    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case ALBUM_TABLE_CODE:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_ALBUM;
            case ALBUM_ROW_CODE:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_ALBUM;
            case SONG_TABLE_CODE:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_SONG;
            case SONG_ROW_CODE:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_SONG;
            case ALBUMSONG_TABLE_CODE:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_ALBUMSONG;
            case ALBUMSONG_ROW_CODE:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_ALBUMSONG;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int code = URI_MATCHER.match(uri);
        Cursor cursor;
        switch (code) {
            case ALBUM_TABLE_CODE:
                cursor = mMusicDao.getAlbumsCursor();
                break;
            case ALBUM_ROW_CODE:
                cursor = mMusicDao.getAlbumWithIdCursor((int) ContentUris.parseId(uri));
                break;
            case SONG_TABLE_CODE:
                cursor = mMusicDao.getSongsCursor();
                break;
            case SONG_ROW_CODE:
                cursor = mMusicDao.getSongWithIdCursor((int) ContentUris.parseId(uri));
                break;
            case ALBUMSONG_TABLE_CODE:
                cursor = mMusicDao.getAlbumSongsCursor();
                break;
            case ALBUMSONG_ROW_CODE:
                cursor = mMusicDao.getAlbumSongWithIdCursor((int) ContentUris.parseId(uri));
                break;
            default:
                return null;
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (URI_MATCHER.match(uri) == ALBUM_TABLE_CODE && isValuesValid(values)) {
            Album album = new Album();
            Integer id = values.getAsInteger("id");
            album.setId(id);
            album.setName(values.getAsString("name"));
            album.setReleaseDate(values.getAsString("release"));
            mMusicDao.insertAlbum(album);
            return ContentUris.withAppendedId(uri, id);

        } else if (URI_MATCHER.match(uri) == SONG_TABLE_CODE && isValuesValid(values)) {
            Song song = new Song();
            int id = values.getAsInteger("id");
            song.setId(id);
            song.setName(values.getAsString("name"));
            song.setDuration(values.getAsString("duration"));
            mMusicDao.insertSong(song);
            return ContentUris.withAppendedId(uri, id);

        } else if (URI_MATCHER.match(uri) == ALBUMSONG_TABLE_CODE && isValuesValid(values)) {
            int id = values.getAsInteger("id");
            int albumId = values.getAsInteger("album_id");
            int songId = values.getAsInteger("song_id");
            AlbumSong albumSong = new AlbumSong(id, albumId, songId);
            mMusicDao.insertAlbumSong(albumSong);
            return ContentUris.withAppendedId(uri, id);

        } else {
            throw new IllegalArgumentException("cant add multiple items");
        }
    }

    private boolean isValuesValid(ContentValues values) {
        return (values.containsKey("id") && values.containsKey("name") && values.containsKey("release"))
                || (values.containsKey("id") && values.containsKey("name") && values.containsKey("duration"))
                || (values.containsKey("id") && values.containsKey("album_id") && values.containsKey("song_id"));
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        if (URI_MATCHER.match(uri) == ALBUM_ROW_CODE && isValuesValid(values)) {
            Album album = new Album();
            Integer id = values.getAsInteger("id");
            album.setId(id);
            album.setName(values.getAsString("name"));
            album.setReleaseDate(values.getAsString("release"));
            return mMusicDao.updateAlbumInfo(album);

        } else if (URI_MATCHER.match(uri) == SONG_ROW_CODE && isValuesValid(values)) {
            Song song = new Song();
            int id = values.getAsInteger("id");
            song.setId(id);
            song.setName(values.getAsString("name"));
            song.setDuration(values.getAsString("duration"));
            return mMusicDao.updateSongInfo(song);

        } else if (URI_MATCHER.match(uri) == ALBUMSONG_ROW_CODE && isValuesValid(values)) {
            int id = values.getAsInteger("id");
            int albumId = values.getAsInteger("album_id");
            int songId = values.getAsInteger("song_id");
            AlbumSong albumSong = new AlbumSong(id, albumId, songId);
            return mMusicDao.updateAlbumSongInfo(albumSong);

        } else {
            throw new IllegalArgumentException("cant update multiple items");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (URI_MATCHER.match(uri) == ALBUM_ROW_CODE) {
            int id = (int) ContentUris.parseId(uri);
            return mMusicDao.deleteAlbumById(id);

        } else if (URI_MATCHER.match(uri) == SONG_ROW_CODE) {
            int id = (int) ContentUris.parseId(uri);
            return mMusicDao.deleteSongsById(id);

        } else if (URI_MATCHER.match(uri) == ALBUMSONG_ROW_CODE) {
            int id = (int) ContentUris.parseId(uri);
            return mMusicDao.deleteAlbumSongsById(id);

        } else {
            throw new IllegalArgumentException("cant delete multiple items");
        }
    }
}