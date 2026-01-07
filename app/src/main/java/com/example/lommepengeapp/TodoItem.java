package com.example.lommepengeapp;

import java.io.Serializable;

public class TodoItem implements Serializable {

    String name;
    boolean isDone;


    public TodoItem() {
    }

    public TodoItem(String name, boolean isDone) {
        this.name = name;
        this.isDone = isDone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }


}
