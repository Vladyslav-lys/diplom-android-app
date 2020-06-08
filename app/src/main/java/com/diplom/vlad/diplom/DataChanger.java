package com.diplom.vlad.diplom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataChanger {

    private PlombData plombData;
    private DBHelper dbHelper;
    private ContentValues cv;

    public DataChanger(PlombData plombData, DBHelper dbHelper)
    {
        cv = new ContentValues();
        this.plombData = plombData;
        this.dbHelper = dbHelper;
    }

    public void setDataToUpdate(String plomb_num, String date_issue, String id_whom_issue, String date_set, String id_enterprise,
                        String id_loco_seria, String id_loco_num, String id_place_set, String date_off, String id_adjuster,
                        String cause_off, String id_getter, String comment, String date_stocked, int index, Context context)
    {
        plombData.updatePlomb_num(plomb_num, index);
        cv.put("plomb_num", plomb_num);
        if(checkDateFormat(date_issue,context))
        {
            plombData.updateDate_issue(date_issue,index);
            cv.put("date_issue", date_issue);
        }
        plombData.updateIdWhom_issue(id_whom_issue,index);
        cv.put("id_whom_issue", id_whom_issue);
        if(checkDateFormat(date_set,context))
        {
            plombData.updateDate_set(date_set,index);
            cv.put("date_set", date_set);
        }
        plombData.updateIdEnterprise(id_enterprise,index);
        cv.put("id_enterprise", id_enterprise);
        plombData.updateIdLoco_seria(id_loco_seria,index);
        cv.put("id_loco_seria", id_loco_seria);
        plombData.updateIdLoco_num(id_loco_num,index);
        cv.put("id_loco_num", id_loco_num);
        plombData.updateIdPlace_set(id_place_set,index);
        cv.put("id_place_set", id_place_set);
        if(checkDateFormat(date_off,context))
        {
            plombData.updateDate_off(date_off,index);
            cv.put("date_off", date_off);
        }
        plombData.updateIdAdjuster(id_adjuster,index);
        cv.put("id_adjuster", id_adjuster);
        plombData.updateCause_off(cause_off,index);
        cv.put("cause_off", cause_off);
        plombData.updateIdGetter(id_getter,index);
        cv.put("id_getter", id_getter);
        plombData.updateComment(comment,index);
        cv.put("comment", comment);
        if(checkDateFormat(date_stocked,context))
        {
            plombData.updateDate_stocked(date_stocked,index);
            cv.put("date_stocked", date_stocked);
        }
    }

    public void updateDatabase(String id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update("plombtable", cv, "id = ?", new String[] { id });
        db.close();
    }

    private boolean checkDateFormat(String value, Context context)
    {
        if(value.isEmpty())
            return true;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date date = sdf.parse(value);
            if (!value.equals(sdf.format(date)))
            {
                Toast toast = Toast.makeText(context, "Неправильный формат даты!", Toast.LENGTH_LONG);
                toast.show();
                return false;
            }
            return true;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
