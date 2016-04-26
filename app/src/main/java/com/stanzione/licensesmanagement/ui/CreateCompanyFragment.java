package com.stanzione.licensesmanagement.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.stanzione.licensesmanagement.Operations;
import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.helper.Utils;
import com.stanzione.licensesmanagement.model.UserAccess;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateCompanyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateCompanyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateCompanyFragment extends Fragment implements Operations.OperationsCallback {

    private static final int CODE_CREATE_COMPANY = 1;

    private static final String ARG_LOGGED_USER = "loggedUser";

    private UserAccess loggedUser;

    private EditText companyNameEditText;
    private EditText companyAddressEditText;
    private Button saveCompanyButton;
    private ProgressBar progressBar;

    private OnFragmentInteractionListener mListener;

    public CreateCompanyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedUser Parameter 1.
     * @return A new instance of fragment CreateCompanyFragment.
     */
    public static CreateCompanyFragment newInstance(UserAccess loggedUser) {
        CreateCompanyFragment fragment = new CreateCompanyFragment();
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
        View view = inflater.inflate(R.layout.fragment_create_company, container, false);

        companyNameEditText = (EditText) view.findViewById(R.id.createCompanyNameEditText);
        companyAddressEditText = (EditText) view.findViewById(R.id.createCompanyAddressEditText);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        saveCompanyButton = (Button) view.findViewById(R.id.createCompanySaveButton);
        saveCompanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCompany();
            }
        });

        return view;
    }

    private void saveCompany(){

        String companyName = companyNameEditText.getText().toString();
        String companyAddress = companyAddressEditText.getText().toString();

        companyName = companyName.trim();
        companyAddress = companyAddress.trim();

        if(companyName.isEmpty() || companyAddress.isEmpty()){
            Toast.makeText(getActivity(), "Preencher todos os campos!", Toast.LENGTH_LONG).show();
            return;
        }

        if(!Utils.isOnline(this.getActivity())){
            Toast.makeText(this.getActivity(), "There is no internet connection!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        Operations ops = new Operations(this, CODE_CREATE_COMPANY);
        ops.addCompany(companyName, companyAddress, loggedUser.getId());

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

        Toast.makeText(getActivity(), "Company added successfully!", Toast.LENGTH_LONG).show();

        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onOperationFail(Object returnObject, int operationCode) {

        Toast.makeText(getActivity(), "Failed to add company!", Toast.LENGTH_LONG).show();

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
