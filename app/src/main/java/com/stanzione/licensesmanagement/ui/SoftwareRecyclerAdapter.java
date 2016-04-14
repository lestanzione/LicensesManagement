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
import com.stanzione.licensesmanagement.model.Project;
import com.stanzione.licensesmanagement.model.Software;
import com.stanzione.licensesmanagement.model.UserAccess;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Leandro Stanzione on 14/12/2015.
 */
public class SoftwareRecyclerAdapter extends RecyclerView.Adapter<SoftwareRecyclerAdapter.ViewHolder> {

    public interface OnSoftwareListener{
        void onSoftwareSelected(int position);
        void onSoftwareToDelete(int position);
    }

    private static final String TAG = SoftwareRecyclerAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Software> values;
    private UserAccess loggedUser;
    private WeakReference<OnSoftwareListener> activity;

    public SoftwareRecyclerAdapter(Context context, ArrayList<Software> values, UserAccess loggedUser, OnSoftwareListener activity) {
        this.context = context;
        this.values = values;
        this.loggedUser = loggedUser;
        this.activity = new WeakReference<OnSoftwareListener>(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_software_list_item, null);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Software currentSoftware = values.get(position);
        final int softwarePosition = position;

        holder.softwareListItemName.setText(currentSoftware.getName());
        holder.softwareListItemCode.setText(currentSoftware.getCode());
        holder.softwareListItemType.setText(currentSoftware.getType());

        holder.softwareListRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "selectedSoftware ID: " + currentSoftware.getId());
                activity.get().onSoftwareSelected(softwarePosition);

            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != values ? values.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout softwareListRelativeLayout;
        TextView softwareListItemName;
        TextView softwareListItemCode;
        TextView softwareListItemType;

        public ViewHolder(View userView) {
            super(userView);
            this.softwareListRelativeLayout = (RelativeLayout) userView.findViewById(R.id.softwareListRelativeLayout);
            this.softwareListItemName = (TextView) userView.findViewById(R.id.softwareListItemName);
            this.softwareListItemCode = (TextView) userView.findViewById(R.id.softwareListItemCode);
            this.softwareListItemType = (TextView) userView.findViewById(R.id.softwareListItemType);
        }
    }

}