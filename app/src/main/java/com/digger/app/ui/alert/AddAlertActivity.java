package com.digger.app.ui.alert;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.digger.app.R;

public class AddAlertActivity extends Activity {

    private EditorViewModel editorViewModel;

    private Spinner stockSpinner;

    private Spinner lessOrGreater;

    private EditText valueText;

    private Button createButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        stockSpinner = findViewById(R.id.stocks_spinner);
        lessOrGreater = findViewById(R.id.spinner_more_less);
        valueText = findViewById(R.id.value);
        editorViewModel = new EditorViewModel(this);
        setUpSpinners();
        setButtons();

    }

    private boolean isNumeric(String input){

        try {
            Double num = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
    private  void setButtons(){


        Button button = findViewById(R.id.create);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = valueText.getText().toString().trim();
                if(isNumeric(value)){
                    editorViewModel.setCreatedAlert();
                    editorViewModel.registerAlert();
                }

            }
        });

    }


    private void setUpSpinners() {

        ArrayAdapter stockSpinnerAdapter = ArrayAdapter.createFromResource
                (this, R.array.array_stocks_options, android.R.layout.simple_spinner_item);

        stockSpinner.setAdapter(stockSpinnerAdapter);

        stockSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String stock = (String) parent.getItemAtPosition(position);


                switch (stock) {
                    case "Bitcoin":
                        editorViewModel.setStock("BTC-USD");
                        break;

                    case "Gold":
                        editorViewModel.setStock("GC=F");
                        break;
                    case "Oil":
                        editorViewModel.setStock("CL=F");
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                editorViewModel.setStock("bitcoin");

            }
        });

        ArrayAdapter lessOrGreaterAdapter = ArrayAdapter.createFromResource
                (this, R.array.array_more_less, android.R.layout.simple_spinner_item);

        lessOrGreater.setAdapter(lessOrGreaterAdapter);

        lessOrGreater.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String less = (String) parent.getItemAtPosition(position);

                switch (less){
                    case "less":
                        editorViewModel.setMore(false);
                        break;
                    case "more":
                        editorViewModel.setMore(true);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


}
