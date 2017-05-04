package com.example.ultim.newtodolist.DataBase;

/**
 * Created by Ultim on 04.05.2017.
 */

public enum PriorityEnum {
    NONE(0),
    LOW(1),
    MEDIUM(2),
    MAX(3);
    private int value;

    PriorityEnum(int Value){
        this.value = Value;
    }

    public int getValue() {
        return value;
    }

    public static PriorityEnum fromInt(int i) {
        for (PriorityEnum val : PriorityEnum .values()) {
            if (val.getValue() == i) { return val; }
        }
        return null;
    }
}

