package com.example.icar.my_notification.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;


import com.example.icar.my_notification.MainActivity;
import com.example.icar.my_notification.Model.Db;
import com.example.icar.my_notification.R;
import com.example.icar.my_notification.helpers.DatePicker;
import com.example.icar.my_notification.helpers.Priority;
import com.example.icar.my_notification.helpers.TimeHelper;

public class UpdateFragment extends CreateFragment{

    String itemId;
    String pName;

    public UpdateFragment(){}

    public static UpdateFragment newInstance(String item_id){
        UpdateFragment importantListFragment = new UpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("itemId", item_id);
        importantListFragment.setArguments(bundle);

        return importantListFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_create, container, false);

        super.init(view);

        this.itemId = getArguments().getString("itemId");
        Log.d("fragment", "item id is ..." + itemId);

        ((MainActivity) getActivity()).toolbar.setTitle(getResources().getString(R.string.update_item_title) + itemId);

        actionBeforeUpdate(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.itemId = getArguments().getString("itemId");
        Log.d("fragment", "item id is ..." + itemId);

        ((MainActivity) getActivity()).toolbar.setTitle(getResources().getString(R.string.update_item_title) + itemId);
    }

    @Override
    public void saveData(View view) {

        if(validator.validate(tvTerm,etDescription,etTitle))
        {

            title = etTitle.getText().toString().trim();
            description = etDescription.getText().toString().trim();
            showNotification = checkBox.isChecked() ? "show": "not_show";

            if(writeNotice){
                database.open();
                database.updateItem(title,description, DatePicker.date,DatePicker.integer_date, priority, showNotification, itemId);
                database.close();
                writeNotice = false;
                showToast(getResources().getString(R.string.date_was_update));

                getFragmentManager().popBackStack();
            }
        }
    }

    //initing data from database
    public void actionBeforeUpdate(View view) {



        database.open();
        Cursor cursor = database.getItem(itemId);
        if (cursor.moveToFirst()){

            etDescription.setText(cursor.getString(cursor.getColumnIndex(Db.KEY_DESCRIPTION)));
            etTitle.setText(cursor.getString(cursor.getColumnIndex(Db.KEY_TITLE)));
            pName = cursor.getString(cursor.getColumnIndex(Db.KEY_PRIORITY));
            showNotification = cursor.getString(cursor.getColumnIndex(Db.KEY_NOTIFICATION));

            if(showNotification.equals("show")) checkBox.setChecked(true);

            DatePicker.date  = cursor.getString(cursor.getColumnIndex(Db.KEY_DATE));
            DatePicker.integer_date = cursor.getLong(cursor.getColumnIndex(Db.KEY_DATE_INTEGER));

            tvTerm.setText(TimeHelper.formatDateToString(DatePicker.integer_date));


            RadioButton rb;

            if(pName.equals(Priority.HIGH.toString())){
                rb = (RadioButton) view.findViewById(R.id.radioUrgent);
            }else {
                rb = (RadioButton) view.findViewById(R.id.radioNotUrgent);
            }

            rb.setChecked(true);




        }
        cursor.close();
        database.close();
    }

}
