package com.example.android.thegetaway;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.android.thegetaway.data.PlaceContract.PlaceEntry;

public class AddPlace extends AppCompatActivity implements
        android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>{


    private EditText mCheclistInput;
    private EditText mStartInput;
    private EditText mEndInput;
    private EditText mPlaceNameInput;
    private EditText mlocationInput;

    private boolean mPlaceHasChanged = false;

    private static final int EXISTING_BOOK_LOADER = 0;

    private Uri mCurrentPlaceUri;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_place);

        ImageButton btn_choose_photo = (ImageButton) findViewById(R.id.place_image_input); // Replace with id of your button.
        btn_choose_photo.setOnClickListener(btnChoosePhotoPressed);

        Intent intent = getIntent();
        mCurrentPlaceUri = intent.getData();

        if (mCurrentPlaceUri == null) {
            setTitle("Add Place");

            invalidateOptionsMenu();
        } else {
            setTitle("Edit Place");

            getSupportLoaderManager().initLoader(EXISTING_BOOK_LOADER, null, this);
        }

        mPlaceNameInput= findViewById(R.id.place_name_input);
        mlocationInput = findViewById(R.id.place_location_input);
        mStartInput = findViewById(R.id.start_time_input);
        mEndInput= findViewById(R.id.end_time_input);
        mCheclistInput = findViewById(R.id.checklist);

        mPlaceNameInput.setOnTouchListener(mTouchListener);
        mlocationInput.setOnTouchListener(mTouchListener);
        mStartInput.setOnTouchListener(mTouchListener);
        mEndInput.setOnTouchListener(mTouchListener);
        mCheclistInput.setOnTouchListener(mTouchListener);
    }

    private void savePlace() {
        String nameString = mPlaceNameInput.getText().toString().trim();
        String priceString = mlocationInput.getText().toString().trim();
        String qtyString = mStartInput.getText().toString().trim();
        String supplierNameString = mEndInput.getText().toString().trim();
        String supplierPhoneString = mCheclistInput.getText().toString().trim();

        if (mCurrentPlaceUri == null) {
            if (TextUtils.isEmpty(nameString)) {
                Toast.makeText(this, getString(R.string.required), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(priceString)) {
                Toast.makeText(this, getString(R.string.required), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(qtyString)) {
                Toast.makeText(this, getString(R.string.required), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(supplierNameString)) {
                Toast.makeText(this, getString(R.string.required), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(supplierPhoneString)) {
                Toast.makeText(this, getString(R.string.required), Toast.LENGTH_SHORT).show();
                return;
            }


            ContentValues values = new ContentValues();
            values.put(PlaceEntry.COLUMN_PLACE_NAME, nameString);
            values.put(PlaceEntry.COLUMN_PLACE_LOCATION, priceString);
            values.put(PlaceEntry.COLUMN_START_TIME, qtyString);
            values.put(PlaceEntry.COLUMN_END_TIME, supplierNameString);
            values.put(PlaceEntry.COLUMN_CHECKLIST, supplierPhoneString);

            Uri newUri = getContentResolver().insert(PlaceEntry.CONTENT_URI, values);

            if (newUri == null) {

                Toast.makeText(this, getString(R.string.failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.Good),
                        Toast.LENGTH_SHORT).show();
            }
        } else {

            if (TextUtils.isEmpty(nameString)) {
                Toast.makeText(this, getString(R.string.required), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(priceString)) {
                Toast.makeText(this, getString(R.string.required), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(qtyString)) {
                Toast.makeText(this, getString(R.string.required), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(supplierNameString)) {
                Toast.makeText(this, getString(R.string.required), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(supplierPhoneString)) {
                Toast.makeText(this, getString(R.string.required), Toast.LENGTH_SHORT).show();
                return;
            }
            ContentValues values = new ContentValues();
            values.put(PlaceEntry.COLUMN_PLACE_NAME, nameString);
            values.put(PlaceEntry.COLUMN_PLACE_LOCATION, priceString);
            values.put(PlaceEntry.COLUMN_START_TIME, qtyString);
            values.put(PlaceEntry.COLUMN_END_TIME, supplierNameString);
            values.put(PlaceEntry.COLUMN_CHECKLIST, supplierPhoneString);


            int rowsAffected = getContentResolver().update(mCurrentPlaceUri, values, null, null);
            if (rowsAffected == 0) {
                Toast.makeText(this, getString(R.string.failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.Good),
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public View.OnClickListener btnChoosePhotoPressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            final int ACTIVITY_SELECT_IMAGE = 1234;
            startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new pet, hide the "Delete" menu item.
        if (mCurrentPlaceUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                savePlace();
                return true;
            case android.R.id.home:
                if (!mPlaceHasChanged) {
                    NavUtils.navigateUpFromSameTask(AddPlace.this);
                    return true;
                }
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NavUtils.navigateUpFromSameTask(AddPlace.this);
                            }
                        };
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!mPlaceHasChanged) {
            super.onBackPressed();
            return;
        }
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        String[] projection = {
                PlaceEntry._ID,
                PlaceEntry.COLUMN_PLACE_NAME,
                PlaceEntry.COLUMN_PLACE_LOCATION,
                PlaceEntry.COLUMN_START_TIME,
                PlaceEntry.COLUMN_END_TIME,
                PlaceEntry.COLUMN_CHECKLIST,};

        return new CursorLoader(this,
                mCurrentPlaceUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if (cursor.moveToFirst()) {
            // Find the columns of pet attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndex(PlaceEntry.COLUMN_PLACE_NAME);
            int priceColumnIndex = cursor.getColumnIndex(PlaceEntry.COLUMN_PLACE_LOCATION);
            int qtyColumnIndex = cursor.getColumnIndex(PlaceEntry.COLUMN_START_TIME);
            int supplierNameColumnIndex = cursor.getColumnIndex(PlaceEntry.COLUMN_END_TIME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(PlaceEntry.COLUMN_CHECKLIST);

            // Extract out the value from the Cursor for the given column index
            String name = cursor.getString(nameColumnIndex);
            String price = cursor.getString(priceColumnIndex);
            String qty = cursor.getString(qtyColumnIndex);
            String sName = cursor.getString(supplierNameColumnIndex);
            String sPhone = cursor.getString(supplierPhoneColumnIndex);

            // Update the views on the screen with the values from the database
            mPlaceNameInput.setText(name);
            mlocationInput.setText(price);
            mStartInput.setText(qty);
            mEndInput.setText(sName);
            mCheclistInput.setText(sPhone);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mPlaceNameInput.setText("");
        mlocationInput.setText("");
        mStartInput.setText("");
        mEndInput.setText("");
        mCheclistInput.setText("");
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.Discard_changes );
        builder.setPositiveButton(R.string.failed, discardButtonClickListener);
        builder.setNegativeButton(R.string.stay, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
