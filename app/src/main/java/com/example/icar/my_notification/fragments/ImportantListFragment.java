package com.example.icar.my_notification.fragments;

import android.os.Bundle;

import com.example.icar.my_notification.MainActivity;
import com.example.icar.my_notification.Model.NotificationObject;
import com.example.icar.my_notification.R;
import com.example.icar.my_notification.helpers.Priority;

import java.util.List;

/**
 * Created by icar on 11.05.17.
 */

public class ImportantListFragment extends AbstractListFragment {


    public ImportantListFragment(){}

    public static ImportantListFragment newInstance(){
        ImportantListFragment importantListFragment = new ImportantListFragment();
        Bundle bundle = new Bundle();
        importantListFragment.setArguments(bundle);

        return importantListFragment;
    }



    @Override
    public void selfTitle() {
        ((MainActivity) getActivity()).toolbar.setTitle(getResources().getString(R.string.all_important_notification));
    }

    @Override
    public List<NotificationObject> getData() {
        return loader.getData(Priority.HIGH, getContext());
    }

}
