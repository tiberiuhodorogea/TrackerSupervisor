package com.example.tiber.trackersupervisor.Clase.AsyncRequests;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.tiber.trackersupervisor.R;
import com.example.tiber.trackersupervisor.SharedClasses.Communication.Exceptions.KeyNotMappedException;
import com.example.tiber.trackersupervisor.SharedClasses.Communication.RequestedAction;

import java.util.ArrayList;
import java.util.jar.Pack200;

import com.example.tiber.trackersupervisor.Clase.ServerConnection;
import com.example.tiber.trackersupervisor.SharedClasses.Objects.Client;
import com.example.tiber.trackersupervisor.SharedClasses.Objects.Supervisor;

/**
 * Created by tiber on 11/2/2016.
 */

public class GetClientsAsync extends MyAsyncTask {

    ArrayAdapter<String> adapter;
    ArrayList<String> clientsNames;
    ArrayList<Client> clients;

    public GetClientsAsync(Context context,ArrayAdapter<String> adapter, ArrayList<Client> clients, ArrayList<String> clientsNames) {
        super(context);
        this.adapter = adapter;
        this.clientsNames = clientsNames;
        this.clients = clients;
    }


    ArrayList<Client> localClients = null;
    @Override
    protected Void doInBackground(Void... params) {
        ServerConnection<Supervisor,ArrayList<Client>> connection =
                new ServerConnection<Supervisor,ArrayList<Client>>(context);
        try {
            localClients = connection.execute(RequestedAction.GET_CLIENTS_FOR_SUPERVISOR,
                    new Supervisor(
                            context.getResources().getInteger(R.integer.SUPERVISOR_ID),
                            context.getResources().getString(R.string.SUPERVISOR_NAME)

                    ));
        } catch (KeyNotMappedException e) {
            e.printStackTrace();
        }
        return null;

    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(localClients == null){
            Toast.makeText(context,"Problem getting the clients list", Toast.LENGTH_SHORT).show();
        }
        else {
            clientsNames.clear();
            adapter.clear();
            for(Client client: localClients){
                clientsNames.add(client.getName());
            }

            clients.clear();
            clients.addAll(localClients);

            ArrayList<String> auxClientNames = (ArrayList<String>) clientsNames.clone();
            adapter.clear();
            adapter.addAll(auxClientNames);
            adapter.notifyDataSetChanged();
        }

    }
}
