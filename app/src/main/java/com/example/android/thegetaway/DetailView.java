package com.example.android.thegetaway;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.thegetaway.data.PlaceContract.PlaceEntry;

public class DetailView extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_INVENTORY_LOADER = 0;
    private Uri mCurrentProductUri;

    private TextView mProductNameViewText;
    private TextView mProductPriceViewText;
    private TextView mProductQuantityViewText;
    private TextView mProductSupplieName;
    private TextView mProductSupplierPhoneNumberViewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        mProductNameViewText = findViewById(R.id.place_name_detail);
        mProductPriceViewText = findViewById(R.id.place_location_detail);
        mProductQuantityViewText = findViewById(R.id.start_time_detail);
        mProductSupplieName = findViewById(R.id.end_time_detail);
        mProductSupplierPhoneNumberViewText = findViewById(R.id.checklist_detail);

        Intent intent = getIntent();
        mCurrentProductUri = intent.getData();
        if (mCurrentProductUri == null) {
            invalidateOptionsMenu();
        } else {
            getLoaderManager().initLoader(EXISTING_INVENTORY_LOADER, null, this);
        }

        Log.d("message", "onCreate ViewActivity");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                PlaceEntry._ID,
                PlaceEntry.COLUMN_PLACE_NAME,
                PlaceEntry.COLUMN_PLACE_LOCATION,
                PlaceEntry.COLUMN_START_TIME,
                PlaceEntry.COLUMN_END_TIME,
                PlaceEntry.COLUMN_CHECKLIST,};

        return new CursorLoader(this,
                mCurrentProductUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if (cursor.moveToFirst()) {

            final int idColumnIndex = cursor.getColumnIndex(PlaceEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PlaceEntry.COLUMN_PLACE_NAME);
            int priceColumnIndex = cursor.getColumnIndex(PlaceEntry.COLUMN_PLACE_LOCATION);
            int quantityColumnIndex = cursor.getColumnIndex(PlaceEntry.COLUMN_START_TIME);
            int supplierNameColumnIndex = cursor.getColumnIndex(PlaceEntry.COLUMN_END_TIME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(PlaceEntry.COLUMN_CHECKLIST);

            String currentName = cursor.getString(nameColumnIndex);
            String currentPrice = cursor.getString(priceColumnIndex);
            final int currentQuantity = cursor.getInt(quantityColumnIndex);
            final int currentSupplierName = cursor.getInt(supplierNameColumnIndex);
            String currentSupplierPhone = cursor.getString(supplierPhoneColumnIndex);

            mProductNameViewText.setText(currentName);
            mProductPriceViewText.setText(currentPrice);
            mProductQuantityViewText.setText(Integer.toString(currentQuantity));
            mProductSupplieName.setText(Integer.toString(currentSupplierName));
            mProductSupplierPhoneNumberViewText.setText(currentSupplierPhone);

            Button productDeleteButton = findViewById(R.id.delete_button);
            productDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteConfirmationDialog();
                }
            });

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void deleteProduct() {
        if (mCurrentProductUri != null) {
            int rowsDeleted = getContentResolver().delete(mCurrentProductUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.Good),
                        Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteProduct();
            }
        });
        builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
