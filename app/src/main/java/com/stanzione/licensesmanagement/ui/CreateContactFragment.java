package com.stanzione.licensesmanagement.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.stanzione.licensesmanagement.Operations;
import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.helper.Utils;
import com.stanzione.licensesmanagement.model.Company;
import com.stanzione.licensesmanagement.model.UserAccess;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateContactFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateContactFragment extends Fragment implements Operations.OperationsCallback{

    private static final int CODE_LIST_COMPANY = 1;
    private static final int CODE_CREATE_CONTACT = 2;

    private static final String ARG_LOGGED_USER = "loggedUser";

    private UserAccess loggedUser;
    private ArrayList<Company> companyArrayList;

    private static final String TAG = CreateContactFragment.class.getSimpleName();

    private EditText contactFirstNameEditText;
    private EditText contactLastNameEditText;
    private EditText contactTitleEditText;
    private EditText contactEmailEditText;
    private EditText contactTelNumberEditText;
    private Spinner contactCompanyNameSpinner;
    private Button saveContactButton;
    private ProgressBar progressBar;

    private OnFragmentInteractionListener mListener;

    public CreateContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedUser Parameter 1.
     * @return A new instance of fragment CreateContactFragment.
     */
    public static CreateContactFragment newInstance(UserAccess loggedUser) {
        CreateContactFragment fragment = new CreateContactFragment();
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
        View view = inflater.inflate(R.layout.fragment_create_contact, container, false);

        contactFirstNameEditText = (EditText) view.findViewById(R.id.createContactFirstNameEditText);
        contactLastNameEditText = (EditText) view.findViewById(R.id.createContactLastNameEditText);
        contactTitleEditText = (EditText) view.findViewById(R.id.createContactTitleEditText);
        contactEmailEditText = (EditText) view.findViewById(R.id.createContactEmailEditText);
        contactTelNumberEditText = (EditText) view.findViewById(R.id.createContactTelNumberEditText);
        contactCompanyNameSpinner = (Spinner) view.findViewById(R.id.createContactCompanyNameSpinner);
        saveContactButton = (Button) view.findViewById(R.id.createContactSaveButton);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        saveContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContact();
            }
        });

        return view;
    }

    private void saveContact(){

        String contactFirstName = contactFirstNameEditText.getText().toString();
        String contactLastName = contactLastNameEditText.getText().toString();
        String contactTitle = contactTitleEditText.getText().toString();
        String contactEmail = contactEmailEditText.getText().toString();
        String contactTelNumber = contactTelNumberEditText.getText().toString();
        int contactCompanyId = companyArrayList.get(contactCompanyNameSpinner.getSelectedItemPosition()).getId();

        contactFirstName = contactFirstName.trim();
        contactLastName = contactLastName.trim();
        contactTitle = contactTitle.trim();
        contactEmail = contactEmail.trim();
        contactTelNumber = contactTelNumber.trim();

        Log.d(TAG, "contactFirstName: " + contactFirstName);
        Log.d(TAG, "contactLastName: " + contactLastName);
        Log.d(TAG, "contactTitle: " + contactTitle);
        Log.d(TAG, "contactEmail: " + contactEmail);
        Log.d(TAG, "contactTelNumber: " + contactTelNumber);
        Log.d(TAG, "contactCompanyId: " + contactCompanyId);

        if(contactFirstName.isEmpty() || contactLastName.isEmpty() || contactTitle.isEmpty() ||
                contactEmail.isEmpty() || contactTelNumber.isEmpty()){
            Toast.makeText(getActivity(), "Preencher todos os campos!", Toast.LENGTH_LONG).show();
            return;
        }

        if(!Utils.isOnline(this.getActivity())){
            Toast.makeText(this.getActivity(), "There is no internet connection!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        Operations ops = new Operations(this, CODE_CREATE_CONTACT);
        ops.addContact(contactFirstName, contactLastName, contactTitle, contactEmail, contactTelNumber, contactCompanyId, loggedUser.getId());

    }

    @Override
    public void onStart() {

        super.onStart();

        if(!Utils.isOnline(this.getActivity())){
            Toast.makeText(this.getActivity(), "There is no internet connection!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        Operations ops = new Operations(this, CODE_LIST_COMPANY);
        ops.getCompanyList();

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

        Log.d(TAG, "Operation success!");

        if(operationCode == CODE_LIST_COMPANY) {

            companyArrayList = (ArrayList<Company>) returnObject;
            ArrayList<String> companyNameArrayList = new ArrayList<String>();

            for (int i = 0; i < companyArrayList.size(); i++) {
                companyNameArrayList.add(companyArrayList.get(i).getName());
            }

            ArrayAdapter<String> companyAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, companyNameArrayList);
            companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            contactCompanyNameSpinner.setAdapter(companyAdapter);

        }
        else if(operationCode == CODE_CREATE_CONTACT){

            Toast.makeText(getActivity(), "Contact created successfully!", Toast.LENGTH_LONG).show();

        }

        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onOperationFail(Object returnObject, int operationCode) {

        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onOperationError(Object returnObject, int operationCode) {

        progressBar.setVisibility(View.INVISIBLE);

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
