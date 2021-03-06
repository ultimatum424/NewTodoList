package com.example.ultim.newtodolist.DataBase;

/**
 * Created by Ultim on 04.04.2017.
 */

public class TodoTask {
    private long id;
    private String title;
    private String text;
    private String date;
    private PriorityEnum priority;
    private DoneEnum isDone;

    public TodoTask(long id, String title, String text, String date, PriorityEnum priority, DoneEnum isDone) {
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

    public String getDate() {
        return date;
    }

    public PriorityEnum getPriority() {
        return priority;
    }
    public int getPriorityValue(){
       return priority.ordinal();
    }

    public DoneEnum isDone() {
        return isDone;
    }

    public void setIsDone(DoneEnum isDone) {
        this.isDone = isDone;
    }
}
