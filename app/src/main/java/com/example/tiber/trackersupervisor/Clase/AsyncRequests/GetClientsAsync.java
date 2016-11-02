package com.example.tiber.trackersupervisor.Clase.AsyncRequests;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.tiber.trackersupervisor.R;
import com.example.tiber.trackersupervisor.SharedClasses.Communication.Exceptions.KeyNotMappedException;
import com.example.tiber.trackersupervisor.SharedClasses.Communication.RequestedAction;

import java.util.ArrayList;

import com.example.tiber.trackersupervisor.Clase.ServerConnection;

/**
 * Created by tiber on 11/2/2016.
 */

public class GetClientsAsync extends MyAsyncTask {

    ArrayAdapter<String> adapter;
    public GetClientsAsync(Context context,ArrayAdapter<String> adapter) {
        super(context);
        this.adapter = adapter;
    }

    ArrayList<String> clients = null;

    @Override
    protected Void doInBackground(Void... params) {
        ServerConnection<String,ArrayList<String>> connection =
                new ServerConnection<String,ArrayList<String>>(context);
        try {
             clients = connection.execute(RequestedAction.GET_CLIENTS_FOR_SUPERVISOR,
                    context.getResources().getString(R.string.SUPERVISOR_NAME));
        } catch (KeyNotMappedException e) {
            e.printStackTrace();
        }
        return null;

    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(clients == null){
            Toast.makeText(context,"Problem getting the clients list", Toast.LENGTH_SHORT).show();
        }
        else {
            adapter.clear();
            adapter.addAll(clients);
            adapter.notifyDataSetChanged();
        }

    }
}
