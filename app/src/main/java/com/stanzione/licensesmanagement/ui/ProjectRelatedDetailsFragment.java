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
import com.stanzione.licensesmanagement.model.Project;
import com.stanzione.licensesmanagement.model.UserAccess;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProjectRelatedDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProjectRelatedDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectRelatedDetailsFragment extends Fragment {

    private static final String ARG_LOGGED_USER = "loggedUser";
    private static final String ARG_SELECTED_PROJECT = "selectedProject";

    private UserAccess loggedUser;
    private Project selectedProject;

    private static final String TAG = ProjectDetailsFragment.class.getSimpleName();

    private TextView projectId;
    private TextView projectName;
    private TextView projectStartDate;
    private TextView projectEndDate;
    private TextView projectCreationDate;
    private TextView projectModificationDate;
    private TextView projectCreationUser;
    private TextView projectModificationUser;
    private TextView projectCompanyName;
    private CheckBox projectActive;
    private Button projectEditButton;

    private OnFragmentInteractionListener mListener;

    public ProjectRelatedDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedUser Parameter 1.
     * @param selectedProject Parameter 2.
     * @return A new instance of fragment ProjectRelatedDetailsFragment.
     */
    public static ProjectRelatedDetailsFragment newInstance(UserAccess loggedUser, Project selectedProject) {
        ProjectRelatedDetailsFragment fragment = new ProjectRelatedDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_project_related_details, container, false);

        projectId = (TextView) view.findViewById(R.id.projectDetailId);
        projectName = (TextView) view.findViewById(R.id.projectDetailName);
        projectStartDate = (TextView) view.findViewById(R.id.projectDetailStartDate);
        projectEndDate = (TextView) view.findViewById(R.id.projectDetailEndDate);
        projectCreationDate = (TextView) view.findViewById(R.id.projectDetailCreationDate);
        projectModificationDate = (TextView) view.findViewById(R.id.projectDetailModificationDate);
        projectCreationUser = (TextView) view.findViewById(R.id.projectDetailCreationUser);
        projectModificationUser = (TextView) view.findViewById(R.id.projectDetailModificationUser);
        projectCompanyName = (TextView) view.findViewById(R.id.projectDetailCompanyName);
        projectActive = (CheckBox) view.findViewById(R.id.projectDetailActive);
        projectEditButton = (Button) view.findViewById(R.id.projectDetailEditButton);

        projectEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProjectFragment();
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

    private void showEditProjectFragment(){

        ProjectEditFragment projectEditFragment = ProjectEditFragment.newInstance(loggedUser, selectedProject);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.mainBody, projectEditFragment);
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
