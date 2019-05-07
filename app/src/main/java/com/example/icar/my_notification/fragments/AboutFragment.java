package com.example.icar.my_notification.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.icar.my_notification.MainActivity;
import com.example.icar.my_notification.R;





public class AboutFragment extends Fragment {

    public AboutFragment(){}

    public static AboutFragment newInstance(){
        AboutFragment importantListFragment = new AboutFragment();
        Bundle bundle = new Bundle();
        importantListFragment.setArguments(bundle);

        return importantListFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        ((MainActivity) getActivity()).toolbar.setTitle(getResources().getString(R.string.about_application_title));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).toolbar.setTitle(getResources().getString(R.string.about_application_title));
    }
}
