package com.example.weekbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateTimePatternGenerator;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import  com.example.weekbook.data.WeekbookContract.RecordsEntry;
import  com.example.weekbook.data.DBHelper;

public class MainActivity extends AppCompatActivity {

    private TextView textViewMonday;
    private TextView textViewTuesday;
    private TextView textViewWednesday;
    private TextView textViewThursday;
    private TextView textViewFriday;
    private TextView textViewSaturday;
    private TextView textViewSunday;

    private SQLiteDatabase db;
    private Cursor userCursor;
    private SimpleCursorAdapter userAdapter;
    private TextView mondayEditText;
    private TextView tuesdayEditText;
    private TextView wednesdayEditText;
    private TextView thursdayEditText;
    private TextView fridayEditText;
    private TextView saturdayEditText;
    private TextView sundayEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewMonday = (TextView) findViewById(R.id.textViewMonday);
        textViewTuesday = (TextView) findViewById(R.id.textViewTuesday);
        textViewWednesday = (TextView) findViewById(R.id.textViewWednesday);
        textViewThursday = (TextView) findViewById(R.id.textViewThursday);
        textViewFriday = (TextView) findViewById(R.id.textViewFriday);
        textViewSaturday = (TextView) findViewById(R.id.textViewSaturday);
        textViewSunday = (TextView) findViewById(R.id.textViewSunday);
        mondayEditText=(TextView) findViewById(R.id.mondayEditText);
        tuesdayEditText=(TextView) findViewById(R.id.tuesdayEditText);
        wednesdayEditText=(TextView) findViewById(R.id.wednesdayEditText);
        thursdayEditText=(TextView) findViewById(R.id.thursdayEditText);
        fridayEditText=(TextView) findViewById(R.id.fridayEditText);
        saturdayEditText=(TextView) findViewById(R.id.saturdayEditText);
        sundayEditText=(TextView) findViewById(R.id.sundayEditText);
        getToday();
        getTextForAll();

    }

    @Override
    public void onResume() {
        super.onResume();
        getTextForAll();
    }

    public void getToday()
    {
        SimpleDateFormat sdf = new SimpleDateFormat(" dd.MM.yy");
       Calendar calendar =Calendar.getInstance();
       // Calendar calendar = new GregorianCalendar(2020,4,3,13,24,56);
        calendar.add(Calendar.DAY_OF_MONTH, -(calendar.get(Calendar.DAY_OF_WEEK)==1? 6:calendar.get(Calendar.DAY_OF_WEEK)-2));

        textViewMonday.setText(textViewMonday.getText()+"  "+sdf.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        textViewTuesday.setText(textViewTuesday.getText()+"  "+sdf.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        textViewWednesday.setText(textViewWednesday.getText()+"  "+sdf.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        textViewThursday.setText(textViewThursday.getText()+"  "+sdf.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        textViewFriday.setText(textViewFriday.getText()+"  "+sdf.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        textViewSaturday.setText(textViewSaturday.getText()+"  "+sdf.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        textViewSunday.setText(textViewSunday.getText()+"  "+sdf.format(calendar.getTime()));

    }
    public void getTextForAll()
    {
        openFile(textViewMonday.getText()+".txt",mondayEditText);
        openFile(textViewTuesday.getText()+".txt",tuesdayEditText);
        openFile(textViewWednesday.getText()+".txt",wednesdayEditText);
        openFile(textViewThursday.getText()+".txt",thursdayEditText);
        openFile(textViewFriday.getText()+".txt",fridayEditText);
        openFile(textViewSaturday.getText()+".txt",saturdayEditText);
        openFile(textViewSunday.getText()+".txt",sundayEditText);

    }
    private void openFile(String fileName, TextView tv) {
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
                tv.setText(builder.toString());
            }
        } catch (Throwable t) {
//            Toast.makeText(getApplicationContext(),
//                    "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void goNewView(View v){
        Intent intent = new Intent(this, DayActivity.class);

        switch (v.getId()) {
            case R.id.mondayEditText:

                // Вказуємо між якими Activity буде зв'язок

                intent.putExtra("weekDay", textViewMonday.getText());
                // показуємо нове Activity
                startActivity(intent);
                break;
            case R.id.tuesdayEditText:

                intent.putExtra("weekDay", textViewTuesday.getText());
                // показуємо нове Activity
                startActivity(intent);
                break;
            case R.id.wednesdayEditText:

                intent.putExtra("weekDay", textViewWednesday.getText());
                // показуємо нове Activity
                startActivity(intent);
                break;
            case R.id.thursdayEditText:

                intent.putExtra("weekDay", textViewThursday.getText());
                // показуємо нове Activity
                startActivity(intent);
                break;
            case R.id.fridayEditText:

                intent.putExtra("weekDay", textViewFriday.getText());
                // показуємо нове Activity
                startActivity(intent);

                break;
            case R.id.saturdayEditText:

                intent.putExtra("weekDay", textViewSaturday.getText());
                // показуємо нове Activity
                startActivity(intent);
                break;
            case R.id.sundayEditText:

                intent.putExtra("weekDay", textViewSunday.getText());
                // показуємо нове Activity
                startActivity(intent);
                break;
            default:
                break;

        }
    }
}



