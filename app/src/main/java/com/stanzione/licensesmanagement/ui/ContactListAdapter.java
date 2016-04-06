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
import com.stanzione.licensesmanagement.model.Contact;
import com.stanzione.licensesmanagement.model.Project;
import com.stanzione.licensesmanagement.model.UserAccess;

import java.util.ArrayList;

/**
 * Created by Leandro Stanzione on 14/12/2015.
 */
public class ContactListAdapter extends BaseAdapter implements Operations.OperationsCallback {

    private static final int CODE_REMOVE_CONTACT = 1;

    private static final String TAG = ContactListAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Contact> values;
    private UserAccess loggedUser;
    private static LayoutInflater inflater;

    public ContactListAdapter(Context context, ArrayList<Contact> values, UserAccess loggedUser) {
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

        final Contact currentContact = values.get(position);

        if (null == convertView) {
            holder = new ViewHolder();
            newView = inflater.inflate(R.layout.row_contact_list_item, null);
            holder.contactListItemName = (TextView) newView.findViewById(R.id.contactListItemName);
            holder.contactListItemTitle = (TextView) newView.findViewById(R.id.contactListItemTitle);
            holder.contactListItemCompanyName = (TextView) newView.findViewById(R.id.contactListItemCompanyName);
            holder.contactListItemEmail = (TextView) newView.findViewById(R.id.contactListItemEmail);
            holder.contactListItemTelNumber = (TextView) newView.findViewById(R.id.contactListItemTelNumber);
            holder.contactListItemRemoveButton = (Button) newView.findViewById(R.id.contactListItemRemoveButton);
            newView.setTag(holder);

        } else {
            holder = (ViewHolder) newView.getTag();
        }

        holder.contactListItemName.setText(currentContact.getFirstName() + " " + currentContact.getLastName());
        holder.contactListItemTitle.setText(currentContact.getTitle());
        holder.contactListItemCompanyName.setText(currentContact.getCompanyName());
        holder.contactListItemEmail.setText(currentContact.getEmail());
        holder.contactListItemTelNumber.setText(currentContact.getTelNumber());

        holder.contactListItemRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_remove_entity);

                TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialogRemoveTitle);
                TextView dialogDescription = (TextView) dialog.findViewById(R.id.dialogRemoveDescription);
                dialogTitle.setText("Remove Contact");
                dialogDescription.setText("Are you sure you want to remove this Contact?");

                Button dialogRemoveButton = (Button) dialog.findViewById(R.id.dialogRemoveRemoveButton);
                Button dialogCancelButton = (Button) dialog.findViewById(R.id.dialogRemoveCancelButton);

                dialogRemoveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "Removing company: " + currentContact.getId() + " - " + currentContact.getFirstName());
                        removeContact(currentContact);
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

        holder.contactListItemRemoveButton.setFocusable(false);

        return newView;

    }

    private void removeContact(Contact contact){
        Operations ops = new Operations(this, CODE_REMOVE_CONTACT);
        ops.removeContact(contact.getId(), loggedUser.getId());
    }

    @Override
    public void onOperationSuccess(Object returnObject, int operationCode) {
        Toast.makeText(context, "Contact removed successfully!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onOperationFail(Object returnObject, int operationCode) {

    }

    @Override
    public void onOperationError(Object returnObject, int operationCode) {

    }

    static class ViewHolder {
        TextView contactListItemName;
        TextView contactListItemTitle;
        TextView contactListItemCompanyName;
        TextView contactListItemEmail;
        TextView contactListItemTelNumber;
        Button contactListItemRemoveButton;
    }

}