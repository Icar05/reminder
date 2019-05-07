package com.example.icar.my_notification.helpers;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.TextView;

import com.example.icar.my_notification.R;

import java.util.Calendar;
import java.util.Date;

public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    public static String date;
    public static long integer_date;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // определяем текущую дату
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // создаем DatePickerDialog и возвращаем его
        Dialog picker = new DatePickerDialog(getActivity(), this, year, month, day);
        picker.setTitle(getResources().getString(R.string.choose_date));

        return picker;
    }

    @Override
    public void onStart() {
        super.onStart();
        // добавляем кастомный текст для кнопки
        Button nButton =  ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_POSITIVE);
        nButton.setText(getResources().getString(R.string.ready));
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        TextView tv = (TextView) getActivity().findViewById(R.id.Term);


        int oldMonth = monthOfYear;




        monthOfYear +=1;

        date  = dayOfMonth+"/"+monthOfYear +"/"+year;


        tv.setTextColor(Color.parseColor("#006600"));

        String date_content = getResources().getString(R.string.date_for_datapicker);
        date_content += dayOfMonth;
        date_content += " - "+ monthOfYear;
        date_content +=  " - " + year;




        Calendar calendar = Calendar.getInstance();
        calendar.set(year, oldMonth, dayOfMonth);
        Date date = calendar.getTime();
        integer_date =  date.getTime();



        tv.setText(TimeHelper.formatDateToString(integer_date));
    }






}
