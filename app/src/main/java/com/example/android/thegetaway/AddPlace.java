package com.example.android.thegetaway;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class AddPlace extends AppCompatActivity {


    private EditText mplaceInput;
    private EditText mlocationInput;
    private EditText mstartInput;
    private EditText mendInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_place);


        mplaceInput = findViewById(R.id.place_name_input);
        mlocationInput = findViewById(R.id.place_location_input);
        mstartInput = findViewById(R.id.start_time_input);
        mendInput = findViewById(R.id.end_time_input);

    }


    private void savePlace(){

        String placeNameString = mplaceInput.getText().toString().trim();


    }
}
