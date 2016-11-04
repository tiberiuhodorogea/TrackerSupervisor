package com.example.tiber.trackersupervisor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SMSGroupsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView listView;
    ArrayList<String> smsGroups = new ArrayList<String>();
    ArrayAdapter<String> adapter = null;
    String selectedClient = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsselecter);
        selectedClient = getIntent().getStringExtra("selectedClient");
        setupListView();

    }
    private void setupListView() {
        listView = (ListView) findViewById(R.id.lvSmsGroups);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, smsGroups);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    public void onButtonLoadClickSmsGoups(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
