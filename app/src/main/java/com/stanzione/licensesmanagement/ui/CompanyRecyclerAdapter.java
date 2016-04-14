package com.stanzione.licensesmanagement.ui;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.stanzione.licensesmanagement.helper.RemoveEntityDialog;
import com.stanzione.licensesmanagement.model.Company;
import com.stanzione.licensesmanagement.model.Contact;
import com.stanzione.licensesmanagement.model.UserAccess;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Leandro Stanzione on 14/12/2015.
 */
public class CompanyRecyclerAdapter extends RecyclerView.Adapter<CompanyRecyclerAdapter.ViewHolder> {

    public interface OnCompanyListener{
        void onCompanySelected(int position);
        void onCompanyToDelete(int position);
    }

    private static final String TAG = CompanyRecyclerAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Company> values;
    private UserAccess loggedUser;
    private WeakReference<OnCompanyListener> activity;

    private boolean isFirstLoad = true;
    private boolean showEdit = false;
    private float originalRemoveIconPosition = 0.0f;

    public CompanyRecyclerAdapter(Context context, ArrayList<Company> values, UserAccess loggedUser, OnCompanyListener activity) {
        this.context = context;
        this.values = values;
        this.loggedUser = loggedUser;
        this.activity = new WeakReference<OnCompanyListener>(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_company_list_item, null);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Company currentCompany = values.get(position);
        final int companyPosition = position;

        if(isFirstLoad){
            originalRemoveIconPosition = holder.companyListItemRemoveButton.getX();
            //isFirstLoad = false;
        }

        holder.companyListItemName.setText(currentCompany.getName());
        holder.companyListItemAddress.setText(currentCompany.getAddress());

        if(showEdit){
            holder.companyListItemRemoveButton.setVisibility(View.VISIBLE);
            //ObjectAnimator anim = ObjectAnimator.ofFloat(holder.companyListItemAddress, "alpha", 0f, 1f);
            //anim.setDuration(1000);
            //anim.start();
            ObjectAnimator anim2 = ObjectAnimator.ofFloat(holder.companyListItemRemoveButton, "translationX", holder.companyListItemRemoveButton.getX(), originalRemoveIconPosition);
            anim2.setDuration(500);
            anim2.start();
        }
        else{
            if(isFirstLoad) {
                holder.companyListItemRemoveButton.setVisibility(View.INVISIBLE);
            }
            else {
                holder.companyListItemRemoveButton.setVisibility(View.VISIBLE);
            }

            ObjectAnimator anim2 = ObjectAnimator.ofFloat(holder.companyListItemRemoveButton, "translationX", originalRemoveIconPosition, originalRemoveIconPosition + 300);
            anim2.setDuration(500);
            anim2.start();

        }

        holder.companyListItemRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Remove Company")
                        .setMessage("Removing this company, any related Project and its Licenses will be removed as well. Do you want to continue?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.d(TAG, "Removing company: " + currentCompany.getId() + " - " + currentCompany.getName());
                                removeCompany(companyPosition);
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

        holder.companyListRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "selectedCompany ID: " + currentCompany.getId());
                activity.get().onCompanySelected(companyPosition);

            }
        });

    }

    private void removeCompany(int companyPosition){
        activity.get().onCompanyToDelete(companyPosition);
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
        RelativeLayout companyListRelativeLayout;
        TextView companyListItemName;
        TextView companyListItemAddress;
        Button companyListItemRemoveButton;

        public ViewHolder(View userView) {
            super(userView);
            this.companyListRelativeLayout = (RelativeLayout) userView.findViewById(R.id.companyListRelativeLayout);
            this.companyListItemName = (TextView) userView.findViewById(R.id.companyListItemName);
            this.companyListItemAddress = (TextView) userView.findViewById(R.id.companyListItemAddress);
            this.companyListItemRemoveButton = (Button) userView.findViewById(R.id.companyListItemRemoveButton);
        }
    }

}