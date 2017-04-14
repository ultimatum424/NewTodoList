package com.example.ultim.newtodolist.DataBase;

/**
 * Created by Ultim on 04.04.2017.
 */

public class TodoTask {
    private long id;
    private String title;
    private String text;
    private int date;
    private String priority;
    private int isDone;

    public TodoTask(long id, String title, String text, int date, String priority, int isDone) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.priority = priority;
        this.isDone = isDone;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getDate() {
        return date;
    }

    public String getPriority() {
        return priority;
    }

    public int isDone() {
        return isDone;
    }

}
