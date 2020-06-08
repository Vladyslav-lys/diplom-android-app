package com.diplom.vlad.diplom;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Record {
    private DBHelper dbHelper;

    public Record(DBHelper dbHelper)
    {
        this.dbHelper = dbHelper;
    }

    public void addPlomb(String plomb_num, int id_whom_issue, Date date_issue)
    {
        Plomb plomb = new Plomb(plomb_num,String.valueOf(id_whom_issue),setDateFormat(date_issue));
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("plomb_num", plomb.getPlomb_num());
        cv.put("id_whom_issue", plomb.getIdWhom_issue());
        cv.put("date_issue", plomb.getDate_issue());
        cv.put("date_set", "");
        cv.put("id_enterprise", "0");
        cv.put("id_loco_seria", "0");
        cv.put("id_loco_num", "0");
        cv.put("id_place_set", "0");
        cv.put("date_off", "");
        cv.put("id_adjuster", plomb.getIdWhom_issue());
        cv.put("id_getter", plomb.getIdWhom_issue());
        cv.put("date_stocked", "");
        db.insert("plombtable", null, cv);
        db.close();
    }

    private String setDateFormat(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date);
    }

    public boolean checkUser(Spinner spinnerIssued, Context context)
    {
        if(spinnerIssued.getSelectedItem().toString().equals("Unknown"))
        {
            Toast toast = Toast.makeText(context, "Пользователь отсутствует!", Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
        return true;
    }

    public boolean checkIsFilled(EditText plombNumber, CalendarView date_issued, Spinner spinnerIssued, Context context)
    {
        if(plombNumber.getText().toString().isEmpty() || spinnerIssued.getSelectedItem().toString().isEmpty()
                || date_issued.equals(null))
        {
            Toast toast = Toast.makeText(context, "Заполните поля!", Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
        return true;
    }
}
