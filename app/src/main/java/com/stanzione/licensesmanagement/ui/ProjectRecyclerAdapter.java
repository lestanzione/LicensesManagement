package com.stanzione.licensesmanagement.ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stanzione.licensesmanagement.R;
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
        void onProjectToEdit(int position);
        void onProjectToDelete(int position);
    }

    private static final String TAG = ProjectRecyclerAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Project> values;
    private UserAccess loggedUser;
    private WeakReference<OnProjectListener> activity;

    private boolean isFirstLoad = true;
    private boolean showEdit = false;
    private float originalEditIconPosition = 0.0f;
    private float originalRemoveIconPosition = 0.0f;

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

        if(showEdit){
            holder.projectListItemRemoveIcon.setVisibility(View.VISIBLE);
            holder.projectListItemEditIcon.setVisibility(View.VISIBLE);
            //ObjectAnimator anim = ObjectAnimator.ofFloat(holder.companyListItemAddress, "alpha", 0f, 1f);
            //anim.setDuration(1000);
            //anim.start();
            ObjectAnimator animEditIcon = ObjectAnimator.ofFloat(holder.projectListItemEditIcon, "translationX", holder.projectListItemEditIcon.getX(), originalEditIconPosition);
            animEditIcon.setDuration(500);
            animEditIcon.start();
            ObjectAnimator animRemoveIcon = ObjectAnimator.ofFloat(holder.projectListItemRemoveIcon, "translationX", holder.projectListItemRemoveIcon.getX(), originalRemoveIconPosition);
            animRemoveIcon.setDuration(500);
            animRemoveIcon.start();
        }
        else{
            if(isFirstLoad) {
                holder.projectListItemEditIcon.setVisibility(View.INVISIBLE);
                holder.projectListItemRemoveIcon.setVisibility(View.INVISIBLE);
            }
            else {
                holder.projectListItemEditIcon.setVisibility(View.VISIBLE);
                holder.projectListItemRemoveIcon.setVisibility(View.VISIBLE);
            }

            ObjectAnimator animEditIcon = ObjectAnimator.ofFloat(holder.projectListItemEditIcon, "translationX", originalEditIconPosition, originalEditIconPosition + 300);
            animEditIcon.setDuration(500);
            animEditIcon.start();

            ObjectAnimator animRemoveIcon = ObjectAnimator.ofFloat(holder.projectListItemRemoveIcon, "translationX", originalRemoveIconPosition, originalRemoveIconPosition + 300);
            animRemoveIcon.setDuration(500);
            animRemoveIcon.start();

        }

        holder.projectListItemEditIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "selectedProject ID: " + currentProject.getId());
                activity.get().onProjectToEdit(projectPosition);
            }
        });

        holder.projectListItemRemoveIcon.setOnClickListener(new View.OnClickListener() {
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

    public void setShowEdit(boolean showEdit){
        this.showEdit = showEdit;
        isFirstLoad = false;
    }

    public boolean getShowEdit(){
        return showEdit;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout projectListRelativeLayout;
        TextView projectListItemName;
        TextView projectListItemCompanyName;
        ImageView projectListItemEditIcon;
        ImageView projectListItemRemoveIcon;

        public ViewHolder(View userView) {
            super(userView);
            this.projectListRelativeLayout = (RelativeLayout) userView.findViewById(R.id.projectListRelativeLayout);
            this.projectListItemName = (TextView) userView.findViewById(R.id.projectListItemName);
            this.projectListItemCompanyName = (TextView) userView.findViewById(R.id.projectListItemCompanyName);
            this.projectListItemEditIcon = (ImageView) userView.findViewById(R.id.projectListItemEditIcon);
            this.projectListItemRemoveIcon = (ImageView) userView.findViewById(R.id.projectListItemRemoveIcon);
        }
    }

}