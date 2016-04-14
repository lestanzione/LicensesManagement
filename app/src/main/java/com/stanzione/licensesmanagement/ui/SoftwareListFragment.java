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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.stanzione.licensesmanagement.Operations;
import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.model.Project;
import com.stanzione.licensesmanagement.model.Software;
import com.stanzione.licensesmanagement.model.UserAccess;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SoftwareListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SoftwareListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class SoftwareListFragment extends Fragment implements Operations.OperationsCallback, SoftwareRecyclerAdapter.OnSoftwareListener{

    private static final int CODE_LIST_SOFTWARE = 1;
    private static final int CODE_REMOVE_SOFTWARE = 2;

    private static final String ARG_LOGGED_USER = "loggedUser";

    private UserAccess loggedUser;

    private static final String TAG = SoftwareListFragment.class.getSimpleName();

    private Button newSoftwareButton;
    private RecyclerView softwareRecyclerView;
    private ArrayList<Software> softwareArrayList;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedUser Parameter 1.
     * @return A new instance of fragment SoftwareListFragment.
     */
    public static SoftwareListFragment newInstance(UserAccess loggedUser) {
        SoftwareListFragment fragment = new SoftwareListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOGGED_USER, loggedUser);
        fragment.setArguments(args);
        return fragment;
    }
    public SoftwareListFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_software_list, container, false);

        newSoftwareButton = (Button) view.findViewById(R.id.newSoftwareButton);
        softwareRecyclerView = (RecyclerView) view.findViewById(R.id.softwareRecyclerView);

        newSoftwareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateSoftwareFragment();
            }
        });

        return view;
    }

    @Override
    public void onStart() {

        super.onStart();
        Operations ops = new Operations(this, CODE_LIST_SOFTWARE);
        ops.getSoftwareList();

    }

    private void showCreateSoftwareFragment(){

        CreateSoftwareFragment createSoftwareFragment = CreateSoftwareFragment.newInstance(loggedUser);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.mainBody, createSoftwareFragment);
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

        softwareArrayList = (ArrayList<Software>) returnObject;

        softwareRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        softwareRecyclerView.setAdapter(new SoftwareRecyclerAdapter(getActivity(), softwareArrayList, loggedUser, this));

    }

    @Override
    public void onOperationFail(Object returnObject, int operationCode) {

    }

    @Override
    public void onOperationError(Object returnObject, int operationCode) {

    }

    @Override
    public void onSoftwareSelected(int position) {

        Software selectedSoftware = softwareArrayList.get(position);

        Log.d(TAG, "selectedSoftware ID: " + selectedSoftware.getId());

        SoftwareDetailsFragment softwareDetailsFragment = SoftwareDetailsFragment.newInstance(loggedUser, selectedSoftware);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.mainBody, softwareDetailsFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }

    @Override
    public void onSoftwareToDelete(int position) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
