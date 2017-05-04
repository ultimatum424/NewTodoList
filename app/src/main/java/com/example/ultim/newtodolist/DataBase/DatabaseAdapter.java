package com.example.ultim.newtodolist.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ultim on 04.04.2017.
 */

public class DatabaseAdapter {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context) {
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String orderBy =  DatabaseHelper.COLUMN_IS_DONE + ", " + DatabaseHelper.COLUMN_PRIORITY + " DESC, "
                + DatabaseHelper.COLUMN_DATE + " DESC";

        String[] columns = new String[] {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_TITLE, DatabaseHelper.COLUMN_TEXT,
                DatabaseHelper.COLUMN_DATE, DatabaseHelper.COLUMN_PRIORITY, DatabaseHelper.COLUMN_IS_DONE};

        return database.query(DatabaseHelper.TABLE, columns, null, null, null, null, orderBy);
    }

    public List<TodoTask> getTodoTasks(){
        ArrayList<TodoTask> todoTasks = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE));
                String text = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEXT));
                String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
                int priority = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRIORITY));
                int isDone = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_IS_DONE));
                todoTasks.add(new TodoTask(id,title, text, date, PriorityEnum.fromInt(priority), DoneEnum.fromInt(isDone)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return todoTasks;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE);
    }

    public TodoTask getTodoTask(long id){
        TodoTask todoTask = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()){
            String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE));
            String text = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEXT));
            String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
            int priority = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRIORITY));
            int isDone = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_IS_DONE));
            todoTask = new TodoTask(id,title, text, date, PriorityEnum.fromInt(priority), DoneEnum.fromInt(isDone));
        }
        cursor.close();
        return  todoTask;
    }

    public long insert(TodoTask todoTask){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_TITLE, todoTask.getTitle());
        cv.put(DatabaseHelper.COLUMN_TEXT, todoTask.getText());
        cv.put(DatabaseHelper.COLUMN_DATE, todoTask.getDate());
        cv.put(DatabaseHelper.COLUMN_PRIORITY, todoTask.getPriorityValue());
        cv.put(DatabaseHelper.COLUMN_IS_DONE, todoTask.isDone().getValue());

        return  database.insert(DatabaseHelper.TABLE, null, cv);
    }

    public long delete(long todoTaskId){
        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(todoTaskId)};
        return database.delete(DatabaseHelper.TABLE, whereClause, whereArgs);
    }

    public long update(TodoTask todoTask){
        String whereClause = DatabaseHelper.COLUMN_ID + "=" + String.valueOf(todoTask.getId());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_TITLE, todoTask.getTitle());
        cv.put(DatabaseHelper.COLUMN_TEXT, todoTask.getText());
        cv.put(DatabaseHelper.COLUMN_DATE, todoTask.getDate());
        cv.put(DatabaseHelper.COLUMN_PRIORITY, todoTask.getPriority().getValue());
        cv.put(DatabaseHelper.COLUMN_IS_DONE, todoTask.isDone().getValue());

        return database.update(DatabaseHelper.TABLE, cv, whereClause, null);
    }
}
