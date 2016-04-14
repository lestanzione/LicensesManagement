package com.stanzione.licensesmanagement.ui;

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
        void onContactToDelete(int position);
    }

    private static final String TAG = ContactRecyclerAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Contact> values;
    private UserAccess loggedUser;
    private WeakReference<OnContactListener> activity;

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

        holder.contactListItemRemoveButton.setOnClickListener(new View.OnClickListener() {
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout contactListRelativeLayout;
        TextView contactListItemName;
        TextView contactListItemTitle;
        TextView contactListItemCompanyName;
        TextView contactListItemEmail;
        TextView contactListItemTelNumber;
        Button contactListItemRemoveButton;

        public ViewHolder(View userView) {
            super(userView);
            this.contactListRelativeLayout = (RelativeLayout) userView.findViewById(R.id.contactListRelativeLayout);
            this.contactListItemName = (TextView) userView.findViewById(R.id.contactListItemName);
            this.contactListItemTitle = (TextView) userView.findViewById(R.id.contactListItemTitle);
            this.contactListItemCompanyName = (TextView) userView.findViewById(R.id.contactListItemCompanyName);
            this.contactListItemEmail = (TextView) userView.findViewById(R.id.contactListItemEmail);
            this.contactListItemTelNumber = (TextView) userView.findViewById(R.id.contactListItemTelNumber);
            this.contactListItemRemoveButton = (Button) userView.findViewById(R.id.contactListItemRemoveButton);
        }
    }

}