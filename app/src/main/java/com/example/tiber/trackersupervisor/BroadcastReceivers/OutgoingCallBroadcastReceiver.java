package com.example.tiber.trackersupervisor.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.tiber.trackersupervisor.Activities.MainActivity;

/**
 * Created by tiber on 11/23/2016.
 */

public class OutgoingCallBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.wtf("ceva","ceca");
        if (intent. getAction (). equals (Intent. ACTION_NEW_OUTGOING_CALL)) {
// If it is to call (outgoing)
            Intent i = new Intent(context, MainActivity.class);
            i.putExtras(intent);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

}