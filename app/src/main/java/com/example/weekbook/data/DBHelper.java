package com.example.weekbook.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import  com.example.weekbook.data.WeekbookContract.RecordsEntry;
import  com.example.weekbook.data.WeekbookContract.TagsEntry;
import  com.example.weekbook.data.WeekbookContract.TagsOnRecordsEntry;

public class DBHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = DBHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "weekbook.db";
    private static final int DATABASE_VERSION = 1;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     *Викликається при створенні БД
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Запит на створення
        String SQL_CREATE_RECORDS_TABLE = "CREATE TABLE " +RecordsEntry.TABLE_NAME+ " ("
                + RecordsEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RecordsEntry.COLUMN_SHORT_TEXT + " TEXT, "
                + RecordsEntry.COLUMN_ALL_TEXT + " TEXT, "
                + RecordsEntry.COLUMN_DATE + " TEXT NOT NULL );";
        //Запускаємо створення таблиці з записами
        db.execSQL(SQL_CREATE_RECORDS_TABLE);


        // Запит на створення
        String SQL_CREATE_TAGS_TABLE = "CREATE TABLE " +TagsEntry.TABLE_NAME+ " ("
                + TagsEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TagsEntry.COLUMN_TAG_NAME + " TEXT NOT NULL );";
        //Запускаємо створення таблиці з тегами
        db.execSQL(SQL_CREATE_TAGS_TABLE);


        // Запит на створення
        String SQL_CREATE_TAGS_ON_RECORDS_TABLE = "CREATE TABLE " +TagsOnRecordsEntry.TABLE_NAME+ " ("
                + TagsOnRecordsEntry.COLUMN_RECORD_ID + " INTEGER NOT NULL, "
                + TagsOnRecordsEntry.COLUMN_TAG_ID + " INTEGER NOT NULL, "
                +"FOREIGN KEY ("+TagsOnRecordsEntry.COLUMN_TAG_ID+") REFERENCES "+TagsEntry.TABLE_NAME
                +"("+TagsEntry.COLUMN_ID+"), "
                +"FOREIGN KEY ("+TagsOnRecordsEntry.COLUMN_RECORD_ID+") REFERENCES "+RecordsEntry.TABLE_NAME
                +"("+RecordsEntry.COLUMN_ID+" ));";
        //Запускаємо створення зведеної таблиці
        db.execSQL(SQL_CREATE_TAGS_ON_RECORDS_TABLE);
    }

    /**
     *Викликається при оновленні БД
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Запис в журнал
        Log.w("SQLite", "Upgrading from version " + oldVersion + " to version " + newVersion);

        // Видаляємо старі таблиці
        db.execSQL("DROP TABLE IF  EXISTS " + RecordsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF  EXISTS " + TagsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF  EXISTS " + TagsOnRecordsEntry.TABLE_NAME);
        // Створюємо нові
        onCreate(db);
    }
}
