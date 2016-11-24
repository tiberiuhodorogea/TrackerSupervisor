package com.example.tiber.trackersupervisor.Clase.AsyncRequests;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.tiber.trackersupervisor.Clase.ServerConnection;
import com.example.tiber.trackersupervisor.R;
import com.example.tiber.trackersupervisor.SharedClasses.Communication.Exceptions.KeyNotMappedException;
import com.example.tiber.trackersupervisor.SharedClasses.Communication.RequestedAction;
import com.example.tiber.trackersupervisor.SharedClasses.Objects.SMSGroupDetails;

import java.util.ArrayList;

/**
 * Created by tiber on 11/4/2016.
 */

public class GetSmsGroupsAsync extends MyAsyncTask {

    private int selectedClientId;
    ArrayAdapter<String> adapter;
    ArrayList<String> smsGroupsString;
    ArrayList<SMSGroupDetails> smsGroupsLocal;
    ArrayList<SMSGroupDetails> smsGroups;

    public GetSmsGroupsAsync(Context context,ArrayAdapter<String> adapter,
                             int selectedClientId, ArrayList<String> smsGroupsString,ArrayList<SMSGroupDetails> smsGroups ) {
        super(context);
        this.adapter = adapter;
        this.smsGroupsString = smsGroupsString;
        this.selectedClientId = selectedClientId;
        this.smsGroups = smsGroups;
    }


    @Override
    protected Void doInBackground(Void... parintams) {

        ServerConnection<Integer,ArrayList<SMSGroupDetails>> connection =
                new ServerConnection<Integer,ArrayList<SMSGroupDetails>>(context);
        try {
            smsGroupsLocal = connection.execute(RequestedAction.GET_SMS_GROUPS_OF_CLIENT,
                    selectedClientId);
        } catch (KeyNotMappedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if( smsGroupsLocal == null){
            Toast.makeText(context,"Problem getting sms groups...",Toast.LENGTH_LONG).show();
            return;
        }

        smsGroupsString.clear();
        adapter.clear();

        for(SMSGroupDetails smsGroup : smsGroupsLocal){
            smsGroupsString.add(smsGroup.toString());
        }
        smsGroups.clear();




        smsGroups.addAll(smsGroupsLocal);

        //adapter.addAll(smsGroupsString);
        adapter.notifyDataSetChanged();
    }


}
