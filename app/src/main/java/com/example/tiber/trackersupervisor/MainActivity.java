package com.example.tiber.trackersupervisor;

import android.content.Intent;
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

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, PopupMenu.OnMenuItemClickListener {

    ListView listView;
    ArrayList<String> clients = new ArrayList<String>();
    ArrayAdapter<String> adapter = null;
    String selectedClient = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupListView();

        new GetClientsAsync(this,adapter).execute();

    }

    private void setupListView() {
        listView = (ListView) findViewById(R.id.clientsListView);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, clients);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        registerForContextMenu(listView);
    }

    public void onButtonLoadClick(View v){
        new GetClientsAsync(this,adapter).execute();
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
        intent.putExtra("client",selectedClient);

        switch (item.getItemId()) {
            case R.id.itemLocation:
                intent.setClass(this,MapActivity.class);
                break;
            case R.id.itemSMS:
                Toast.makeText(this, "SMS Clicked", Toast.LENGTH_SHORT).show();

                break;
            default:
                Toast.makeText(this,"Ceva nu e bine, default pe switch onmenuclick",Toast.LENGTH_LONG).show();
                return true;
        }

        startActivity(intent);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //nuthins
    }



}
