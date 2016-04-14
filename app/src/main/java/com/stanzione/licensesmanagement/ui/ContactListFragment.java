package com.stanzione.licensesmanagement.ui;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.stanzione.licensesmanagement.Operations;
import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.model.Contact;
import com.stanzione.licensesmanagement.model.Project;
import com.stanzione.licensesmanagement.model.Software;
import com.stanzione.licensesmanagement.model.UserAccess;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactListFragment extends Fragment implements Operations.OperationsCallback, ContactRecyclerAdapter.OnContactListener{

    private static final int CODE_LIST_CONTACT = 1;
    private static final int CODE_REMOVE_CONTACT = 2;

    private static final String ARG_LOGGED_USER = "loggedUser";

    private UserAccess loggedUser;

    private static final String TAG = ContactListFragment.class.getSimpleName();

    private Button newContactButton;
    private RecyclerView contactRecyclerView;
    private ArrayList<Contact> contactArrayList;

    private OnFragmentInteractionListener mListener;

    public ContactListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedUser Parameter 1.
     * @return A new instance of fragment ContactListFragment.
     */
    public static ContactListFragment newInstance(UserAccess loggedUser) {
        ContactListFragment fragment = new ContactListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOGGED_USER, loggedUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loggedUser = (UserAccess) getArguments().getSerializable(ARG_LOGGED_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);

        newContactButton = (Button) view.findViewById(R.id.newContactButton);
        contactRecyclerView = (RecyclerView) view.findViewById(R.id.contactRecyclerView);

        newContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateContactFragment();
            }
        });

        return view;
    }

    private void showCreateContactFragment(){

        CreateContactFragment createContactFragment = CreateContactFragment.newInstance(loggedUser);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.mainBody, createContactFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }

    @Override
    public void onStart() {

        super.onStart();
        Operations ops = new Operations(this, CODE_LIST_CONTACT);
        ops.getContactList();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onOperationSuccess(Object returnObject, int operationCode) {

        Log.d(TAG, "Operation success!");

        if(operationCode == CODE_LIST_CONTACT) {

            contactArrayList = (ArrayList<Contact>) returnObject;

            contactRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            contactRecyclerView.setAdapter(new ContactRecyclerAdapter(getContext(), contactArrayList, loggedUser, this));

        }
        else if(operationCode == CODE_REMOVE_CONTACT){

            Toast.makeText(getContext(), "Contact removed successfully!", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onOperationFail(Object returnObject, int operationCode) {

    }

    @Override
    public void onOperationError(Object returnObject, int operationCode) {

    }

    @Override
    public void onContactSelected(int position) {

        Contact selectedContact = contactArrayList.get(position);

        Log.d(TAG, "selectedContact ID: " + selectedContact.getId());

        ContactDetailsFragment contactDetailsFragment = ContactDetailsFragment.newInstance(loggedUser, selectedContact);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.mainBody, contactDetailsFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }

    @Override
    public void onContactToDelete(int position) {

        Contact selectedContact = contactArrayList.get(position);

        Log.d(TAG, "selectedContact ID: " + selectedContact.getId());

        Operations ops = new Operations(this, CODE_REMOVE_CONTACT);
        ops.removeContact(selectedContact.getId(), loggedUser.getId());

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
