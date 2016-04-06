package com.stanzione.licensesmanagement.ui;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.model.Contact;
import com.stanzione.licensesmanagement.model.UserAccess;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactRelatedDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactRelatedDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactRelatedDetailsFragment extends Fragment {

    private static final String ARG_LOGGED_USER = "loggedUser";
    private static final String ARG_SELECTED_CONTACT = "selectedContact";

    private UserAccess loggedUser;
    private Contact selectedContact;

    private static final String TAG = ContactRelatedDetailsFragment.class.getSimpleName();

    private TextView contactId;
    private TextView contactFirstName;
    private TextView contactLastName;
    private TextView contactTitle;
    private TextView contactEmail;
    private TextView contactTelNumber;
    private TextView contactCreationDate;
    private TextView contactModificationDate;
    private TextView contactCreationUser;
    private TextView contactModificationUser;
    private TextView contactCompanyName;
    private CheckBox contactActive;
    private Button contactEditButton;

    private OnFragmentInteractionListener mListener;

    public ContactRelatedDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedUser Parameter 1.
     * @param selectedContact Parameter 2.
     * @return A new instance of fragment ContactRelatedDetailsFragment.
     */
    public static ContactRelatedDetailsFragment newInstance(UserAccess loggedUser, Contact selectedContact) {
        ContactRelatedDetailsFragment fragment = new ContactRelatedDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_contact_related_details, container, false);

        contactId = (TextView) view.findViewById(R.id.contactDetailId);
        contactFirstName = (TextView) view.findViewById(R.id.contactDetailFirstName);
        contactLastName = (TextView) view.findViewById(R.id.contactDetailLastName);
        contactTitle = (TextView) view.findViewById(R.id.contactDetailTitle);
        contactEmail = (TextView) view.findViewById(R.id.contactDetailEmail);
        contactTelNumber = (TextView) view.findViewById(R.id.contactDetailTelNumber);
        contactCreationDate = (TextView) view.findViewById(R.id.contactDetailCreationDate);
        contactModificationDate = (TextView) view.findViewById(R.id.contactDetailModificationDate);
        contactCreationUser = (TextView) view.findViewById(R.id.contactDetailCreationUser);
        contactModificationUser = (TextView) view.findViewById(R.id.contactDetailModificationUser);
        contactCompanyName = (TextView) view.findViewById(R.id.contactDetailCompanyName);
        contactActive = (CheckBox) view.findViewById(R.id.contactDetailActive);
        contactEditButton = (Button) view.findViewById(R.id.contactDetailEditButton);

        contactEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditContactFragment();
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

    private void showEditContactFragment(){

        ContactEditFragment contactEditFragment = ContactEditFragment.newInstance(loggedUser, selectedContact);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.mainBody, contactEditFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

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
