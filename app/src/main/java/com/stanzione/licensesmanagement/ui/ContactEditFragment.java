package com.stanzione.licensesmanagement.ui;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stanzione.licensesmanagement.Operations;
import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.model.Contact;
import com.stanzione.licensesmanagement.model.UserAccess;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactEditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactEditFragment extends Fragment implements Operations.OperationsCallback{

    private static final int CODE_EDIT_CONTACT = 1;

    private static final String ARG_LOGGED_USER = "loggedUser";
    private static final String ARG_SELECTED_CONTACT = "selectedContact";

    private UserAccess loggedUser;
    private Contact selectedContact;

    private static final String TAG = ContactEditFragment.class.getSimpleName();

    private TextView contactId;
    private EditText contactFirstName;
    private EditText contactLastName;
    private EditText contactTitle;
    private EditText contactEmail;
    private EditText contactTelNumber;
    private TextView contactCreationDate;
    private TextView contactModificationDate;
    private TextView contactCreationUser;
    private TextView contactModificationUser;
    private TextView contactCompanyName;
    private CheckBox contactActive;
    private Button saveContactButton;

    private OnFragmentInteractionListener mListener;

    public ContactEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedUser Parameter 1.
     * @param selectedContact Parameter 2.
     * @return A new instance of fragment ContactEditFragment.
     */
    public static ContactEditFragment newInstance(UserAccess loggedUser, Contact selectedContact) {
        ContactEditFragment fragment = new ContactEditFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOGGED_USER, loggedUser);
        args.putSerializable(ARG_SELECTED_CONTACT, selectedContact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loggedUser = (UserAccess) getArguments().getSerializable(ARG_LOGGED_USER);
            selectedContact = (Contact) getArguments().getSerializable(ARG_SELECTED_CONTACT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_edit, container, false);

        contactId = (TextView) view.findViewById(R.id.contactEditId);
        contactFirstName = (EditText) view.findViewById(R.id.contactEditFirstName);
        contactLastName = (EditText) view.findViewById(R.id.contactEditLastName);
        contactTitle = (EditText) view.findViewById(R.id.contactEditTitle);
        contactEmail = (EditText) view.findViewById(R.id.contactEditEmail);
        contactTelNumber = (EditText) view.findViewById(R.id.contactEditTelNumber);
        contactCreationDate = (TextView) view.findViewById(R.id.contactEditCreationDate);
        contactModificationDate = (TextView) view.findViewById(R.id.contactEditModificationDate);
        contactCreationUser = (TextView) view.findViewById(R.id.contactEditCreationUser);
        contactModificationUser = (TextView) view.findViewById(R.id.contactEditModificationUser);
        contactCompanyName = (TextView) view.findViewById(R.id.contactEditCompanyName);
        contactActive = (CheckBox) view.findViewById(R.id.contactEditActive);
        saveContactButton = (Button) view.findViewById(R.id.contactEditSaveButton);

        saveContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContact();
            }
        });

        contactId.setText(String.valueOf(selectedContact.getId()));
        contactFirstName.setText(selectedContact.getFirstName());
        contactLastName.setText(selectedContact.getLastName());
        contactTitle.setText(selectedContact.getTitle());
        contactEmail.setText(selectedContact.getEmail());
        contactTelNumber.setText(selectedContact.getTelNumber());
        contactCreationDate.setText(selectedContact.getCreationDate());
        contactModificationDate.setText(selectedContact.getModificationDate());
        contactCreationUser.setText(selectedContact.getCreationUserName());
        contactModificationUser.setText(selectedContact.getModificationUserName());
        contactCompanyName.setText(selectedContact.getCompanyName());
        contactActive.setChecked(selectedContact.isActivate());

        return view;
    }

    private void saveContact(){

        String firstName = contactFirstName.getText().toString();
        String lastName = contactLastName.getText().toString();
        String title = contactTitle.getText().toString();
        String email = contactEmail.getText().toString();
        String telNumber = contactTelNumber.getText().toString();

        firstName = firstName.trim();
        lastName = lastName.trim();
        title = title.trim();
        email = email.trim();
        telNumber = telNumber.trim();

        if(firstName.isEmpty() || lastName.isEmpty() || title.isEmpty() || email.isEmpty() || telNumber.isEmpty()){
            Toast.makeText(getActivity(), "Preencher todos os campos!", Toast.LENGTH_LONG).show();
            return;
        }

        Operations ops = new Operations(this, CODE_EDIT_CONTACT);
        ops.editContact(selectedContact.getId(), firstName, lastName, title, email, telNumber, loggedUser.getId());

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onOperationSuccess(Object returnObject, int operationCode) {

        Toast.makeText(getActivity(), "Contact edited successfully!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onOperationFail(Object returnObject, int operationCode) {

    }

    @Override
    public void onOperationError(Object returnObject, int operationCode) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
