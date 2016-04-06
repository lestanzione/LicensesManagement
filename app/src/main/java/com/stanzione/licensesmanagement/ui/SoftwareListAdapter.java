package com.stanzione.licensesmanagement.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.model.Company;
import com.stanzione.licensesmanagement.model.Software;

import java.util.ArrayList;

/**
 * Created by Leandro Stanzione on 14/12/2015.
 */
public class SoftwareListAdapter extends BaseAdapter {

    private static final String TAG = SoftwareListAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Software> values;
    private static LayoutInflater inflater;

    public SoftwareListAdapter(Context context, ArrayList<Software> values) {
        this.context = context;
        this.values = values;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View newView = convertView;
        ViewHolder holder;

        final Software currentSoftware = values.get(position);

        if (null == convertView) {
            holder = new ViewHolder();
            newView = inflater.inflate(R.layout.row_software_list_item, null);
            holder.softwareListItemName = (TextView) newView.findViewById(R.id.softwareListItemName);
            holder.softwareListItemCode = (TextView) newView.findViewById(R.id.softwareListItemCode);
            holder.softwareListItemType = (TextView) newView.findViewById(R.id.softwareListItemType);
            newView.setTag(holder);

        } else {
            holder = (ViewHolder) newView.getTag();
        }

        holder.softwareListItemName.setText(currentSoftware.getName());
        holder.softwareListItemCode.setText(currentSoftware.getCode());
        holder.softwareListItemType.setText(currentSoftware.getType());

        return newView;

    }

    static class ViewHolder {
        TextView softwareListItemName;
        TextView softwareListItemCode;
        TextView softwareListItemType;
    }

}