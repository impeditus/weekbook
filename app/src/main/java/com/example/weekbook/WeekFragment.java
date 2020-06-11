package com.example.weekbook;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weekbook.data.DBHelper;
import com.example.weekbook.data.WeekbookContract;


public class WeekFragment extends Fragment {
    final static String MONDAY = "text_Monday";
    final static String TUESDAY = "text_Tuesday";
    final static String WEDNESDAY = "text_Wednesday";
    final static String THURSDAY = "text_Thursday";
    final static String FRIDAY = "text_Friday";
    final static String SATURDAY = "text_Saturday";
    final static String SUNDAY = "text_Sunday";

    private TextView mondayEditText;
    private TextView tuesdayEditText;
    private TextView wednesdayEditText;
    private TextView thursdayEditText;
    private TextView fridayEditText;
    private TextView saturdayEditText;
    private TextView sundayEditText;

    private DBHelper myDBHelper;
    private Long  recId;

    private String text_Monday ;
    private String text_Tuesday;
    private String text_Wednesday;
    private String text_Thursday;
    private String text_Friday;
    private String text_Saturday;
    private String text_Sunday;

    public WeekFragment(){}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.week_fragment, container, false);

        Bundle arguments = getArguments();
        if (arguments != null) {
             text_Monday = arguments.getString(MONDAY);
             text_Tuesday = arguments.getString(TUESDAY);
             text_Wednesday = arguments.getString(WEDNESDAY);
             text_Thursday = arguments.getString(THURSDAY);
             text_Friday = arguments.getString(FRIDAY);
             text_Saturday = arguments.getString(SATURDAY);
             text_Sunday = arguments.getString(SUNDAY);

            displayValues(view);

        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getTextForAll();
    }


    private void displayValues(View v) {

        mondayEditText=(TextView) v.findViewById(R.id.mondayEditText);
        tuesdayEditText=(TextView) v.findViewById(R.id.tuesdayEditText);
        wednesdayEditText=(TextView) v.findViewById(R.id.wednesdayEditText);
        thursdayEditText=(TextView) v.findViewById(R.id.thursdayEditText);
        fridayEditText=(TextView) v.findViewById(R.id.fridayEditText);
        saturdayEditText=(TextView) v.findViewById(R.id.saturdayEditText);
        sundayEditText=(TextView) v.findViewById(R.id.sundayEditText);


        myDBHelper= new DBHelper(v.getContext());
        getToday(v);
        getTextForAll();
        //створюємо лістенер, який слухає чи натискалось на елемент
        View.OnClickListener listener= new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goNewView(v);
            }
        };
        //назначаємо цей лістенер елементам
        mondayEditText.setOnClickListener(listener);
        tuesdayEditText.setOnClickListener(listener);
        wednesdayEditText.setOnClickListener(listener);
        thursdayEditText.setOnClickListener(listener);
        fridayEditText.setOnClickListener(listener);
        saturdayEditText.setOnClickListener(listener);
        sundayEditText.setOnClickListener(listener);
    }
    public void getToday(View v)
    {
        TextView textViewMonday = (TextView) v.findViewById(R.id.textViewMonday);
        TextView textViewTuesday = (TextView) v.findViewById(R.id.textViewTuesday);
        TextView textViewWednesday = (TextView) v.findViewById(R.id.textViewWednesday);
        TextView textViewThursday = (TextView) v.findViewById(R.id.textViewThursday);
        TextView textViewFriday = (TextView) v.findViewById(R.id.textViewFriday);
        TextView textViewSaturday = (TextView) v.findViewById(R.id.textViewSaturday);
        TextView textViewSunday = (TextView) v.findViewById(R.id.textViewSunday);
        //встановлюємо текст з датою
        textViewMonday.setText(text_Monday);
        textViewTuesday.setText(text_Tuesday);
        textViewWednesday.setText(text_Wednesday);
        textViewThursday.setText(text_Thursday);
        textViewFriday.setText(text_Friday);
        textViewSaturday.setText(text_Saturday);
        textViewSunday.setText(text_Sunday);
    }
    public void getTextForAll()
    {
        //відображаємо дані з БД
        displayDatabaseInfo(text_Monday,mondayEditText);
        displayDatabaseInfo(text_Tuesday,tuesdayEditText);
        displayDatabaseInfo(text_Wednesday,wednesdayEditText);
        displayDatabaseInfo(text_Thursday,thursdayEditText);
        displayDatabaseInfo(text_Friday,fridayEditText);
        displayDatabaseInfo(text_Saturday, saturdayEditText);
        displayDatabaseInfo(text_Sunday,sundayEditText);

    }
    public void goNewView(View v){
        Intent intent = new Intent(v.getContext(), DayActivity.class);
        switch (v.getId()) {
            case R.id.mondayEditText:
                // Вказуємо між якими Activity буде зв'язок
                intent.putExtra("weekDay", text_Monday);
                // показуємо нове Activity
                startActivity(intent);
                break;
            case R.id.tuesdayEditText:

                intent.putExtra("weekDay", text_Tuesday);
                // показуємо нове Activity
                startActivity(intent);
                break;
            case R.id.wednesdayEditText:

                intent.putExtra("weekDay",text_Wednesday);
                // показуємо нове Activity
                startActivity(intent);
                break;
            case R.id.thursdayEditText:

                intent.putExtra("weekDay", text_Thursday);
                // показуємо нове Activity
                startActivity(intent);
                break;
            case R.id.fridayEditText:

                intent.putExtra("weekDay",text_Friday);
                // показуємо нове Activity
                startActivity(intent);

                break;
            case R.id.saturdayEditText:

                intent.putExtra("weekDay", text_Saturday);
                // показуємо нове Activity
                startActivity(intent);
                break;
            case R.id.sundayEditText:

                intent.putExtra("weekDay", text_Sunday);
                // показуємо нове Activity
                startActivity(intent);
                break;
            default:
                break;

        }
    }

    private void displayDatabaseInfo(String date, TextView textView) {
        // Створюємо і відкриваємо для читання БД
        SQLiteDatabase db = myDBHelper.getReadableDatabase();

        // Задаємо умову для вибірки - список стовбчиків
        String[] projection = {
                WeekbookContract.RecordsEntry._ID,
                WeekbookContract.RecordsEntry.COLUMN_SHORT_TEXT,
                WeekbookContract.RecordsEntry.COLUMN_ALL_TEXT};
        String selection = WeekbookContract.RecordsEntry.COLUMN_DATE + "=?";
        String[] selectionArgs = {date};
        // Робимо запит
        Cursor cursor = db.query(
                WeekbookContract.RecordsEntry.TABLE_NAME,   // таблиця
                projection,            // стовбчики
                selection,                  // стовбчики для умови WHERE
                selectionArgs,                  // значення для умови WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортування


        TextView displayTextView = textView;
        if (cursor.getCount()==0) {
            insert( date);


        }
        try {

            // Дізнаємось індекс стовбчиків
            int idColumnIndex =cursor.getColumnIndex(WeekbookContract.RecordsEntry._ID);
            int Short_Text_ColumnIndex = cursor.getColumnIndex(WeekbookContract.RecordsEntry.COLUMN_SHORT_TEXT);


            // Проходимо через всі записи
            while (cursor.moveToNext()) {
                //Використовуємо індекс для отримання рядків чи чисел
                int currentID = cursor.getInt(idColumnIndex);
                //зберігамо індекс запиту
                recId = (long)currentID;
                String currentShortText = cursor.getString(Short_Text_ColumnIndex);

                // Виводимо значення кожного стовпчика
                displayTextView.setText(currentShortText);
            }
        } finally {
            // Закриваємо курсор
            cursor.close();

        }
    }
    private void insert(String date ) {

        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        // створюємо об'єкт ContentValues, де імена стовбчиків ключі,
        // а дата - значення ключів
        ContentValues values = new ContentValues();
        values.put(WeekbookContract.RecordsEntry.COLUMN_DATE, date );

        recId = db.insert(WeekbookContract.RecordsEntry.TABLE_NAME, null, values);
        Log.d("myLog", "inserted rows id = " + recId);
    }

}
