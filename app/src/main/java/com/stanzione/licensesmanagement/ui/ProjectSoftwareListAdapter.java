package com.stanzione.licensesmanagement.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.model.Contact;
import com.stanzione.licensesmanagement.model.ProjectSoftware;

import java.util.ArrayList;

/**
 * Created by Leandro Stanzione on 14/12/2015.
 */
public class ProjectSoftwareListAdapter extends BaseAdapter {

    private static final String TAG = ProjectSoftwareListAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<ProjectSoftware> values;
    private static LayoutInflater inflater;

    public ProjectSoftwareListAdapter(Context context, ArrayList<ProjectSoftware> values) {
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

        final ProjectSoftware currentProjectSoftware = values.get(position);

        if (null == convertView) {
            holder = new ViewHolder();
            newView = inflater.inflate(R.layout.row_project_software_list_item, null);
            holder.projectSoftwareListItemSoftwareName = (TextView) newView.findViewById(R.id.projectSoftwareListItemSoftwareName);
            newView.setTag(holder);

        } else {
            holder = (ViewHolder) newView.getTag();
        }

        holder.projectSoftwareListItemSoftwareName.setText(currentProjectSoftware.getSoftwareName());

        return newView;

    }

    static class ViewHolder {
        TextView projectSoftwareListItemSoftwareName;
    }

}