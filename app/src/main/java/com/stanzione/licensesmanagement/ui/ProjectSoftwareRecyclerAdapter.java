package com.stanzione.licensesmanagement.ui;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.model.Contact;
import com.stanzione.licensesmanagement.model.ProjectSoftware;
import com.stanzione.licensesmanagement.model.UserAccess;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Leandro Stanzione on 14/12/2015.
 */
public class ProjectSoftwareRecyclerAdapter extends RecyclerView.Adapter<ProjectSoftwareRecyclerAdapter.ViewHolder> {

    public interface OnProjectSoftwareListener{
        void onProjectSoftwareSelected(int position);
        void onProjectSoftwareToDelete(int position);
    }

    private static final String TAG = ProjectSoftwareRecyclerAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<ProjectSoftware> values;
    private UserAccess loggedUser;
    private WeakReference<OnProjectSoftwareListener> activity;

    public ProjectSoftwareRecyclerAdapter(Context context, ArrayList<ProjectSoftware> values, UserAccess loggedUser, OnProjectSoftwareListener activity) {
        this.context = context;
        this.values = values;
        this.loggedUser = loggedUser;
        this.activity = new WeakReference<OnProjectSoftwareListener>(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_project_software_list_item, null);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final ProjectSoftware currentProjectSoftware = values.get(position);
        final int projectSoftwarePosition = position;

        holder.projectSoftwareListItemSoftwareName.setText(currentProjectSoftware.getSoftwareName());

        holder.projectSoftwareListRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "selectedProjectSoftware ID: " + currentProjectSoftware.getId());
                activity.get().onProjectSoftwareSelected(projectSoftwarePosition);

            }
        });

    }

    private void removeProjectSoftware(int projectSoftwarePosition){
        activity.get().onProjectSoftwareToDelete(projectSoftwarePosition);
    }

    @Override
    public int getItemCount() {
        return (null != values ? values.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout projectSoftwareListRelativeLayout;
        TextView projectSoftwareListItemSoftwareName;

        public ViewHolder(View userView) {
            super(userView);
            this.projectSoftwareListRelativeLayout = (RelativeLayout) userView.findViewById(R.id.projectSoftwareListRelativeLayout);
            this.projectSoftwareListItemSoftwareName = (TextView) userView.findViewById(R.id.projectSoftwareListItemSoftwareName);
        }
    }

}