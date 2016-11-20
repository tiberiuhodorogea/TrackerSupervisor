package com.example.tiber.trackersupervisor.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tiber.trackersupervisor.Clase.AsyncRequests.GetClientsAsync;
import com.example.tiber.trackersupervisor.Clase.AsyncRequests.MyAsyncTask;
import com.example.tiber.trackersupervisor.Clase.ServerConnection;
import com.example.tiber.trackersupervisor.R;
import com.example.tiber.trackersupervisor.SharedClasses.Communication.Exceptions.KeyNotMappedException;
import com.example.tiber.trackersupervisor.SharedClasses.Communication.RequestedAction;
import com.example.tiber.trackersupervisor.SharedClasses.Communication.ResponseEnum;
import com.example.tiber.trackersupervisor.SharedClasses.Objects.Client;
import com.google.gson.Gson;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, PopupMenu.OnMenuItemClickListener {

    public static final int REFRESH_RESULT_CODE = 53;
    ListView listView;
    ArrayList<Client> clients = new ArrayList<Client>();
    ArrayList<String> clientsNames = new ArrayList<String>();
    ArrayAdapter<String> adapter = null;
    Client selectedClient = null;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupListView();
        context = this;
        new GetClientsAsync(this,adapter,clients,clientsNames).execute();

    }

    private void setupListView() {
        listView = (ListView) findViewById(R.id.clientsListView);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, clientsNames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        registerForContextMenu(listView);
    }

    public void onButtonLoadClick(View v){
        new GetClientsAsync(this,adapter,clients,clientsNames).execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedClient = clients.get(position);

        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();

    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {

        Intent intent = new Intent();
        intent.putExtra("selectedClient",new Gson().toJson(selectedClient,Client.class));

        switch (item.getItemId()) {
            case R.id.itemLocation:
                intent.setClass(this,MapActivity.class);
                break;
            case R.id.itemSMS:
                intent.setClass(this,SMSGroupsActivity.class);
                break;
            case R.id.itemDeactivateClient:
                deactivateClientWithConfirmation();
                return true;
            case R.id.itemShowClientId:
                Toast.makeText(this,"ID for "+ selectedClient.getName()
                        + ": "+ selectedClient.getId(),Toast.LENGTH_LONG).show();
                return true;
            default:
                Toast.makeText(this,"Ceva nu e bine, default pe switch onmenuclick",Toast.LENGTH_LONG).show();
                return true;
        }

        startActivity(intent);
        return true;
    }


    private void deactivateClientWithConfirmation() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Confirm");

        alertDialog.setMessage("You sure you wanna de-activate " + selectedClient.getName() + " ?");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new DeactivateClientAsync(context,selectedClient).execute();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (resultCode == REFRESH_RESULT_CODE){//client added  - reoad clients
           new GetClientsAsync(this,adapter,clients,clientsNames).execute();
       }
    }

    public void onAddNewClientFromMainActivityClick(View v){
        Intent startAddNewClientActivity = new Intent(this,AddClientActivity.class);
        startActivityForResult(startAddNewClientActivity,1/*whatever*/);
    }

    class DeactivateClientAsync extends MyAsyncTask{

        private Client client;

        public DeactivateClientAsync(Context context, Client selectedClient) {
            super(context);
            this.client = selectedClient;
        }

        ResponseEnum response;

        @Override
        protected Void doInBackground(Void... params) {
            response = null;
            ServerConnection<Client,ResponseEnum> connection =
                    new ServerConnection<Client,ResponseEnum>(context);
            try {
               response =  connection.execute(RequestedAction.DEACTIVATE_CLIENT,client);
            } catch (KeyNotMappedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (response != ResponseEnum.OK){
                Toast.makeText(context,"Some problem....",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context,"Client deactivated successfully, refreshing....",Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {

                }
                new GetClientsAsync(context,adapter,clients,clientsNames).execute();
            }

        }
    }

}
