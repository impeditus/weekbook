package com.example.weekbook.data;

import android.provider.BaseColumns;

public final class WeekbookContract {
    private WeekbookContract(){}
    //таблиця з записами
    public static final class RecordsEntry implements BaseColumns {
        public static final String TABLE_NAME = "records"; // назва таблиці в бд
        // назва стовбців
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_SHORT_TEXT = "shortText";
        public static final String COLUMN_ALL_TEXT = "allText";
        public static final String COLUMN_DATE = "date";
    }
    //таблиця з тегами
    public static final class TagsEntry implements BaseColumns {
        static final String TABLE_NAME = "tags"; // назва таблиці в бд
        // назва стовбців
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_TAG_NAME = "tagName";
    }
    //зведена таблиця(допоміжна) для створення зв'язку багато до багатьох
    public static final class TagsOnRecordsEntry implements BaseColumns {
        static final String TABLE_NAME = "TagsOnRecords"; // назва таблиці в бд
        // назва стовбців
        public static final String COLUMN_TAG_ID = "TagId";
        public static final String COLUMN_RECORD_ID = "RecordId";
    }
}
