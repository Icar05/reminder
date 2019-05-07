package com.example.icar.my_notification.fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.icar.my_notification.MainActivity;
import com.example.icar.my_notification.Model.Db;
import com.example.icar.my_notification.R;
import com.example.icar.my_notification.helpers.Constants;
import com.example.icar.my_notification.helpers.DatePicker;
import com.example.icar.my_notification.helpers.LimitedEditText;
import com.example.icar.my_notification.helpers.MyValidator;
import com.example.icar.my_notification.helpers.Priority;


public class CreateFragment extends Fragment implements View.OnClickListener {

    Button saveBtn;
    LimitedEditText etTitle, etDescription;
    TextView tvTerm;

//    LinearLayout root;

    DialogFragment dateDialog;
    Db database;

    Priority priority = Priority.HIGH;
    String title, description, showNotification;
    RadioGroup radioGroup;
    Boolean writeNotice = true;
    MyValidator validator;
    CheckBox checkBox;

    long count;


    public CreateFragment(){}

    public static CreateFragment newInstance(){
        CreateFragment importantListFragment = new CreateFragment();
        Bundle bundle = new Bundle();
        importantListFragment.setArguments(bundle);

        return importantListFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);

        ((MainActivity) getActivity()).toolbar.setTitle(getResources().getString(R.string.item_create));
        init(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).toolbar.setTitle(getResources().getString(R.string.item_create));
    }


   //init objects
   protected void init(View view) {

        validator = new MyValidator(getContext());
        dateDialog = new DatePicker();
        saveBtn = (Button)view.findViewById(R.id.save);

        etTitle = (LimitedEditText) view.findViewById(R.id.etTitle);
        etTitle.setMaxCharacters(20);
        etTitle.setMaxLines(1);


        etDescription = (LimitedEditText) view.findViewById(R.id.etDiscription);
        etDescription.setMaxCharacters(150);
        etDescription.setMaxLines(5);


        tvTerm = (TextView)view.findViewById(R.id.Term);
        radioGroup = (RadioGroup)view.findViewById(R.id.radioGroup);
        checkBox = (CheckBox)view.findViewById(R.id.chb);

       Typeface font = Typeface.createFromAsset(getContext().getAssets(), Constants.CUSTOM_FONT);
       checkBox.setTypeface(font);



       for(int i = 0; i< radioGroup.getChildCount(); i++){
           ( (RadioButton) radioGroup.getChildAt(i)).setTypeface(font);
       }




//        root = (LinearLayout) view.findViewById(R.id.root);
        saveBtn.setOnClickListener(this);
        tvTerm.setOnClickListener(this);
//        root.setOnClickListener(this);

        database = new Db(getContext());

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int ID) {
                switch (ID) {
                    case R.id.radioUrgent:priority = Priority.HIGH;break;
                    case R.id.radioNotUrgent:priority = Priority.LOW;break;
                    default:break;
                }
            }
        });

    }



     //here only saving data in database
    public void saveData(View view){

        if(validator.validate(tvTerm,etDescription,etTitle))
        {
            title = etTitle.getText().toString().trim();
            description = etDescription.getText().toString().trim();
            showNotification = checkBox.isChecked() ? "show": "not_show";

            if(writeNotice){
                database.open();

                //todo  write data
                database.writeData(title, description, DatePicker.date,DatePicker.integer_date, priority,showNotification);
                database.close();
                writeNotice = false;

                showToast(getResources().getString(R.string.done));


                getFragmentManager().popBackStack();
            }
        }
    }



    @Override
    public void onClick(View view) {


        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        switch (view.getId())
        {

            case R.id.Term:                dateDialog.show(getFragmentManager(), "datePicker");
                                           break;

            case R.id.save:                saveData(view);
                                            break;
            default: break;

        }

    }


    void showToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
