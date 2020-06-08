package com.diplom.vlad.diplom;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Filter {

    private PlombData plombData;
    private DBHelper dbHelper;

    public Filter(PlombData plombData, DBHelper dbHelper)
    {
        this.plombData = plombData;
        this.dbHelper = dbHelper;
    }

    public PlombData filterData(Integer posType, String value)
    {
        PlombData newData = new PlombData();
        for(int i = 0; i < plombData.getSize(); i++)
        {
            if(getType(posType,i).equals(value))
            {
                newData.add(plombData.getByIndex(i));
            }
        }
        return newData;
    }

    public boolean checkIsFilled(String value, Context context)
    {
        if(!value.equals(""))
        {
            return true;
        }
        Toast toast = Toast.makeText(context, "Заполните поле!", Toast.LENGTH_LONG);
        toast.show();
        return false;
    }

    public boolean checkDateFormat(Integer posType, String dateValue, Context context)
    {
        try {
            if (posType.equals(0) || posType.equals(1) || posType.equals(6) || posType.equals(7)) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                Date date = sdf.parse(dateValue);
                if (!dateValue.equals(sdf.format(date))) {
                    Toast toast = Toast.makeText(context, "Неправильный формат даты!", Toast.LENGTH_LONG);
                    toast.show();
                    return false;
                }
            }
            return true;
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkDataExist(Integer posType, String value, Context context)
    {
        for(int i = 0; i < plombData.getSize(); i++)
        {
            if(getType(posType, i).equals(value))
            {
                return true;
            }
        }
        Toast toast = Toast.makeText(context, "Данных не найдено!", Toast.LENGTH_LONG);
        toast.show();
        return false;
    }

    public String getType(Integer posType, Integer index)
    {
        String value;
        switch (posType)
        {
            case 0:
                return plombData.getByIndex(index).getDate_issue();
            case 1:
                return plombData.getByIndex(index).getDate_set();
            case 2:
                value = dbHelper.getValueByIdEnterprise(dbHelper,plombData.getByIndex(index).getIdEnterprise());
                return value;
            case 3:
                value = dbHelper.getValueByIdLocoTypes(dbHelper,plombData.getByIndex(index).getIdLoco_seria());
                return value;
            case 4:
                value = dbHelper.getValueByIdLoco(dbHelper,plombData.getByIndex(index).getIdLoco_num());
                return value;
            case 5:
                value = dbHelper.getValueByIdSetPlace(dbHelper,plombData.getByIndex(index).getIdPlace_set());
                return value;
            case 6:
                return plombData.getByIndex(index).getDate_off();
            case 7:
                return plombData.getByIndex(index).getDate_stocked();
        }
        return "";
    }

    //public boolean checkIsFilled(String date_issue, String id_whom_issue, String date_set, String id_enterprise,
    //                             String id_loco_seria,String id_loco_num, String id_place_set, String date_off,
    //                             String id_adjuster,String id_getter, String date_stocked, Context context)
    //{
    //    if(!date_issue.isEmpty() || !id_whom_issue.equals("0") || !date_set.isEmpty() || !id_enterprise.equals("0")
    //            || !id_loco_seria.equals("0") || !id_loco_num.equals("0") || !id_place_set.equals("0") || !date_off.isEmpty()
    //            || !id_adjuster.equals("0") || !id_getter.equals("0") || !date_stocked.isEmpty())
    //    {
    //        return true;
    //    }
    //    Toast toast = Toast.makeText(context, "Заполните поля!", Toast.LENGTH_LONG);
    //    toast.show();
    //    return false;
    //}
//
    //public boolean checkDateFormat(String date_issue, String date_set, String date_off, String date_stocked, Context context)
    //{
    //    try {
    //        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    //        String[] dates = {date_issue, date_set, date_off, date_stocked};
    //        for(int i = 0;i < dates.length;i++)
    //        {
    //            Date date = sdf.parse(dates[i]);
    //            if (!dates[i].equals(sdf.format(date)))
    //            {
    //                Toast toast = Toast.makeText(context, "Неправильный формат даты!", Toast.LENGTH_LONG);
    //                toast.show();
    //                return false;
    //            }
    //        }
    //        return true;
    //    }
    //    catch (ParseException e)
    //    {
    //        e.printStackTrace();
    //    }
    //    return false;
    //}
//
    //public boolean checkDataExist(String date_issue, String id_whom_issue, String date_set, String id_enterprise,
    //                              String id_loco_seria,String id_loco_num, String id_place_set, String date_off,
    //                              String id_adjuster,String id_getter, String date_stocked, Context context)
    //{
    //    for(int i = 0; i < plombData.getSize(); i++)
    //    {
    //        if(plombData.getByIndex(i).getDate_issue().equals(date_issue) || plombData.getByIndex(i).getDate_set().equals(date_set)
    //                || plombData.getByIndex(i).getDate_off().equals(date_off) || plombData.getByIndex(i).getDate_stocked().equals(date_stocked)
    //                || (plombData.getByIndex(i).getIdWhom_issue().equals(id_whom_issue) && !plombData.getByIndex(i).getIdWhom_issue().equals("0"))
    //                || (plombData.getByIndex(i).getIdEnterprise().equals(id_enterprise) && !plombData.getByIndex(i).getIdEnterprise().equals("0"))
    //                || (plombData.getByIndex(i).getIdLoco_seria().equals(id_loco_seria) && !plombData.getByIndex(i).getIdLoco_seria().equals("0"))
    //                || (plombData.getByIndex(i).getIdLoco_num().equals(id_loco_num) && !plombData.getByIndex(i).getIdLoco_num().equals("0"))
    //                || (plombData.getByIndex(i).getIdPlace_set().equals(id_place_set) && !plombData.getByIndex(i).getIdPlace_set().equals("0"))
    //                || (plombData.getByIndex(i).getIdAdjuster().equals(id_adjuster) && !plombData.getByIndex(i).getIdAdjuster().equals("0"))
    //                || (plombData.getByIndex(i).getIdGetter().equals(id_getter) && !plombData.getByIndex(i).getIdGetter().equals("0")))
    //        {
    //            return true;
    //        }
    //    }
    //    Toast toast = Toast.makeText(context, "Данных не найдено!", Toast.LENGTH_LONG);
    //    toast.show();
    //    return false;
    //}
}
