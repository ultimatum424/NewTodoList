package com.example.ultim.newtodolist.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ultim on 04.04.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todo_st.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "todo"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_IS_DONE = "isDone";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE todo (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_TEXT + " TEXT, "
                + COLUMN_DATE + " INTEGER, "
                + COLUMN_PRIORITY + " TEXT, "
                + COLUMN_IS_DONE + " INTEGER);");
        // добавление начальных данных
        db.execSQL("INSERT INTO "+ TABLE
                +" (" + COLUMN_TITLE
               + ", " + COLUMN_TEXT
                + ", " + COLUMN_DATE
                + ", " + COLUMN_PRIORITY
                + ", " + COLUMN_IS_DONE
                +  ")  VALUES ('Том Смит', 'Текст', 1865, 'good', 0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
