package com.thetrooper.health.healthapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thetrooper.health.healthapp.Chat.ChatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

public class FormActivity extends AppCompatActivity {
    int form = 0;
    private DatabaseReference mDatabaseReference;
    ListView listView;
    ArrayList<String> list;
    MyAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                adapter.add(new String(""));
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

        setAdapter();

        findViewById(R.id.form_done_FAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.i("list.size() = " + list.size());
                Timber.i("listView.getChildCount()" + listView.getChildCount());
                switch (form) {
                    case 0:
                        for (int i = 0; i < list.size(); i++)
                            if (listView.getChildAt(i) != null)
                                mDatabaseReference.child("user/abc/details/" + list.get(i))
                                        .setValue(((EditText) listView.getChildAt(i)).getText().toString());
                        break;
                    case 1:
                        for (int i = 0; i < list.size(); i++)
                            if (listView.getChildAt(i) != null)
                                mDatabaseReference.child("user/abc/medical conditions/" + list.get(i))
                                        .setValue(((CheckBox) listView.getChildAt(i)).isChecked());
                        break;
                    case 2:
                        for (int i = 0; i < list.size(); i++)
                            if (listView.getChildAt(i) != null)
                                mDatabaseReference.child("user/abc/family history/" + list.get(i))
                                        .setValue(((CheckBox) listView.getChildAt(i)).isChecked());

                        startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                        finish();
                        return;
                    default:
                        throw new RuntimeException("invalid form");
                }
                form++;
                setAdapter();
            }
        });
    }

    private void setupFirebase() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void setAdapter() {
        list = null;
        list = new ArrayList<>();
        List<java.lang.String> stringList;
        switch (form) {
            case 0:
                setTitle(R.string.details);
                stringList = Arrays.asList(getResources().getStringArray(R.array.form_details));
                break;
            case 1:
                setTitle(R.string.med_con);
                stringList = Arrays.asList(getResources().getStringArray(R.array.form_med_con));
                break;
            case 2:
                setTitle(R.string.fam_his);
                stringList = Arrays.asList(getResources().getStringArray(R.array.form_fam_his));
                break;
            default:
                throw new RuntimeException("invalid form");
        }
        list.addAll(stringList);

        adapter = new MyAdapter(this, list);
        listView = findViewById(R.id.details);
        listView.setAdapter(adapter);
    }

    private class MyAdapter extends ArrayAdapter<String> {

        MyAdapter(Activity context, ArrayList<String> items) {
            super(context, 0, items);
        }

        @Override
        public View getView(int position, View listItemView, ViewGroup parent) {
            switch (form) {
                case 0:
                    if (listItemView == null) listItemView = LayoutInflater.from(getContext())
                            .inflate(R.layout.item_form_edit_text, parent, false);
                    ((EditText) listItemView).setHint(getItem(position));
                    break;
                case 1:
                    if (listItemView == null) listItemView = LayoutInflater.from(getContext())
                            .inflate(R.layout.item_form_checkbox, parent, false);
                    ((CheckBox) listItemView).setText(getItem(position));
                    break;
                case 2:
                    if (listItemView == null) listItemView = LayoutInflater.from(getContext())
                            .inflate(R.layout.item_form_checkbox, parent, false);
                    ((CheckBox) listItemView).setText(getItem(position));
                    break;
            }

            return listItemView;
        }
    }
}
//TODO: medical conditions
//TODO: medications
//TODO: recurring pain/injuries
//TODO: allergies
//TODO: family history
