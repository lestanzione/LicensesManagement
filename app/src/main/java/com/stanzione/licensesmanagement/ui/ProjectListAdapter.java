package com.stanzione.licensesmanagement.ui;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stanzione.licensesmanagement.Operations;
import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.model.Company;
import com.stanzione.licensesmanagement.model.Project;
import com.stanzione.licensesmanagement.model.UserAccess;

import java.util.ArrayList;

/**
 * Created by Leandro Stanzione on 14/12/2015.
 */
public class ProjectListAdapter extends BaseAdapter implements Operations.OperationsCallback {

    private static final int CODE_REMOVE_PROJECT = 1;

    private static final String TAG = ProjectListAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Project> values;
    private UserAccess loggedUser;
    private static LayoutInflater inflater;

    public ProjectListAdapter(Context context, ArrayList<Project> values, UserAccess loggedUser) {
        this.context = context;
        this.values = values;
        this.loggedUser = loggedUser;
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

        final Project currentProject = values.get(position);

        if (null == convertView) {
            holder = new ViewHolder();
            newView = inflater.inflate(R.layout.row_project_list_item, null);
            holder.projectListItemName = (TextView) newView.findViewById(R.id.projectListItemName);
            holder.projectListItemCompanyName = (TextView) newView.findViewById(R.id.projectListItemCompanyName);
            holder.projectListItemRemoveButton = (Button) newView.findViewById(R.id.projectListItemRemoveButton);
            newView.setTag(holder);

        } else {
            holder = (ViewHolder) newView.getTag();
        }

        holder.projectListItemName.setText(currentProject.getName());
        holder.projectListItemCompanyName.setText(currentProject.getCompanyName());

        holder.projectListItemRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_remove_entity);

                TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialogRemoveTitle);
                TextView dialogDescription = (TextView) dialog.findViewById(R.id.dialogRemoveDescription);
                dialogTitle.setText("Remove Project");
                dialogDescription.setText("Removing this project, any related Project Software and its Licenses will be removed as well. Do you want to continue?");

                Button dialogRemoveButton = (Button) dialog.findViewById(R.id.dialogRemoveRemoveButton);
                Button dialogCancelButton = (Button) dialog.findViewById(R.id.dialogRemoveCancelButton);

                dialogRemoveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "Removing company: " + currentProject.getId() + " - " + currentProject.getName());
                        removeProject(currentProject);
                        dialog.dismiss();
                    }
                });

                dialogCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

        holder.projectListItemRemoveButton.setFocusable(false);

        return newView;

    }

    private void removeProject(Project project){
        Operations ops = new Operations(this, CODE_REMOVE_PROJECT);
        ops.removeProject(project.getId(), loggedUser.getId());
    }

    @Override
    public void onOperationSuccess(Object returnObject, int operationCode) {
        Toast.makeText(context, "Project removed successfully!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onOperationFail(Object returnObject, int operationCode) {

    }

    @Override
    public void onOperationError(Object returnObject, int operationCode) {

    }

    static class ViewHolder {
        TextView projectListItemName;
        TextView projectListItemCompanyName;
        Button projectListItemRemoveButton;
    }

}