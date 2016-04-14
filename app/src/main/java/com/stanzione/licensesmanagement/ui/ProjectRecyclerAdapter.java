package com.stanzione.licensesmanagement.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
import com.stanzione.licensesmanagement.model.Company;
import com.stanzione.licensesmanagement.model.Project;
import com.stanzione.licensesmanagement.model.UserAccess;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Leandro Stanzione on 14/12/2015.
 */
public class ProjectRecyclerAdapter extends RecyclerView.Adapter<ProjectRecyclerAdapter.ViewHolder> {

    public interface OnProjectListener{
        void onProjectSelected(int position);
        void onProjectToDelete(int position);
    }

    private static final String TAG = ProjectRecyclerAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Project> values;
    private UserAccess loggedUser;
    private WeakReference<OnProjectListener> activity;

    public ProjectRecyclerAdapter(Context context, ArrayList<Project> values, UserAccess loggedUser, OnProjectListener activity) {
        this.context = context;
        this.values = values;
        this.loggedUser = loggedUser;
        this.activity = new WeakReference<OnProjectListener>(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_project_list_item, null);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Project currentProject = values.get(position);
        final int projectPosition = position;

        holder.projectListItemName.setText(currentProject.getName());
        holder.projectListItemCompanyName.setText(currentProject.getCompanyName());

        holder.projectListItemRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Remove Project")
                        .setMessage("Removing this project, any related Project Software and its Licenses will be removed as well. Do you want to continue?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.d(TAG, "Removing project: " + currentProject.getId() + " - " + currentProject.getName());
                                removeProject(projectPosition);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                builder.create().show();

            }
        });

        holder.projectListRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "selectedProject ID: " + currentProject.getId());
                activity.get().onProjectSelected(projectPosition);

            }
        });

    }

    private void removeProject(int projectPosition){
        activity.get().onProjectToDelete(projectPosition);
    }

    @Override
    public int getItemCount() {
        return (null != values ? values.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout projectListRelativeLayout;
        TextView projectListItemName;
        TextView projectListItemCompanyName;
        Button projectListItemRemoveButton;

        public ViewHolder(View userView) {
            super(userView);
            this.projectListRelativeLayout = (RelativeLayout) userView.findViewById(R.id.projectListRelativeLayout);
            this.projectListItemName = (TextView) userView.findViewById(R.id.projectListItemName);
            this.projectListItemCompanyName = (TextView) userView.findViewById(R.id.projectListItemCompanyName);
            this.projectListItemRemoveButton = (Button) userView.findViewById(R.id.projectListItemRemoveButton);
        }
    }

}