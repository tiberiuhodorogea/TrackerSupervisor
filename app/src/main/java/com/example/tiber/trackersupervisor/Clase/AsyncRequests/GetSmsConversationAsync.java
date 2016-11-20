package com.example.tiber.trackersupervisor.Clase.AsyncRequests;

import android.content.Context;
import android.widget.Toast;

import com.example.tiber.trackersupervisor.Clase.AsyncRequests.MyAsyncTask;
import com.example.tiber.trackersupervisor.Clase.ConversationListViewAdapter;
import com.example.tiber.trackersupervisor.Clase.ServerConnection;
import com.example.tiber.trackersupervisor.SharedClasses.Communication.Exceptions.KeyNotMappedException;
import com.example.tiber.trackersupervisor.SharedClasses.Communication.RequestedAction;
import com.example.tiber.trackersupervisor.SharedClasses.Objects.Contact;
import com.example.tiber.trackersupervisor.SharedClasses.Objects.SmsData;

import java.util.ArrayList;

/**
 * Created by tiber on 11/20/2016.
 */

public class GetSmsConversationAsync extends MyAsyncTask {

    private Contact contact;
    ConversationListViewAdapter adapter;
    public GetSmsConversationAsync(Context context, Contact contact, ConversationListViewAdapter adapter) {
        super(context);
        this.contact = contact;
        this.adapter = adapter;
    }

    ArrayList<SmsData> smsList;

    @Override
    protected Void doInBackground(Void... params) {
        smsList = null;
        ServerConnection<Contact,ArrayList<SmsData>> connection
                = new ServerConnection<Contact,ArrayList<SmsData>>(context);

        try {
            smsList = connection.execute(RequestedAction.GET_CONVERSATION,contact);
        } catch (KeyNotMappedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(smsList == null){
            Toast.makeText(context,"Some problems....",Toast.LENGTH_SHORT).show();
            return;
        }
        adapter.clear();
        adapter.addAll(smsList);
        adapter.notifyDataSetChanged();
    }
}