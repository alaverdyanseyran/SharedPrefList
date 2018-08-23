package com.example.sey.sharedpreflist;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String key, val;
    public ArrayList<PEntry> keysList;
    private SharedPreferences.Editor editor;
    final public static String MYPREF = "myPrFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        keysList = new ArrayList<PEntry>();

        SharedPreferences sp = getSharedPreferences(MYPREF, MODE_PRIVATE);
        editor = getSharedPreferences(MYPREF, MODE_PRIVATE).edit();
        final ListView lv = findViewById(R.id.lv);
        final RefAdapter adapter = new RefAdapter(this, keysList);
        lv.setAdapter(adapter);
        makeList(sp);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                PEntry ent = keysList.get(i);
                editor.remove(ent.pkey);
                editor.apply();
                keysList.remove(i);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDialog(adapter);
            }
        });

    }

    private void makeList(SharedPreferences sharedPreferences) {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

            keysList.add(new PEntry(entry.getKey(), entry.getValue().toString()));
        }

    }

    private void callDialog(final RefAdapter refAdapter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");
        builder.setIcon(R.drawable.ic_playlist_add_black_24dp);
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        final EditText etKey = new EditText(this);
        final EditText etVal = new EditText(this);
        builder.setView(ll);
        etKey.setHint("input key");
        etVal.setHint("input value");
        ll.addView(etKey);
        ll.addView(etVal);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                val = etVal.getText().toString();
                key = etKey.getText().toString();
                editor.putString(key, val);
                editor.apply();
                keysList.add(new PEntry(key, val));
                refAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}