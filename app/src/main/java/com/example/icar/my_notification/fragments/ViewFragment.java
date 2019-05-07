package com.example.icar.my_notification.fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.icar.my_notification.CoreApplication;
import com.example.icar.my_notification.MainActivity;
import com.example.icar.my_notification.Model.Db;
import com.example.icar.my_notification.R;
import com.example.icar.my_notification.helpers.Priority;
import com.example.icar.my_notification.helpers.TimeHelper;

/**
 * Created by icar on 28.02.17.
 */
public class ViewFragment extends Fragment {


    TextView title, description, priority, date, mainTitle;
    Db database;
    String id;
    String priorityText = "";
    ImageButton edit, delete;



    public static ViewFragment newInstance(String item_id){
        ViewFragment viewFragment = new ViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", item_id);
        viewFragment.setArguments(bundle);
        return viewFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        String mtText = getResources().getString(R.string.see_notif)+id;

        ((MainActivity) getActivity()).toolbar.setTitle(mtText);
        init(view);
        return view;
    }

    private void init(View view) {

        title = (TextView)view.findViewById(R.id.item_title);
        description = (TextView)view.findViewById(R.id.item_description);
        priority = (TextView)view.findViewById(R.id.item_priority);
        date = (TextView)view.findViewById(R.id.item_date);
        mainTitle = (TextView)view.findViewById(R.id.mainTitle);
        edit = (ImageButton)view.findViewById(R.id.item_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = UpdateFragment.newInstance(id);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMain, fragment)
                        .addToBackStack("myStack")
                        .commit();
            }
        });


        id = getArguments().getString("id");

        delete = (ImageButton) view.findViewById(R.id.item_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  deleteItem(id);
            }
        });






        String mtText = getResources().getString(R.string.see_notif)+id;
        mainTitle.setText(mtText);

        database = new Db(getContext());
        database.open();
        Cursor cursor = database.getItem(id);

        if(cursor.moveToFirst())
        {
            title.setText(cursor.getString(cursor.getColumnIndex(Db.KEY_TITLE)));


            description.setText(cursor.getString(cursor.getColumnIndex(Db.KEY_DESCRIPTION)));
            priorityText = cursor.getString(cursor.getColumnIndex(Db.KEY_PRIORITY));
            date.setText(TimeHelper.formatDateToString(cursor.getLong(cursor.getColumnIndex(Db.KEY_DATE_INTEGER))));
        }
        cursor.close();
        database.close();


        if(priorityText.equals(Priority.HIGH.toString())) {
            priority.setTextColor(Color.RED);
            priority.setText(getContext().getResources().getString(R.string.important));
        } else{
            priority.setTextColor(Color.parseColor("#007f00"));
            priority.setText(getContext().getResources().getString(R.string.not_important));
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        String mtText = getResources().getString(R.string.see_notif)+id;
        ((MainActivity) getActivity()).toolbar.setTitle(mtText);
    }


    private void deleteItem(String item_id) {

        database.open();
        database.deleteItem(Long.valueOf(item_id));
        database.close();


        Toast.makeText(getContext(), getString(R.string.deleted), Toast.LENGTH_SHORT).show();
        getFragmentManager().popBackStack();


    }




}
