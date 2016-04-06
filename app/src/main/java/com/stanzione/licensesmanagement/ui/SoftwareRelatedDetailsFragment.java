package com.stanzione.licensesmanagement.ui;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.model.Software;
import com.stanzione.licensesmanagement.model.UserAccess;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SoftwareRelatedDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SoftwareRelatedDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoftwareRelatedDetailsFragment extends Fragment {

    private static final String ARG_LOGGED_USER = "loggedUser";
    private static final String ARG_SELECTED_SOFTWARE = "selectedSoftware";

    private UserAccess loggedUser;
    private Software selectedSoftware;

    private OnFragmentInteractionListener mListener;

    private TextView softwareId;
    private TextView softwareName;
    private TextView softwareCode;
    private TextView softwareType;
    private TextView softwareCreationDate;
    private TextView softwareModificationDate;
    private TextView softwareCreationUser;
    private TextView softwareModificationUser;
    private CheckBox softwareActive;

    public SoftwareRelatedDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedUser Parameter 1.
     * @param selectedSoftware Parameter 2.
     * @return A new instance of fragment SoftwareRelatedDetailsFragment.
     */
    public static SoftwareRelatedDetailsFragment newInstance(UserAccess loggedUser, Software selectedSoftware) {
        SoftwareRelatedDetailsFragment fragment = new SoftwareRelatedDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOGGED_USER, loggedUser);
        args.putSerializable(ARG_SELECTED_SOFTWARE, selectedSoftware);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loggedUser = (UserAccess) getArguments().getSerializable(ARG_LOGGED_USER);
            selectedSoftware = (Software) getArguments().getSerializable(ARG_SELECTED_SOFTWARE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_software_related_details, container, false);

        softwareId = (TextView) view.findViewById(R.id.softwareDetailId);
        softwareName = (TextView) view.findViewById(R.id.softwareDetailName);
        softwareCode = (TextView) view.findViewById(R.id.softwareDetailCode);
        softwareType = (TextView) view.findViewById(R.id.softwareDetailType);
        softwareCreationDate = (TextView) view.findViewById(R.id.softwareDetailCreationDate);
        softwareModificationDate = (TextView) view.findViewById(R.id.softwareDetailModificationDate);
        softwareCreationUser = (TextView) view.findViewById(R.id.softwareDetailCreationUser);
        softwareModificationUser = (TextView) view.findViewById(R.id.softwareDetailModificationUser);
        softwareActive = (CheckBox) view.findViewById(R.id.softwareDetailActive);

        softwareId.setText(String.valueOf(selectedSoftware.getId()));
        softwareName.setText(selectedSoftware.getName());
        softwareCode.setText(selectedSoftware.getCode());
        softwareType.setText(selectedSoftware.getType());
        softwareCreationDate.setText(selectedSoftware.getCreationDate());
        softwareModificationDate.setText(selectedSoftware.getModificationDate());
        softwareCreationUser.setText(selectedSoftware.getCreationUserName());
        softwareModificationUser.setText(selectedSoftware.getModificationUserName());
        softwareActive.setChecked(selectedSoftware.isActivate());

        return view;
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
