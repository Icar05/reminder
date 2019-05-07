package com.example.icar.my_notification.helpers;

import android.content.Context;
import android.graphics.Color;
import android.widget.EditText;
import android.widget.TextView;

import com.example.icar.my_notification.R;

public class MyValidator
{

    Context context;

    public MyValidator(Context context) {
        this.context = context;
    }

    private boolean dateCheck(TextView tvTerm){

        if(tvTerm.getText().equals(context.getResources().getString(R.string.push_dedline)))
        {
            tvTerm.setTextColor(Color.RED);
            return false;
        }
        else
            return true;
    }

    private boolean textCheck(EditText etDiscription){
        if(etDiscription.getText().toString().trim().isEmpty())
        {
            etDiscription.setText(null);
            etDiscription.setSelection(0);
            etDiscription.setError(context.getResources().getString(R.string.need_content_notif));
            return false;
        }
        else
        {
            return true;
        }

    }

    private boolean titleCheck(EditText etTitle){
        if(etTitle.getText().toString().trim().isEmpty())
        {
            etTitle.setText(null);
            etTitle.setSelection(0);
            etTitle.setError(context.getResources().getString(R.string.need_title_notif));
            return false;
        }
        else{
            return true;
        }

    }

    public  boolean validate(TextView tvTerm,EditText etDiscription, EditText etTitle ) {

        return dateCheck(tvTerm) && textCheck(etDiscription) && titleCheck(etTitle);

    }

}
