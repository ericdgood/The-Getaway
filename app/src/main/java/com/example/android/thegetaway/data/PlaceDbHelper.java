package com.example.android.thegetaway.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLDataException;

public class PlaceDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "places.db";

    private static final int DATABASE_VERSION = 1;

    public PlaceDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_PLACE_TABLE = "CREATE TABLE " + PlaceContract.PlaceEntry.TABLE_NAME + "("
                + PlaceContract.PlaceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PlaceContract.PlaceEntry.COLUMN_PLACE_NAME +" TEXT NOT NULL, "
                + PlaceContract.PlaceEntry.COLUMN_PLACE_LOCATION + "TEXT NOT NULL, "
                + PlaceContract.PlaceEntry.COLUMN_START_TIME + "INT NOT NULL, "
                + PlaceContract.PlaceEntry.COLUMN_END_TIME + "INT NOT NULL, ";

        db.execSQL(SQL_CREATE_PLACE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
