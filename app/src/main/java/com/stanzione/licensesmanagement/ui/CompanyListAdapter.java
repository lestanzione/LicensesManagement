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
import com.stanzione.licensesmanagement.model.UserAccess;

import java.util.ArrayList;

/**
 * Created by Leandro Stanzione on 14/12/2015.
 */
public class CompanyListAdapter extends BaseAdapter implements Operations.OperationsCallback{

    private static final int CODE_REMOVE_COMPANY = 1;

    private static final String TAG = CompanyListAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<Company> values;
    private UserAccess loggedUser;
    private static LayoutInflater inflater;

    public CompanyListAdapter(Context context, ArrayList<Company> values, UserAccess loggedUser) {
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

        final Company currentCompany = values.get(position);

        if (null == convertView) {
            holder = new ViewHolder();
            newView = inflater.inflate(R.layout.row_company_list_item, null);
            holder.companyListItemName = (TextView) newView.findViewById(R.id.companyListItemName);
            holder.companyListItemAddress = (TextView) newView.findViewById(R.id.companyListItemAddress);
            holder.companyListItemRemoveButton = (Button) newView.findViewById(R.id.companyListItemRemoveButton);
            newView.setTag(holder);

        } else {
            holder = (ViewHolder) newView.getTag();
        }

        holder.companyListItemName.setText(currentCompany.getName());
        holder.companyListItemAddress.setText(currentCompany.getAddress());

        holder.companyListItemRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_remove_entity);

                TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialogRemoveTitle);
                TextView dialogDescription = (TextView) dialog.findViewById(R.id.dialogRemoveDescription);
                dialogTitle.setText("Remove Company");
                dialogDescription.setText("Removing this company, any related Project and its Licenses will be removed as well. Do you want to continue?");

                Button dialogRemoveButton = (Button) dialog.findViewById(R.id.dialogRemoveRemoveButton);
                Button dialogCancelButton = (Button) dialog.findViewById(R.id.dialogRemoveCancelButton);

                dialogRemoveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "Removing company: " + currentCompany.getId() + " - " + currentCompany.getName());
                        removeCompany(currentCompany);
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

        holder.companyListItemRemoveButton.setFocusable(false);

        return newView;

    }

    private void removeCompany(Company company){
        Operations ops = new Operations(this, CODE_REMOVE_COMPANY);
        ops.removeCompany(company.getId(), loggedUser.getId());
    }

    @Override
    public void onOperationSuccess(Object returnObject, int operationCode) {
        Toast.makeText(context, "Company removed successfully!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onOperationFail(Object returnObject, int operationCode) {

    }

    @Override
    public void onOperationError(Object returnObject, int operationCode) {

    }

    static class ViewHolder {
        TextView companyListItemName;
        TextView companyListItemAddress;
        Button companyListItemRemoveButton;
    }

}