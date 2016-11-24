package com.example.tiber.trackersupervisor.Clase.AsyncRequests;

import android.content.Context;
import android.widget.ListView;
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
    private ListView listView;
    public GetSmsConversationAsync(Context context, Contact contact, ConversationListViewAdapter adapter,ListView listView) {
        super(context);
        this.contact = contact;
        this.adapter = adapter;
        this.listView = listView;
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

        listView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                listView.setSelection(listView.getCount() - 1);
            }
        });

    }
}