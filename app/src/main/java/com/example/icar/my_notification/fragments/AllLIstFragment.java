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

public class AllLIstFragment extends AbstractListFragment {

    public AllLIstFragment(){}

    public static AllLIstFragment newInstance(){
        AllLIstFragment importantListFragment = new AllLIstFragment();
        Bundle bundle = new Bundle();
        importantListFragment.setArguments(bundle);

        return importantListFragment;
    }



    @Override
    public void selfTitle() {
        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.all_notification));
    }

    @Override
    public List<NotificationObject> getData() {
        return loader.getData(Priority.ALL, getContext());
    }
}
