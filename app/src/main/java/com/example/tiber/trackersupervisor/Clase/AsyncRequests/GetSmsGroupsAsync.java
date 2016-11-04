package com.example.tiber.trackersupervisor.Clase.AsyncRequests;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.tiber.trackersupervisor.Clase.ServerConnection;
import com.example.tiber.trackersupervisor.R;
import com.example.tiber.trackersupervisor.SharedClasses.Communication.Exceptions.KeyNotMappedException;
import com.example.tiber.trackersupervisor.SharedClasses.Communication.RequestedAction;

import java.util.ArrayList;

/**
 * Created by tiber on 11/4/2016.
 */

public class GetSmsGroupsAsync extends MyAsyncTask {

    ArrayAdapter<String> adapter;
    public GetSmsGroupsAsync(Context context,ArrayAdapter<String> adapter) {
        super(context);
        this.adapter = adapter;
    }

    ArrayList<String> SmsGroups = null;
    @Override
    protected Void doInBackground(Void... params) {

        ServerConnection<String,ArrayList<String>> connection =
                new ServerConnection<String,ArrayList<String>>(context);
        try {
            SmsGroups = connection.execute(RequestedAction.GET_CLIENTS_FOR_SUPERVISOR,
                    context.getResources().getString(R.string.SUPERVISOR_NAME));
        } catch (KeyNotMappedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
