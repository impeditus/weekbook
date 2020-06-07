package com.example.weekbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weekbook.data.DBHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import  com.example.weekbook.data.WeekbookContract.RecordsEntry;
import  com.example.weekbook.data.WeekbookContract.TagsEntry;
import  com.example.weekbook.data.WeekbookContract.TagsOnRecordsEntry;

public class DayActivity extends AppCompatActivity {

   private TextView weekDayTextView;
   private EditText editText;
   private String FILENAME;
   private DBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        myDBHelper= new DBHelper(this);
        weekDayTextView = (TextView) findViewById(R.id.weekDayTextView);
        weekDayTextView.setText(getIntent().getStringExtra("weekDay"));
        FILENAME= getIntent().getStringExtra("weekDay")+".txt";
        FILENAME.replaceAll(" ", "");
        editText = (EditText)findViewById(R.id.editText);
        openFile(FILENAME);
        insertGuest();
        displayDatabaseInfo();
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
    private void displayDatabaseInfo() {
        // Создадим и откроем для чтения базу данных
        SQLiteDatabase db = myDBHelper.getReadableDatabase();

        // Зададим условие для выборки - список столбцов
        String[] projection = {
                RecordsEntry.COLUMN_ALL_TEXT};
        String selection = RecordsEntry.COLUMN_DATE + "=?";
        String[] selectionArgs = {getIntent().getStringExtra("weekDay")};
        // Делаем запрос
        Cursor cursor = db.query(
                RecordsEntry.TABLE_NAME,   // таблица
                projection,            // столбцы
                selection,                  // столбцы для условия WHERE
                selectionArgs,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки

        TextView displayTextView = editText;

        try {
           /* displayTextView.setText("Таблица содержит " + cursor.getCount() + " гостей.\n\n");
            displayTextView.append(GuestEntry._ID + " - " +
                    GuestEntry.COLUMN_NAME + " - " +
                    GuestEntry.COLUMN_CITY + " - " +
                    GuestEntry.COLUMN_GENDER + " - " +
                    GuestEntry.COLUMN_AGE + "\n");
*/
            // Узнаем индекс каждого столбца
            int All_Text_ColumnIndex = cursor.getColumnIndex(RecordsEntry.COLUMN_ALL_TEXT);
           /* int nameColumnIndex = cursor.getColumnIndex(GuestEntry.COLUMN_NAME);
            int cityColumnIndex = cursor.getColumnIndex(GuestEntry.COLUMN_CITY);
            int genderColumnIndex = cursor.getColumnIndex(GuestEntry.COLUMN_GENDER);
            int ageColumnIndex = cursor.getColumnIndex(GuestEntry.COLUMN_AGE);*/

            // Проходим через все ряды
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа
               // int currentID = cursor.getInt(idColumnIndex);
                String currentAllText = cursor.getString(All_Text_ColumnIndex);
                /*String currentCity = cursor.getString(cityColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                int currentAge = cursor.getInt(ageColumnIndex);*/
                // Выводим значения каждого столбца
                displayTextView.append(currentAllText+ "\n");
            }
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
    }
    private void insertGuest() {

        // Gets the database in write mode
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        // Создаем объект ContentValues, где имена столбцов ключи,
        // а информация о госте является значениями ключей
        ContentValues values = new ContentValues();
        values.put(RecordsEntry.COLUMN_ALL_TEXT, "Мурзик");
        values.put(RecordsEntry.COLUMN_SHORT_TEXT, "Мурманск");
        values.put(RecordsEntry.COLUMN_DATE,getIntent().getStringExtra("weekDay") );

        long newRowId = db.insert(RecordsEntry.TABLE_NAME, null, values);
    }
}
