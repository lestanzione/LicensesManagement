package com.stanzione.licensesmanagement.helper;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.stanzione.licensesmanagement.R;

/**
 * Created by lstanzione on 4/13/2016.
 */
public class RemoveEntityDialog extends DialogFragment {

    public interface OnRemoveEntityListener{
        void onPositiveSelection();
        void onNegativeSelection();
    }

    public RemoveEntityDialog(OnRemoveEntityListener onRemoveEntityListener, String title, String message, String positiveTitle, String negativeTitle){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Title")
                .setMessage("Message")
                .setPositiveButton("Positive", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    }
                })
                .setNegativeButton("Negative", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        return builder.create();

    }

}