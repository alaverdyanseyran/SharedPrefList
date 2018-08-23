package com.example.sey.sharedpreflist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RefAdapter extends ArrayAdapter<PEntry> {

    public RefAdapter(@NonNull Context context, @NonNull ArrayList<PEntry> objects) {
        super(context, 0, objects);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final PEntry pEntry = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ref_item, parent, false);
        }

        TextView tvKey = (TextView) convertView.findViewById(R.id.r_key);
        TextView tvVal = (TextView) convertView.findViewById(R.id.r_val);
        tvKey.setText(pEntry.pkey);
        tvVal.setText(pEntry.pval);
        return convertView;

    }
}
