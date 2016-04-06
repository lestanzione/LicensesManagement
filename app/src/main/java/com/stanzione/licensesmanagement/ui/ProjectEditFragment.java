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
import com.stanzione.licensesmanagement.model.Project;
import com.stanzione.licensesmanagement.model.UserAccess;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProjectEditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProjectEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectEditFragment extends Fragment implements Operations.OperationsCallback{

    private static final int CODE_EDIT_PROJECT = 1;

    private static final String ARG_LOGGED_USER = "loggedUser";
    private static final String ARG_SELECTED_PROJECT = "selectedProject";

    private UserAccess loggedUser;
    private Project selectedProject;

    private static final String TAG = ProjectEditFragment.class.getSimpleName();

    private TextView projectId;
    private EditText projectName;
    private EditText projectStartDate;
    private EditText projectEndDate;
    private TextView projectCreationDate;
    private TextView projectModificationDate;
    private TextView projectCreationUser;
    private TextView projectModificationUser;
    private TextView projectCompanyName;
    private CheckBox projectActive;
    private Button saveProjectButton;

    private OnFragmentInteractionListener mListener;

    public ProjectEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedUser Parameter 1.
     * @param selectedProject Parameter 2.
     * @return A new instance of fragment ProjectEditFragment.
     */
    public static ProjectEditFragment newInstance(UserAccess loggedUser, Project selectedProject) {
        ProjectEditFragment fragment = new ProjectEditFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOGGED_USER, loggedUser);
        args.putSerializable(ARG_SELECTED_PROJECT, selectedProject);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loggedUser = (UserAccess) getArguments().getSerializable(ARG_LOGGED_USER);
            selectedProject = (Project) getArguments().getSerializable(ARG_SELECTED_PROJECT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project_edit, container, false);

        projectId = (TextView) view.findViewById(R.id.projectEditId);
        projectName = (EditText) view.findViewById(R.id.projectEditName);
        projectStartDate = (EditText) view.findViewById(R.id.projectEditStartDate);
        projectEndDate = (EditText) view.findViewById(R.id.projectEditEndDate);
        projectCreationDate = (TextView) view.findViewById(R.id.projectEditCreationDate);
        projectModificationDate = (TextView) view.findViewById(R.id.projectEditModificationDate);
        projectCreationUser = (TextView) view.findViewById(R.id.projectEditCreationUser);
        projectModificationUser = (TextView) view.findViewById(R.id.projectEditModificationUser);
        projectCompanyName = (TextView) view.findViewById(R.id.projectEditCompanyName);
        projectActive = (CheckBox) view.findViewById(R.id.projectEditActive);
        saveProjectButton = (Button) view.findViewById(R.id.projectEditSaveButton);

        saveProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProject();
            }
        });

        projectId.setText(String.valueOf(selectedProject.getId()));
        projectName.setText(selectedProject.getName());
        projectStartDate.setText(selectedProject.getStartDate());
        projectEndDate.setText(selectedProject.getEndDate());
        projectCreationDate.setText(selectedProject.getCreationDate());
        projectModificationDate.setText(selectedProject.getModificationDate());
        projectCreationUser.setText(selectedProject.getCreationUserName());
        projectModificationUser.setText(selectedProject.getModificationUserName());
        projectCompanyName.setText(selectedProject.getCompanyName());
        projectActive.setChecked(selectedProject.isActivate());

        return view;
    }

    private void saveProject(){

        String name = projectName.getText().toString();
        String startDate = projectStartDate.getText().toString();
        String endDate = projectEndDate.getText().toString();

        name = name.trim();
        startDate = startDate.trim();
        endDate = endDate.trim();

        if(name.isEmpty()){
            Toast.makeText(getActivity(), "Nome não pode estar em branco!", Toast.LENGTH_LONG).show();
            return;
        }

        Operations ops = new Operations(this, CODE_EDIT_PROJECT);
        ops.editProject(selectedProject.getId(), name, startDate, endDate, loggedUser.getId());

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

        Toast.makeText(getActivity(), "Project edited successfully!", Toast.LENGTH_LONG).show();

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
