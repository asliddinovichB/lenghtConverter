package com.hfad.converter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity{

    private EditText inputText;
    private EditText outputText;
    private double input;
    private Unit fromFactor;
    private Unit toFactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner inputSpinner = findViewById(R.id.inputFactor);
        Spinner outputSpinner = findViewById(R.id.outputFactor);

        inputText = findViewById(R.id.inputNum);
        outputText = findViewById(R.id.outputNum);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.units, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        inputSpinner.setAdapter(adapter);
        outputSpinner.setAdapter(adapter);

        inputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fromFactor = Unit.fromString((String) parent.getSelectedItem());
                refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                fromFactor = Unit.fromString((String) parent.getSelectedItem());
            }
        });

        outputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toFactor = Unit.fromString((String) parent.getSelectedItem());
                refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                toFactor = Unit.fromString((String) parent.getSelectedItem());
            }
        });

        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!inputText.getText().toString().isEmpty()) {
                    Converter converter = new Converter(fromFactor, toFactor);
                    double result = converter.convert(input);
                    outputText.setText(String.valueOf(result));
                }
            }
        });
    }

    private void refresh() {
        inputText.setText("");
        outputText.setText("");
        input = 0;
    }

    public void buttonClick(View view) {
        String text = inputText.getText().toString();
        switch (view.getId()) {
            case R.id.clearAllbtn:{
                text = "";
                input = 0;
                break;
            }
            case R.id.dotbtn: {
                if (text.isEmpty()) {
                    text = "0.";
                } else if (!text.contains(".")) {
                    text = text + ".";
                }
                break;
            }
            case R.id.clearbtn: {
                if (!text.isEmpty())
                    text = text.substring(0, text.length() - 1);
                break;
            }
            default: {
                if (!text.isEmpty())
                    text += ((Button) view).getText().toString();
                else
                    text = ((Button) view).getText().toString();
                input = Double.valueOf(text);
            }
        }

        if (text.isEmpty())
            outputText.setText("");

        inputText.setText(text);
    }
}
