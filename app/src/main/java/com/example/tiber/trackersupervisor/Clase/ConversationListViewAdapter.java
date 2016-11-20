package com.example.tiber.trackersupervisor.Clase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tiber.trackersupervisor.R;
import com.example.tiber.trackersupervisor.SharedClasses.Objects.SmsData;
import com.example.tiber.trackersupervisor.SharedClasses.Utils.DateUtil;

import java.util.ArrayList;

/**
 * Created by tiber on 11/20/2016.
 */

public class ConversationListViewAdapter extends ArrayAdapter<SmsData> {


    public ConversationListViewAdapter(Context context, ArrayList<SmsData> smsList) {
        super(context, 0, smsList);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        SmsData sms = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sms_item, parent, false);
        }
        // Lookup view for data population
        TextView tvSmsText = (TextView) convertView.findViewById(R.id.smsMessageText);
        TextView tvSmsTime = (TextView) convertView.findViewById(R.id.smsMessageTime);
        RelativeLayout smsDataContainer = (RelativeLayout) convertView.findViewById(R.id.smsContainerRelativeLayout);
        RelativeLayout smsLvItem = (RelativeLayout) convertView.findViewById(R.id.smsLvItem);

        tvSmsText.setText(sms.getMessage());
        tvSmsTime.setText(DateUtil.fromIntFormat(sms.getDate()).toString());

        sms.prepareContainerForDisplay(smsDataContainer);
        sms.prepareContainerForDisplay(smsLvItem);

        return convertView;
    }
}
