package com.example.tiber.trackersupervisor.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.tiber.trackersupervisor.Clase.AsyncRequests.GetSmsConversationAsync;
import com.example.tiber.trackersupervisor.Clase.ConversationListViewAdapter;
import com.example.tiber.trackersupervisor.R;
import com.example.tiber.trackersupervisor.SharedClasses.Objects.Contact;
import com.example.tiber.trackersupervisor.SharedClasses.Objects.SMSGroupDetails;
import com.example.tiber.trackersupervisor.SharedClasses.Objects.SmsData;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ConversationViewerActivity extends AppCompatActivity {

    SMSGroupDetails smsGroup = null;
    Contact contact = null;

    ConversationListViewAdapter adapter;
    ArrayList<SmsData> smsList = new ArrayList<SmsData>();
    ListView lvSmsConversation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_viewer);
        String smsGroupJson = getIntent().getStringExtra("selectedSmsGroup");
        smsGroup = new Gson().fromJson(smsGroupJson,SMSGroupDetails.class);
        contact = smsGroup.getContact();
        setTitle("Conversation with "+ contact.getNames());
        lvSmsConversation = (ListView) findViewById(R.id.lvSmsConversation);
        adapter = new ConversationListViewAdapter(this,smsList);
        lvSmsConversation.setAdapter(adapter);
        new GetSmsConversationAsync(this,contact,adapter,lvSmsConversation).execute();
    }




}
