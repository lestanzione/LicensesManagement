package com.stanzione.licensesmanagement.ui;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.stanzione.licensesmanagement.Operations;
import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.model.Project;
import com.stanzione.licensesmanagement.model.ProjectSoftware;
import com.stanzione.licensesmanagement.model.UserAccess;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProjectRelatedSoftwaresFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProjectRelatedSoftwaresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectRelatedSoftwaresFragment extends Fragment implements Operations.OperationsCallback, ProjectSoftwareRecyclerAdapter.OnProjectSoftwareListener{

    private static final int CODE_LIST_SOFTWARES_FROM_PROJECT = 1;

    private static final String ARG_LOGGED_USER = "loggedUser";
    private static final String ARG_SELECTED_PROJECT = "selectedProject";

    private UserAccess loggedUser;
    private Project selectedProject;

    private static final String TAG = ProjectDetailsFragment.class.getSimpleName();

    private RecyclerView projectRelatedSoftwareRecyclerView;

    private OnFragmentInteractionListener mListener;

    public ProjectRelatedSoftwaresFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedUser Parameter 1.
     * @param selectedProject Parameter 2.
     * @return A new instance of fragment ProjectRelatedSoftwaresFragment.
     */
    public static ProjectRelatedSoftwaresFragment newInstance(UserAccess loggedUser, Project selectedProject) {
        ProjectRelatedSoftwaresFragment fragment = new ProjectRelatedSoftwaresFragment();
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
        View view = inflater.inflate(R.layout.fragment_project_related_softwares, container, false);

        projectRelatedSoftwareRecyclerView = (RecyclerView) view.findViewById(R.id.projectRelatedSoftwareRecyclerView);

        return view;
    }

    @Override
    public void onStart() {

        super.onStart();
        Operations ops = new Operations(this, CODE_LIST_SOFTWARES_FROM_PROJECT);
        ops.getProjectSoftwareListFromProject(selectedProject.getId());

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

        ArrayList<ProjectSoftware> projectSoftwareArrayList = (ArrayList<ProjectSoftware>) returnObject;

        projectRelatedSoftwareRecyclerView.setAdapter(new ProjectSoftwareRecyclerAdapter(getActivity().getApplicationContext(), projectSoftwareArrayList, loggedUser, this));

    }

    @Override
    public void onOperationFail(Object returnObject, int operationCode) {

    }

    @Override
    public void onOperationError(Object returnObject, int operationCode) {

    }

    @Override
    public void onProjectSoftwareSelected(int position) {

    }

    @Override
    public void onProjectSoftwareToDelete(int position) {

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
