package com.example.icar.my_notification.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.example.icar.my_notification.CoreApplication;
import com.example.icar.my_notification.Model.Db;
import com.example.icar.my_notification.Model.Loader;
import com.example.icar.my_notification.Model.NotificationObject;
import com.example.icar.my_notification.R;
import com.example.icar.my_notification.helpers.Constants;
import com.example.icar.my_notification.helpers.NotificationAdapter;

import java.util.List;

import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

/**
 * Created by icar on 11.05.17.
 */

public abstract class AbstractListFragment extends Fragment {


    Loader loader;
    RecyclerView recyclerView;
    Db database;
    LinearLayout no_data;
    NotificationAdapter adapter;
    long count;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_all, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler);
        no_data = (LinearLayout) view.findViewById(R.id.empty_layout);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        selfTitle();
    }

    @Override
    public void onResume() {
        super.onResume();
        selfTitle();
    }

    private void init(){

        loader = new Loader();
        database = new Db(getContext());

        final List<NotificationObject> data = getData();

        if(data.size() > 0){
            showRecycler(data);
        } else {
            hideRecycler();
        }

    }



    private void deleteItem(int position, int item_id) {

        database.open();
        database.deleteItem((long) item_id);
        database.close();

        if(adapter.deleteItem(position) < 1)
            hideRecycler();


    }

    private void updateItem(int item_id) {

        Fragment fragment = UpdateFragment.newInstance(String.valueOf(item_id));
        goTo(fragment);

    }

    private void showRecycler(List<NotificationObject> data){
        adapter = new NotificationAdapter(data);
        adapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View parentItem, int position, View currentView, int item_id) {



                switch (currentView.getId()) {
                    case R.id.item_edit:
                        updateItem(item_id);
                        break;

                    case R.id.item_delete:
                        deleteItem(position, item_id);
                        break;

                    case R.id.activation_layout:

                        goTo(ViewFragment.newInstance(String.valueOf(item_id)));

                        break;
                }
            }
        });


        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setItemAnimator(new ScaleInAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        no_data.setVisibility(View.GONE);
    }

    private void hideRecycler() {
        recyclerView.setVisibility(View.GONE);
        no_data.setVisibility(View.VISIBLE);
    }




    public void goTo(Fragment fragment){

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentMain, fragment)
                .addToBackStack("myStack")
                .commit();
    }

    private void showLog(String message){
        Log.d("lifecicle", message);
    }



    /*
     обязательная реализация наследников
     */
    public abstract void  selfTitle();

    public abstract List<NotificationObject> getData();







}
