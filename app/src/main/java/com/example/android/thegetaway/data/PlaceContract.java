package com.example.android.thegetaway.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class PlaceContract {

    public PlaceContract(){}

    public static final String PATH_PLACES = "places";
    public static final String CONTENT_AUTHORITY = "com.example.android.thegetaway";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class PlaceEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PLACES);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLACES;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLACES;

        public final static String TABLE_NAME = "places";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_PLACE_NAME ="name";

        public final static String COLUMN_PLACE_LOCATION = "location";

        public final static String COLUMN_START_TIME = "start";

        public final static String COLUMN_END_TIME = "end";


    }

}
