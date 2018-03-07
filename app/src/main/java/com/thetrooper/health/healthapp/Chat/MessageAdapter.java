package com.thetrooper.health.healthapp.Chat;

/**
 * Created by manik on 1/1/18.
 */

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thetrooper.health.healthapp.R;

import java.util.List;


public class MessageAdapter extends ArrayAdapter<FriendlyMessage> {

    public MessageAdapter(Context context, int resource, List<FriendlyMessage> friendlyMessages) {
        super(context, resource, friendlyMessages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message,
                    parent, false);
        }

        TextView messageTextView = convertView.findViewById(R.id.messageTextView);
        TextView authorTextView = convertView.findViewById(R.id.nameTextView);

        final FriendlyMessage message = getItem(position);

        messageTextView.setVisibility(View.VISIBLE);
        messageTextView.setText(message.getText());

        setSender(message, convertView);
        return convertView;
    }

    private void setSender(FriendlyMessage message, View convertView) {
        LinearLayout messageLayout = convertView.findViewById(R.id.message);
        if (message.getUser()) {
            messageLayout.setGravity(Gravity.END);
            convertView.findViewById(R.id.spacer_left).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.message).setBackgroundResource(R.drawable.simple_bubble);

            //convertView.findViewById(R.id.nameTextView).
        } else {
            messageLayout.setGravity(Gravity.START);
            convertView.findViewById(R.id.spacer_right).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.message).setBackgroundResource(R.drawable.simple_bubble);

        }
    }
}

//TODO: play videos and gifs
//TODO: improve message layout
//TODO: receive files
//TODO: show name if is group