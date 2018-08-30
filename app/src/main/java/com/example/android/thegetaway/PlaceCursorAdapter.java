package com.example.android.thegetaway;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.thegetaway.data.PlaceContract;

public class PlaceCursorAdapter extends CursorAdapter {

    public PlaceCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.place_list_view, parent, false);
    }

    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.place_name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.start_time);
        TextView summaryQtyView = (TextView) view.findViewById(R.id.end_time);

        final int columnIdIndex = cursor.getColumnIndex(PlaceContract.PlaceEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(PlaceContract.PlaceEntry.COLUMN_PLACE_NAME);
        int priceColumnIndex = cursor.getColumnIndex(PlaceContract.PlaceEntry.COLUMN_START_TIME);
        int qtyColumnIndex = cursor.getColumnIndex(PlaceContract.PlaceEntry.COLUMN_END_TIME);

        final String productID = cursor.getString(columnIdIndex);
        String bookName = cursor.getString(nameColumnIndex);
        String bookprice = cursor.getString(priceColumnIndex);
        String bookQty = cursor.getString(qtyColumnIndex);

        nameTextView.setText(bookName);
        summaryTextView.setText(bookprice);
        summaryQtyView.setText(bookQty);

    }
}
