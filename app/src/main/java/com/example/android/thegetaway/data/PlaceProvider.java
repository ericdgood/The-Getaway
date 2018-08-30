package com.example.android.thegetaway.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.thegetaway.R;

public class PlaceProvider extends ContentProvider {

    public static final String LOG_TAG = PlaceProvider.class.getSimpleName();

    private PlaceDbHelper mDbHelper;

    private static final int PLACES = 100;

    private static final int PLACE_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(PlaceContract.CONTENT_AUTHORITY, PlaceContract.PATH_PLACES, PLACES);

        sUriMatcher.addURI(PlaceContract.CONTENT_AUTHORITY, PlaceContract.PATH_PLACES + "/#", PLACE_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new PlaceDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case PLACES:
                cursor = database.query(PlaceContract.PlaceEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case PLACE_ID:
                selection = PlaceContract.PlaceEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(PlaceContract.PlaceEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException(String.valueOf(R.string.no_query) + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PLACES:
                return PlaceContract.PlaceEntry.CONTENT_LIST_TYPE;
            case PLACE_ID:
                return PlaceContract.PlaceEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI" + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PLACES:
                return insertPlace(uri, contentValues);
            default:
                throw new IllegalArgumentException(String.valueOf("Not Supported") + uri);
        }
    }

    private Uri insertPlace(Uri uri, ContentValues values) {
        String name = values.getAsString(PlaceContract.PlaceEntry.COLUMN_PLACE_NAME);
        if (name == null) {
            throw new IllegalArgumentException(String.valueOf("need place name"));
        }
        String loc = values.getAsString(PlaceContract.PlaceEntry.COLUMN_PLACE_LOCATION);
        if (loc == null) {
            throw new IllegalArgumentException(String.valueOf("need place location"));
        }
        String start = values.getAsString(PlaceContract.PlaceEntry.COLUMN_START_TIME);
        if (start == null) {
            throw new IllegalArgumentException(String.valueOf("need start time"));
        }
        String end = values.getAsString(PlaceContract.PlaceEntry.COLUMN_END_TIME);
        if (end == null) {
            throw new IllegalArgumentException(String.valueOf("need end time"));
        }
        String check = values.getAsString(PlaceContract.PlaceEntry.COLUMN_CHECKLIST);
        if (check == null) {
            throw new IllegalArgumentException(String.valueOf("need check list"));
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(PlaceContract.PlaceEntry.TABLE_NAME, null, values);


        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PLACES:
                return updatePlace(uri, contentValues, selection, selectionArgs);
            case PLACE_ID:
                selection = PlaceContract.PlaceEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updatePlace(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException(String.valueOf(R.string.no_query) + uri);
        }
    }

    private int updatePlace(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(PlaceContract.PlaceEntry.COLUMN_PLACE_NAME)) {
            String name = values.getAsString(PlaceContract.PlaceEntry.COLUMN_PLACE_NAME);
            if (name == null) {
                throw new IllegalArgumentException(String.valueOf("need place name"));
            }
            String loc = values.getAsString(PlaceContract.PlaceEntry.COLUMN_PLACE_LOCATION);
            if (loc == null) {
                throw new IllegalArgumentException(String.valueOf("need place location"));
            }
            String start = values.getAsString(PlaceContract.PlaceEntry.COLUMN_START_TIME);
            if (start == null) {
                throw new IllegalArgumentException(String.valueOf("need start time"));
            }
            String end = values.getAsString(PlaceContract.PlaceEntry.COLUMN_END_TIME);
            if (end == null) {
                throw new IllegalArgumentException(String.valueOf("need end time"));
            }
            String check = values.getAsString(PlaceContract.PlaceEntry.COLUMN_CHECKLIST);
            if (check == null) {
                throw new IllegalArgumentException(String.valueOf("need check list"));
            }
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(PlaceContract.PlaceEntry.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PLACES:

                rowsDeleted = database.delete(PlaceContract.PlaceEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PLACE_ID:
                selection = PlaceContract.PlaceEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(PlaceContract.PlaceEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException(String.valueOf(R.string.no_query) + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

}
