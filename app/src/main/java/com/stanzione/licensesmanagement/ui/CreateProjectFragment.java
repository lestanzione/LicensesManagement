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
 * {@link CreateProjectFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateProjectFragment extends Fragment implements Operations.OperationsCallback{

    private static final int CODE_LIST_COMPANY = 1;
    private static final int CODE_CREATE_PROJECT = 2;

    private static final String ARG_LOGGED_USER = "loggedUser";

    private UserAccess loggedUser;
    private ArrayList<Company> companyArrayList;

    private static final String TAG = CreateProjectFragment.class.getSimpleName();

    private EditText projectNameEditText;
    private Spinner projectCompanyNameSpinner;
    private Button saveProjectButton;
    private ProgressBar progressBar;

    private OnFragmentInteractionListener mListener;

    public CreateProjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedUser Parameter 1.
     * @return A new instance of fragment CreateProjectFragment.
     */
    public static CreateProjectFragment newInstance(UserAccess loggedUser) {
        CreateProjectFragment fragment = new CreateProjectFragment();
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
        View view = inflater.inflate(R.layout.fragment_create_project, container, false);

        projectNameEditText = (EditText) view.findViewById(R.id.createProjectNameEditText);
        projectCompanyNameSpinner = (Spinner) view.findViewById(R.id.createProjectCompanyNameSpinner);
        saveProjectButton = (Button) view.findViewById(R.id.createProjectSaveButton);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        saveProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProject();
            }
        });

        return view;
    }

    private void saveProject(){

        String projectName = projectNameEditText.getText().toString();
        int projectCompanyId = companyArrayList.get(projectCompanyNameSpinner.getSelectedItemPosition()).getId();

        projectName = projectName.trim();

        Log.d(TAG, "projectName: " + projectName);
        Log.d(TAG, "projectCompanyId: " + projectCompanyId);

        if(projectName.isEmpty()){
            Toast.makeText(getActivity(), "Preencher todos os campos!", Toast.LENGTH_LONG).show();
            return;
        }

        if(!Utils.isOnline(this.getActivity())){
            Toast.makeText(this.getActivity(), "There is no internet connection!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        Operations ops = new Operations(this, CODE_CREATE_PROJECT);
        ops.addProject(projectName, projectCompanyId, loggedUser.getId());

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

            projectCompanyNameSpinner.setAdapter(companyAdapter);

        }
        else if(operationCode == CODE_CREATE_PROJECT){

            Toast.makeText(getActivity(), "Project created successfully!", Toast.LENGTH_LONG).show();

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
