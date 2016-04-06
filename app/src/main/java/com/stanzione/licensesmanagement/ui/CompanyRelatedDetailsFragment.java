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
import com.stanzione.licensesmanagement.model.Company;
import com.stanzione.licensesmanagement.model.UserAccess;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CompanyRelatedDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CompanyRelatedDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompanyRelatedDetailsFragment extends Fragment {

    private static final String ARG_LOGGED_USER = "loggedUser";
    private static final String ARG_SELECTED_COMPANY = "selectedCompany";

    private UserAccess loggedUser;
    private Company selectedCompany;

    private OnFragmentInteractionListener mListener;

    private TextView companyId;
    private TextView companyName;
    private TextView companyAddress;
    private TextView companyCreationDate;
    private TextView companyModificationDate;
    private TextView companyCreationUser;
    private TextView companyModificationUser;
    private CheckBox companyActive;
    private Button companyEditButton;

    public CompanyRelatedDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedUser Parameter 1.
     * @param selectedCompany Parameter 2.
     * @return A new instance of fragment CompanyRelatedDetailsFragment.
     */
    public static CompanyRelatedDetailsFragment newInstance(UserAccess loggedUser, Company selectedCompany) {
        CompanyRelatedDetailsFragment fragment = new CompanyRelatedDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_company_related_details, container, false);

        companyId = (TextView) view.findViewById(R.id.companyDetailId);
        companyName = (TextView) view.findViewById(R.id.companyDetailName);
        companyAddress = (TextView) view.findViewById(R.id.companyDetailAddress);
        companyCreationDate = (TextView) view.findViewById(R.id.companyDetailCreationDate);
        companyModificationDate = (TextView) view.findViewById(R.id.companyDetailModificationDate);
        companyCreationUser = (TextView) view.findViewById(R.id.companyDetailCreationUser);
        companyModificationUser = (TextView) view.findViewById(R.id.companyDetailModificationUser);
        companyActive = (CheckBox) view.findViewById(R.id.companyDetailActive);
        companyEditButton = (Button) view.findViewById(R.id.companyDetailEditButton);

        companyEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditCompanyFragment();
            }
        });

        companyId.setText(String.valueOf(selectedCompany.getId()));
        companyName.setText(selectedCompany.getName());
        companyAddress.setText(selectedCompany.getAddress());
        companyCreationDate.setText(selectedCompany.getCreationDate());
        companyModificationDate.setText(selectedCompany.getModificationDate());
        companyCreationUser.setText(selectedCompany.getCreationUserName());
        companyModificationUser.setText(selectedCompany.getModificationUserName());
        companyActive.setChecked(selectedCompany.isActivate());

        return view;
    }

    private void showEditCompanyFragment(){

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
