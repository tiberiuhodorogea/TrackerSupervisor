package com.example.tiber.trackersupervisor.SharedClasses.Objects;

import android.view.Gravity;
import android.widget.RelativeLayout;

/**
 * Created by tiber on 11/6/2016.
 */

public class SentSmsData extends Sendable {

    String number;
    String name;
    String message;
    int id;

    public SentSmsData(int date, String clientName, int clientId) {
        super(date, clientName, clientId);
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
