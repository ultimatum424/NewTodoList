package com.example.ultim.newtodolist.DataBase;

/**
 * Created by Ultim on 04.05.2017.
 */

public enum DoneEnum {
    NO(0),
    YES(1);
    private int value;

    DoneEnum (int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public static DoneEnum fromInt(int i){
        for (DoneEnum val : DoneEnum .values()) {
            if (val.getValue() == i) { return val; }
        }
        return null;
    }
}
