package com.stanzione.licensesmanagement.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.stanzione.licensesmanagement.Operations;
import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.helper.Utils;
import com.stanzione.licensesmanagement.model.Company;
import com.stanzione.licensesmanagement.model.Project;
import com.stanzione.licensesmanagement.model.UserAccess;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CompanyRelatedProjectsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CompanyRelatedProjectsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompanyRelatedProjectsFragment extends Fragment implements Operations.OperationsCallback, ProjectRecyclerAdapter.OnProjectListener{

    private static final int CODE_LIST_PROJECTS_FROM_COMPANY = 1;

    private static final String ARG_LOGGED_USER = "loggedUser";
    private static final String ARG_SELECTED_COMPANY = "selectedCompany";

    private UserAccess loggedUser;
    private Company selectedCompany;

    private static final String TAG = CompanyRelatedProjectsFragment.class.getSimpleName();

    private RecyclerView companyRelatedProjectRecyclerView;
    private ProgressBar progressBar;

    private OnFragmentInteractionListener mListener;

    public CompanyRelatedProjectsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedUser Parameter 1.
     * @param selectedCompany Parameter 2.
     * @return A new instance of fragment CompanyRelatedProjectsFragment.
     */
    public static CompanyRelatedProjectsFragment newInstance(UserAccess loggedUser, Company selectedCompany) {
        CompanyRelatedProjectsFragment fragment = new CompanyRelatedProjectsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOGGED_USER, loggedUser);
        args.putSerializable(ARG_SELECTED_COMPANY, selectedCompany);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loggedUser = (UserAccess) getArguments().getSerializable(ARG_LOGGED_USER);
            selectedCompany = (Company) getArguments().getSerializable(ARG_SELECTED_COMPANY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_company_related_projects, container, false);

        companyRelatedProjectRecyclerView = (RecyclerView) view.findViewById(R.id.companyRelatedProjectRecyclerView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public void onStart() {

        super.onStart();

        if(!Utils.isOnline(this.getActivity())){
            Toast.makeText(this.getActivity(), "There is no internet connection!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        Operations ops = new Operations(this, CODE_LIST_PROJECTS_FROM_COMPANY);
        ops.getProjectListFromCompany(selectedCompany.getId());

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

        ArrayList<Project> projectArrayList = (ArrayList<Project>) returnObject;

        companyRelatedProjectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        companyRelatedProjectRecyclerView.setAdapter(new ProjectRecyclerAdapter(getContext(), projectArrayList, loggedUser, this));

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

    }

    @Override
    public void onProjectToEdit(int position) {

    }

    @Override
    public void onProjectToDelete(int position) {

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
