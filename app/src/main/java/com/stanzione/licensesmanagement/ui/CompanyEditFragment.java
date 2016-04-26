package com.stanzione.licensesmanagement.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.stanzione.licensesmanagement.Operations;
import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.helper.Utils;
import com.stanzione.licensesmanagement.model.Company;
import com.stanzione.licensesmanagement.model.UserAccess;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CompanyEditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CompanyEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompanyEditFragment extends Fragment implements Operations.OperationsCallback{

    private static final int CODE_EDIT_COMPANY = 1;

    private static final String ARG_LOGGED_USER = "loggedUser";
    private static final String ARG_SELECTED_COMPANY = "selectedCompany";

    private UserAccess loggedUser;
    private Company selectedCompany;

    private OnFragmentInteractionListener mListener;

    private TextView companyId;
    private TextView companyName;
    private EditText companyAddress;
    private TextView companyCreationDate;
    private TextView companyModificationDate;
    private TextView companyCreationUser;
    private TextView companyModificationUser;
    private CheckBox companyActive;
    private Button saveCompanyButton;
    private ProgressBar progressBar;

    public CompanyEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedUser Parameter 1.
     * @param selectedCompany Parameter 2.
     * @return A new instance of fragment CompanyEditFragment.
     */
    public static CompanyEditFragment newInstance(UserAccess loggedUser, Company selectedCompany) {
        CompanyEditFragment fragment = new CompanyEditFragment();
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
        View view = inflater.inflate(R.layout.fragment_company_edit, container, false);

        companyId = (TextView) view.findViewById(R.id.companyEditId);
        companyName = (TextView) view.findViewById(R.id.companyEditName);
        companyAddress = (EditText) view.findViewById(R.id.companyEditAddress);
        companyCreationDate = (TextView) view.findViewById(R.id.companyEditCreationDate);
        companyModificationDate = (TextView) view.findViewById(R.id.companyEditModificationDate);
        companyCreationUser = (TextView) view.findViewById(R.id.companyEditCreationUser);
        companyModificationUser = (TextView) view.findViewById(R.id.companyEditModificationUser);
        companyActive = (CheckBox) view.findViewById(R.id.companyEditActive);
        saveCompanyButton = (Button) view.findViewById(R.id.companyEditSaveButton);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        saveCompanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCompany();
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

    private void saveCompany(){

        String address = companyAddress.getText().toString();
        address = address.trim();

        if(address.isEmpty()){
            Toast.makeText(getActivity(), "Preencher todos os campos!", Toast.LENGTH_LONG).show();
            return;
        }

        if(!Utils.isOnline(this.getActivity())){
            Toast.makeText(this.getActivity(), "There is no internet connection!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        Operations ops = new Operations(this, CODE_EDIT_COMPANY);
        ops.editCompany(selectedCompany.getId(), address, loggedUser.getId());

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

        Toast.makeText(getActivity(), "Company edited successfully!", Toast.LENGTH_LONG).show();

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
