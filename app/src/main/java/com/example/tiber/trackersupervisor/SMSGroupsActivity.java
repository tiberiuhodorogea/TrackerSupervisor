package com.example.tiber.trackersupervisor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tiber.trackersupervisor.Clase.AsyncRequests.GetSmsGroupsAsync;
import com.example.tiber.trackersupervisor.SharedClasses.Objects.Client;
import com.example.tiber.trackersupervisor.SharedClasses.Objects.SMSGroupDetails;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SMSGroupsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView listView;
    ArrayList<SMSGroupDetails> smsGroups = new ArrayList<SMSGroupDetails>();
    ArrayList<String> smsGroupsString = new ArrayList<String>();
    ArrayAdapter<String> adapter = null;
    Client selectedClient = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsselecter);
        String clientJson = getIntent().getStringExtra("selectedClient");
        selectedClient = new Gson().fromJson(clientJson,Client.class);
        setupListView();
        new GetSmsGroupsAsync(this,adapter,selectedClient.getId(),smsGroupsString,smsGroups).execute();

    }
    private void setupListView() {
        listView = (ListView) findViewById(R.id.lvSmsGroups);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, smsGroupsString);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    public void onButtonLoadClickSmsGoups(View v) {
        new GetSmsGroupsAsync(this,adapter,selectedClient.getId(),smsGroupsString,smsGroups).execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
