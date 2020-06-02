package com.example.weekbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class DayActivity extends AppCompatActivity {
   private TextView weekDayTextView;
   private EditText editText;
   private String FILENAME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        weekDayTextView = (TextView) findViewById(R.id.weekDayTextView);
        weekDayTextView.setText(getIntent().getStringExtra("weekDay"));
        FILENAME= getIntent().getStringExtra("weekDay")+".txt";
        FILENAME.replaceAll(" ", "");
        editText = (EditText)findViewById(R.id.editText);
        openFile(FILENAME);
        editText.addTextChangedListener ( new TextWatcher() {

            public void afterTextChanged (Editable s ){

                saveFile(FILENAME);
            }
            public void beforeTextChanged ( CharSequence s, int start, int count, int after ) {
            }

            public void onTextChanged ( CharSequence s, int start, int before, int count ) {
            }
        });
    }

    // Метод для відкиття файлу
    private void openFile(String fileName) {
        try {
            InputStream inputStream = openFileInput(fileName);

            if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                String line;
                StringBuilder builder = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }

                inputStream.close();
                editText.setText(builder.toString());
            }
        } catch (Throwable t) {
//            Toast.makeText(getApplicationContext(),
//                    "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    // Метод для збереження файлу
    private void saveFile(String fileName) {
        try {
            OutputStream outputStream = openFileOutput(fileName, 0);
            OutputStreamWriter osw = new OutputStreamWriter(outputStream);
            osw.write(editText.getText().toString());
            osw.close();
        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(),
                    "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
