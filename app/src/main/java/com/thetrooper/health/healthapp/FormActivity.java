package com.thetrooper.health.healthapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thetrooper.health.healthapp.Chat.ChatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class FormActivity extends AppCompatActivity {
    int form = 0;
    private DatabaseReference mDatabaseReference;
    ArrayList<Item> list;
    MyAdapter adapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                adapter.add(new Item(""));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        setupFirebase();

        setList();
        adapter = new MyAdapter(this, list);
        final ListView listView = findViewById(R.id.details);
        listView.setAdapter(adapter);

        findViewById(R.id.form_done_FAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key;
                switch (form) {
                    case 0:
                        key = "details";
                        for (int i = 0; i < list.size(); i++)
                            mDatabaseReference.child("user/abc/" + key + "/" + list.get(i).title)
                                    .setValue(((EditText) listView.getChildAt(i)).getText().toString());
                        break;
                    case 1:
                        key = "medical conditions";
                        break;
                    case 2:
                        key = "medications";
                        break;
                    case 3:
                        key = "recurring pain and injuries";
                        break;
                    case 4:
                        key = "allergies";
                        break;
                    case 5:
                        key = "family history";
                        break;
                    default:
                        throw new RuntimeException("invalid form");
                }
                for (int i = 0; i < list.size(); i++) {
                    mDatabaseReference.child("user/abc/" + key + "/" + list.get(i).title).push()
                            .setValue(((EditText) listView.getChildAt(i)).getText().toString());
                    //TODO: use the users name/id
                }
                startActivity(new Intent(getApplicationContext(), ChatActivity.class));
            }
        });
    }

    private void setupFirebase() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void setList() {
        list = new ArrayList<>();
        if (form == 0) {
            for (String string : Arrays.asList(getResources().getStringArray(R.array.form)))
                list.add(new Item(string));
        } else {
            switch (form) {
                case 1:
                    setTitle(R.string.med_con);
                    break;
                case 2:
                    setTitle(R.string.medic);
                    break;
                case 3:
                    setTitle(R.string.rec);
                    break;
                case 4:
                    setTitle(R.string.allergies);
                    break;
                case 5:
                    setTitle(R.string.fam_his);
                    break;
            }

        }
    }

    private class Item {
        String title;
        String value;

        Item(String title) {
            this.title = title;
        }
    }

    private class MyAdapter extends ArrayAdapter<Item> {

        MyAdapter(Activity context, ArrayList<Item> items) {
            super(context, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.form_item,
                        parent, false);
            }

            EditText view = listItemView.findViewById(R.id.text);
            view.setHint(getItem(position).title);

            return listItemView;
        }
    }
}

//TODO: medical conditions
//TODO: medications
//TODO: recurring pain/injuries
//TODO: allergies
//TODO: family history