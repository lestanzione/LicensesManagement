package com.stanzione.licensesmanagement.ui;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.ProgressBar;
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
 * {@link CompanyListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CompanyListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompanyListFragment extends Fragment implements Operations.OperationsCallback, CompanyRecyclerAdapter.OnCompanyListener {

    private static final int CODE_LIST_COMPANY = 1;
    private static final int CODE_REMOVE_COMPANY = 2;

    private static final String ARG_LOGGED_USER = "loggedUser";

    private UserAccess loggedUser;

    private static final String TAG = CompanyListFragment.class.getSimpleName();

    private Button newCompanyButton;
    private RecyclerView companyRecyclerView;
    private ArrayList<Company> companyArrayList;
    private ProgressBar progressBar;

    private CompanyRecyclerAdapter companyRecyclerAdapter;

    private int positionToDelete;

    private OnFragmentInteractionListener mListener;

    public CompanyListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedUser Parameter 1.
     * @return A new instance of fragment CompanyListFragment.
     */
    public static CompanyListFragment newInstance(UserAccess loggedUser) {
        CompanyListFragment fragment = new CompanyListFragment();
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
        View view = inflater.inflate(R.layout.fragment_company_list, container, false);

        setHasOptionsMenu(true);

        newCompanyButton = (Button) view.findViewById(R.id.newCompanyButton);
        companyRecyclerView = (RecyclerView) view.findViewById(R.id.companyRecyclerView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        newCompanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateCompanyFragment();
            }
        });

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

        Operations ops = new Operations(this, CODE_LIST_COMPANY);
        ops.getCompanyList();

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
                companyRecyclerAdapter.setShowEdit(!companyRecyclerAdapter.getShowEdit());
                companyRecyclerAdapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showCreateCompanyFragment(){

        CreateCompanyFragment createCompanyFragment = CreateCompanyFragment.newInstance(loggedUser);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.mainBody, createCompanyFragment);
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

        if(operationCode == CODE_LIST_COMPANY) {

            companyArrayList = (ArrayList<Company>) returnObject;

            companyRecyclerAdapter = new CompanyRecyclerAdapter(getContext(), companyArrayList, loggedUser, this);

            companyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            companyRecyclerView.setAdapter(companyRecyclerAdapter);

        }
        else if(operationCode == CODE_REMOVE_COMPANY){
            Toast.makeText(getContext(), "Company removed successfully!", Toast.LENGTH_LONG).show();

            companyArrayList.remove(positionToDelete);
            companyRecyclerAdapter.notifyDataSetChanged();
        }

        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onOperationFail(Object returnObject, int operationCode) {
        Log.d(TAG, "Operation fail!");

        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onOperationError(Object returnObject, int operationCode) {

        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onCompanySelected(int position) {

        positionToDelete = position;

        Company selectedCompany = companyArrayList.get(positionToDelete);

        Log.d(TAG, "selectedCompany ID: " + selectedCompany.getId());

        CompanyDetailsFragment companyDetailsFragment = CompanyDetailsFragment.newInstance(loggedUser, selectedCompany);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.mainBody, companyDetailsFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }

    @Override
    public void onCompanyToEdit(int position) {

        Company selectedCompany = companyArrayList.get(position);

        Log.d(TAG, "selectedCompany ID: " + selectedCompany.getId());

        CompanyEditFragment companyEditFragment = CompanyEditFragment.newInstance(loggedUser, selectedCompany);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.mainBody, companyEditFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }

    @Override
    public void onCompanyToDelete(int position) {

        Company selectedCompany = companyArrayList.get(position);

        Log.d(TAG, "selectedCompany ID: " + selectedCompany.getId());

        if(!Utils.isOnline(this.getActivity())){
            Toast.makeText(this.getActivity(), "There is no internet connection!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        Operations ops = new Operations(this, CODE_REMOVE_COMPANY);
        ops.removeCompany(selectedCompany.getId(), loggedUser.getId());

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
