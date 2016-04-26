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
import com.stanzione.licensesmanagement.model.Contact;
import com.stanzione.licensesmanagement.model.UserAccess;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CompanyRelatedContactsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CompanyRelatedContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompanyRelatedContactsFragment extends Fragment implements Operations.OperationsCallback, ContactRecyclerAdapter.OnContactListener{

    private static final int CODE_LIST_CONTACTS_FROM_COMPANY = 1;

    private static final String ARG_LOGGED_USER = "loggedUser";
    private static final String ARG_SELECTED_COMPANY = "selectedCompany";

    private UserAccess loggedUser;
    private Company selectedCompany;

    private static final String TAG = CompanyRelatedContactsFragment.class.getSimpleName();

    private RecyclerView companyRelatedContactRecyclerView;
    private ProgressBar progressBar;

    private OnFragmentInteractionListener mListener;

    public CompanyRelatedContactsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedUser Parameter 1.
     * @param selectedCompany Parameter 2.
     * @return A new instance of fragment CompanyRelatedContactsFragment.
     */
    public static CompanyRelatedContactsFragment newInstance(UserAccess loggedUser, Company selectedCompany) {
        CompanyRelatedContactsFragment fragment = new CompanyRelatedContactsFragment();
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
        View view = inflater.inflate(R.layout.fragment_company_related_contacts, container, false);

        companyRelatedContactRecyclerView = (RecyclerView) view.findViewById(R.id.companyRelatedContactRecyclerView);
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

        Operations ops = new Operations(this, CODE_LIST_CONTACTS_FROM_COMPANY);
        ops.getContactListFromCompany(selectedCompany.getId());

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

        ArrayList<Contact> contactArrayList = (ArrayList<Contact>) returnObject;

        companyRelatedContactRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        companyRelatedContactRecyclerView.setAdapter(new ContactRecyclerAdapter(getContext(), contactArrayList, loggedUser, this));

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
    public void onContactSelected(int position) {

    }

    @Override
    public void onContactToEdit(int position) {

    }

    @Override
    public void onContactToDelete(int position) {

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
