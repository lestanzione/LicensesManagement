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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.stanzione.licensesmanagement.Operations;
import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.model.Company;
import com.stanzione.licensesmanagement.model.Project;
import com.stanzione.licensesmanagement.model.UserAccess;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProjectListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProjectListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectListFragment extends Fragment implements Operations.OperationsCallback, ProjectRecyclerAdapter.OnProjectListener{

    private static final int CODE_LIST_PROJECT = 1;
    private static final int CODE_REMOVE_PROJECT = 2;

    private static final String ARG_LOGGED_USER = "loggedUser";

    private UserAccess loggedUser;

    private static final String TAG = ProjectListFragment.class.getSimpleName();

    private Button newProjectButton;
    private RecyclerView projectRecyclerView;
    private ArrayList<Project> projectArrayList;
    private ProgressBar progressBar;

    private ProjectRecyclerAdapter projectRecyclerAdapter;

    private int positionToDelete;

    private OnFragmentInteractionListener mListener;

    public ProjectListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedUser Parameter 1.
     * @return A new instance of fragment ProjectListFragment.
     */
    public static ProjectListFragment newInstance(UserAccess loggedUser) {
        ProjectListFragment fragment = new ProjectListFragment();
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
        View view = inflater.inflate(R.layout.fragment_project_list, container, false);

        setHasOptionsMenu(true);

        newProjectButton = (Button) view.findViewById(R.id.newProjectButton);
        projectRecyclerView = (RecyclerView) view.findViewById(R.id.projectRecyclerView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        newProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateProjectFragment();
            }
        });

        return view;
    }

    @Override
    public void onStart() {

        super.onStart();
        Operations ops = new Operations(this, CODE_LIST_PROJECT);
        ops.getProjectList();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_company_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menu_company_manage:
                projectRecyclerAdapter.setShowEdit(!projectRecyclerAdapter.getShowEdit());
                projectRecyclerAdapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showCreateProjectFragment(){

        CreateProjectFragment createProjectFragment = CreateProjectFragment.newInstance(loggedUser);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.mainBody, createProjectFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

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

        if(operationCode == CODE_LIST_PROJECT) {

            projectArrayList = (ArrayList<Project>) returnObject;

            projectRecyclerAdapter = new ProjectRecyclerAdapter(getContext(), projectArrayList, loggedUser, this);

            projectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            projectRecyclerView.setAdapter(projectRecyclerAdapter);

        }
        else if(operationCode == CODE_REMOVE_PROJECT){
            Toast.makeText(getContext(), "Project removed successfully!", Toast.LENGTH_LONG).show();

            projectArrayList.remove(positionToDelete);
            projectRecyclerAdapter.notifyDataSetChanged();
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

    @Override
    public void onProjectSelected(int position) {

        Project selectedProject = projectArrayList.get(position);

        Log.d(TAG, "selectedProject ID: " + selectedProject.getId());

        ProjectDetailsFragment projectDetailsFragment = ProjectDetailsFragment.newInstance(loggedUser, selectedProject);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.mainBody, projectDetailsFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }

    @Override
    public void onProjectToEdit(int position) {

        Project selectedProject = projectArrayList.get(position);

        Log.d(TAG, "selectedProject ID: " + selectedProject.getId());

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
    public void onProjectToDelete(int position) {

        positionToDelete = position;

        Project selectedProject = projectArrayList.get(positionToDelete);

        Log.d(TAG, "selectedProject ID: " + selectedProject.getId());

        progressBar.setVisibility(View.VISIBLE);

        Operations ops = new Operations(this, CODE_REMOVE_PROJECT);
        ops.removeProject(selectedProject.getId(), loggedUser.getId());

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
