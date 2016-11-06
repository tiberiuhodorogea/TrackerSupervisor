package com.example.tiber.trackersupervisor.SharedClasses.Objects;

import java.util.ArrayList;

/**
 * Created by tiber on 11/5/2016.
 */

public class Contact {
    private ArrayList<String> names;
    private String number;
    private int clientId;

    public Contact(ArrayList<String> names, String number) {
        this.names = names;
        this.number = number;
    }

    public Contact(){
        names = new ArrayList<String>();
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
