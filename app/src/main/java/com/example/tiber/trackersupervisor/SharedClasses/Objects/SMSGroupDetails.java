package com.example.tiber.trackersupervisor.SharedClasses.Objects;

/**
 * Created by tiber on 11/5/2016.
 */

public class SMSGroupDetails {
    private int clientId;
    private Contact contact;
    private int sentCount;
    private int receivedCount;
    private int latesActivityTime;

    public SMSGroupDetails(int clientId, Contact contact, int sentCount, int receivedCount) {
        this.clientId = clientId;
        this.contact = contact;
        this.sentCount = sentCount;
        this.receivedCount = receivedCount;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public int getSentCount() {
        return sentCount;
    }

    public void setSentCount(int sentCount) {
        this.sentCount = sentCount;
    }

    public int getReceivedCount() {
        return receivedCount;
    }

    public void setReceivedCount(int receivedCount) {
        this.receivedCount = receivedCount;
    }

    public String toString(){
        String ret = "";
        String virgula = getContact().getNames().size() > 1 ? ", " : "";
        for(String name: contact.getNames())
            ret += name + virgula;

        if(!virgula.equals(""))
            ret = ret.substring(0,ret.length()-2);

        ret += "\n" + "number: "+contact.getNumber();
        ret += "\n";
        ret += "received: " +receivedCount +", sent: " + sentCount;
        return  ret;
    }
}
