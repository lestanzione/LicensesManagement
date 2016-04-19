package com.stanzione.licensesmanagement.ui;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.stanzione.licensesmanagement.Operations;
import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.model.Company;
import com.stanzione.licensesmanagement.model.UserAccess;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateSoftwareFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateSoftwareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateSoftwareFragment extends Fragment implements Operations.OperationsCallback{

    private static final int CODE_CREATE_SOFTWARE = 1;

    private static final String ARG_LOGGED_USER = "loggedUser";

    private UserAccess loggedUser;

    private static final String TAG = CreateSoftwareFragment.class.getSimpleName();

    private EditText softwareNameEditText;
    private EditText softwareCodeEditText;
    private EditText softwareTypeEditText;
    private Button saveSoftwareButton;
    private ProgressBar progressBar;

    private OnFragmentInteractionListener mListener;

    public CreateSoftwareFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedUser Parameter 1.
     * @return A new instance of fragment CreateSoftwareFragment.
     */
    public static CreateSoftwareFragment newInstance(UserAccess loggedUser) {
        CreateSoftwareFragment fragment = new CreateSoftwareFragment();
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
        View view = inflater.inflate(R.layout.fragment_create_software, container, false);

        softwareNameEditText = (EditText) view.findViewById(R.id.createSoftwareNameEditText);
        softwareCodeEditText = (EditText) view.findViewById(R.id.createSoftwareCodeEditText);
        softwareTypeEditText = (EditText) view.findViewById(R.id.createSoftwareTypeEditText);
        saveSoftwareButton = (Button) view.findViewById(R.id.createSoftwareSaveButton);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        saveSoftwareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSoftware();
            }
        });

        return view;
    }

    private void saveSoftware(){

        String softwareName = softwareNameEditText.getText().toString();
        String softwareCode = softwareCodeEditText.getText().toString();
        String softwareType = softwareTypeEditText.getText().toString();

        softwareName = softwareName.trim();
        softwareCode = softwareCode.trim();
        softwareType = softwareType.trim();

        if(softwareName.isEmpty() || softwareCode.isEmpty() || softwareType.isEmpty()){
            Toast.makeText(getActivity(), "Preencher todos os campos!", Toast.LENGTH_LONG).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        Operations ops = new Operations(this, CODE_CREATE_SOFTWARE);
        ops.addSoftware(softwareName, softwareCode, softwareType, loggedUser.getId());

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

        Toast.makeText(getActivity(), "Software created successfully!", Toast.LENGTH_LONG).show();

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
