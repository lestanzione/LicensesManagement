package com.stanzione.licensesmanagement.ui;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.helper.ViewPagerAdapter;
import com.stanzione.licensesmanagement.model.Project;
import com.stanzione.licensesmanagement.model.Software;
import com.stanzione.licensesmanagement.model.UserAccess;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SoftwareDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SoftwareDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoftwareDetailsFragment extends Fragment {

    private static final String ARG_LOGGED_USER = "loggedUser";
    private static final String ARG_SELECTED_SOFTWARE = "selectedSoftware";

    private UserAccess loggedUser;
    private Software selectedSoftware;

    private static final String TAG = SoftwareDetailsFragment.class.getSimpleName();

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private OnFragmentInteractionListener mListener;

    public SoftwareDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loggedUser Parameter 1.
     * @param selectedSoftware Parameter 2.
     * @return A new instance of fragment SoftwareDetailsFragment.
     */
    public static SoftwareDetailsFragment newInstance(UserAccess loggedUser, Software selectedSoftware) {
        SoftwareDetailsFragment fragment = new SoftwareDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_software_details, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.softwareDetailsViewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.softwareDetailsTabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(SoftwareRelatedDetailsFragment.newInstance(loggedUser, selectedSoftware), "Details");
        viewPager.setAdapter(adapter);
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
