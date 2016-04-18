package com.stanzione.licensesmanagement.ui;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stanzione.licensesmanagement.Operations;
import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.model.Contact;
import com.stanzione.licensesmanagement.model.UserAccess;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Leandro Stanzione on 14/12/2015.
 */
public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.ViewHolder> {

    public interface OnContactListener{
        void onContactSelected(int position);
        void onContactToEdit(int position);
        void onContactToDelete(int position);
    }

    private static final String TAG = ContactRecyclerAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Contact> values;
    private UserAccess loggedUser;
    private WeakReference<OnContactListener> activity;

    private boolean isFirstLoad = true;
    private boolean showEdit = false;
    private float originalEditIconPosition = 0.0f;
    private float originalRemoveIconPosition = 0.0f;

    public ContactRecyclerAdapter(Context context, ArrayList<Contact> values, UserAccess loggedUser, OnContactListener activity) {
        this.context = context;
        this.values = values;
        this.loggedUser = loggedUser;
        this.activity = new WeakReference<OnContactListener>(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contact_list_item, null);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Contact currentContact = values.get(position);
        final int contactPosition = position;

        holder.contactListItemName.setText(currentContact.getFirstName() + " " + currentContact.getLastName());
        holder.contactListItemTitle.setText(currentContact.getTitle());
        holder.contactListItemCompanyName.setText(currentContact.getCompanyName());
        holder.contactListItemEmail.setText(currentContact.getEmail());
        holder.contactListItemTelNumber.setText(currentContact.getTelNumber());

        if(showEdit){
            holder.contactListItemRemoveIcon.setVisibility(View.VISIBLE);
            holder.contactListItemEditIcon.setVisibility(View.VISIBLE);
            //ObjectAnimator anim = ObjectAnimator.ofFloat(holder.companyListItemAddress, "alpha", 0f, 1f);
            //anim.setDuration(1000);
            //anim.start();
            ObjectAnimator animEditIcon = ObjectAnimator.ofFloat(holder.contactListItemEditIcon, "translationX", holder.contactListItemEditIcon.getX(), originalEditIconPosition);
            animEditIcon.setDuration(500);
            animEditIcon.start();
            ObjectAnimator animRemoveIcon = ObjectAnimator.ofFloat(holder.contactListItemRemoveIcon, "translationX", holder.contactListItemRemoveIcon.getX(), originalRemoveIconPosition);
            animRemoveIcon.setDuration(500);
            animRemoveIcon.start();
        }
        else{
            if(isFirstLoad) {
                holder.contactListItemEditIcon.setVisibility(View.INVISIBLE);
                holder.contactListItemRemoveIcon.setVisibility(View.INVISIBLE);
            }
            else {
                holder.contactListItemEditIcon.setVisibility(View.VISIBLE);
                holder.contactListItemRemoveIcon.setVisibility(View.VISIBLE);
            }

            ObjectAnimator animEditIcon = ObjectAnimator.ofFloat(holder.contactListItemEditIcon, "translationX", originalEditIconPosition, originalEditIconPosition + 300);
            animEditIcon.setDuration(500);
            animEditIcon.start();

            ObjectAnimator animRemoveIcon = ObjectAnimator.ofFloat(holder.contactListItemRemoveIcon, "translationX", originalRemoveIconPosition, originalRemoveIconPosition + 300);
            animRemoveIcon.setDuration(500);
            animRemoveIcon.start();

        }

        holder.contactListItemEditIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "selectedContact ID: " + currentContact.getId());
                activity.get().onContactToEdit(contactPosition);
            }
        });

        holder.contactListItemRemoveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Remove Contact")
                        .setMessage("Are you sure you want to remove this Contact?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.d(TAG, "Removing contact: " + currentContact.getId() + " - " + currentContact.getFirstName());
                                removeContact(contactPosition);
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

        holder.contactListRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "selectedContact ID: " + currentContact.getId());
                activity.get().onContactSelected(contactPosition);

            }
        });

    }

    private void removeContact(int contactPosition){
        activity.get().onContactToDelete(contactPosition);
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
        RelativeLayout contactListRelativeLayout;
        TextView contactListItemName;
        TextView contactListItemTitle;
        TextView contactListItemCompanyName;
        TextView contactListItemEmail;
        TextView contactListItemTelNumber;
        ImageView contactListItemEditIcon;
        ImageView contactListItemRemoveIcon;

        public ViewHolder(View userView) {
            super(userView);
            this.contactListRelativeLayout = (RelativeLayout) userView.findViewById(R.id.contactListRelativeLayout);
            this.contactListItemName = (TextView) userView.findViewById(R.id.contactListItemName);
            this.contactListItemTitle = (TextView) userView.findViewById(R.id.contactListItemTitle);
            this.contactListItemCompanyName = (TextView) userView.findViewById(R.id.contactListItemCompanyName);
            this.contactListItemEmail = (TextView) userView.findViewById(R.id.contactListItemEmail);
            this.contactListItemTelNumber = (TextView) userView.findViewById(R.id.contactListItemTelNumber);
            this.contactListItemEditIcon = (ImageView) userView.findViewById(R.id.contactListItemEditIcon);
            this.contactListItemRemoveIcon = (ImageView) userView.findViewById(R.id.contactListItemRemoveIcon);
        }
    }

}