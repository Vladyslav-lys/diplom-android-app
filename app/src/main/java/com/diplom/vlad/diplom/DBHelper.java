package com.diplom.vlad.diplom;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.os.Bundle;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    //private List<String> dataEnterprise = Arrays.asList("Kek","LTD","Kiev");
    //private List<String> dataSetPlace=Arrays.asList("Крыша","Капот","Бампер");
    //private List<String> dataLocoNum=Arrays.asList("23","24","45");
    //private List<String> dataSeria=Arrays.asList("FSSD","SDFSD","QWEQ");
    //private String[] dataSurname=("Лысенко","Владиславов","Юрьев");
    //private String[] dataName=("Влад","Вова","Юра");
    //private String[] dataSecondName=("Лысенкович","Владиславоввич","Юрьевич");
    //private String[] dataPhone=("+380982281488","+380688700690","+380666666666");

    public DBHelper(Context context, String dbName) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createPlombTable(db);
        createEmployeerTable(db);
        createEnterpriseTable(db);
        createSetPlaceTable(db);
        createLocoTypesTable(db);
        createLocoTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getTableByName(SQLiteDatabase db, String tableName) {
        return db.query(tableName, null, null, null, null, null, null);
    }

    private void createPlombTable(SQLiteDatabase db) {
        db.execSQL("create table plombtable ("
                + "id integer primary key autoincrement,"
                + "plomb_num integer,"
                + "date_issue text,"
                + "id_whom_issue integer,"
                + "date_set text,"
                + "id_enterprise integer,"
                + "id_loco_seria integer,"
                + "id_loco_num integer,"
                + "id_place_set integer,"
                + "date_off text,"
                + "id_adjuster integer,"
                + "cause_off text,"
                + "id_getter integer,"
                + "comment text,"
                + "date_stocked text" + ");");
    }

    private void createEnterpriseTable(SQLiteDatabase db) {
        db.execSQL("create table enterprisetable ("
                + "id integer primary key autoincrement,"
                + "name text" + ");");
        ContentValues values = new ContentValues();
        String[] dataEnterprise = {"Kek", "LTD", "Kiev"};
        for (int i = 0; i < dataEnterprise.length; i++) {
            values.put("name", dataEnterprise[i]);
            db.insert("enterprisetable", null, values);
        }
    }

    private void createSetPlaceTable(SQLiteDatabase db) {
        db.execSQL("create table setplacetable ("
                + "id integer primary key autoincrement,"
                + "name text" + ");");
        ContentValues values = new ContentValues();
        String[] dataSetPlace = {"Крыша", "Капот", "Бампер"};
        for (int i = 0; i < dataSetPlace.length; i++) {
            values.put("name", dataSetPlace[i]);
            db.insert("setplacetable", null, values);
        }
    }

    private void createLocoTypesTable(SQLiteDatabase db) {
        db.execSQL("create table locotypestable ("
                + "id integer primary key autoincrement,"
                + "seria text" + ");");
        ContentValues values = new ContentValues();
        String[] dataSeria = {"FSSD", "SDFSD", "QWEQ"};
        for (int i = 0; i < dataSeria.length; i++) {
            values.put("seria", dataSeria[i]);
            db.insert("locotypestable", null, values);
        }
    }

    private void createLocoTable(SQLiteDatabase db) {
        db.execSQL("create table locotable ("
                + "id integer primary key autoincrement,"
                + "num integer,"
                + "id_loco_type integer" + ");");
        ContentValues values = new ContentValues();
        String[] dataLocoNum = {"23", "24", "45"};
        String[] dataIdLocoType = {"1","2","3"};
        for (int i = 0; i < dataLocoNum.length; i++) {
            values.put("num", dataLocoNum[i]);
            values.put("id_loco_type", dataIdLocoType[i]);
            db.insert("locotable", null, values);
        }
    }

    private void createEmployeerTable(SQLiteDatabase db) {
        db.execSQL("create table employeertable ("
                + "id integer primary key autoincrement,"
                + "surname text,"
                + "name text,"
                + "second_name text,"
                + "phone text" + ");");
        ContentValues values = new ContentValues();
        String[] dataSurname = {"Лысенко", "Владиславов", "Юрьев"};
        String[] dataName = {"Влад", "Вова", "Юра"};
        String[] dataSecondName = {"Лысенкович", "Владиславоввич", "Юрьевич"};
        String[] dataPhone = {"+380982281488", "+380688700690", "+380666666666"};
        for (int i = 0; i < dataSurname.length; i++) {
            values.put("surname", dataSurname[i]);
            values.put("name", dataName[i]);
            values.put("second_name", dataSecondName[i]);
            values.put("phone", dataPhone[i]);
            db.insert("employeertable", null, values);
        }
    }

    public String[] getEmployeerData(DBHelper dbHelper) {
        ArrayList<String> data = new ArrayList<String>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = dbHelper.getTableByName(db, "employeertable");
        data.add("Unknown");
        while (c.moveToNext()) {
            data.add(c.getString(c.getColumnIndex("surname")));
        }
        db.close();
        return data.toArray(new String[data.size()]);
    }

    public Integer getEmployeerIdByPhone(DBHelper dbHelper, Context context) {
        TelephonyManager tManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = dbHelper.getTableByName(db, "employeertable");
        Integer id = 0;
        if(ContextCompat.checkSelfPermission(context,Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context,Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED)
        {
            Toast toast = Toast.makeText(context, "Дайте доступ к данным телефона!", Toast.LENGTH_LONG);
            toast.show();
            ActivityCompat.requestPermissions((Activity) context,new String[] {Manifest.permission.READ_PHONE_STATE}, 1);
            return 0;
        }
        while (c.moveToNext())
        {
            if(c.getString(c.getColumnIndex("phone")).equalsIgnoreCase("+380982281488"))
            {
                id = Integer.valueOf(c.getString(c.getColumnIndex("id")));
                break;
            }
        }
        db.close();
        return id;
    }

    public String[] getEnterpriseData(DBHelper dbHelper)
    {
        ArrayList<String> data = new ArrayList<String>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = dbHelper.getTableByName(db, "enterprisetable");
        data.add("Unknown");
        while (c.moveToNext())
        {
            data.add(c.getString(c.getColumnIndex("name")));
        }
        db.close();
        return data.toArray(new String[data.size()]);
    }
    public String getValueByIdEnterprise(DBHelper dbHelper, String id)
    {
        String value = "";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = dbHelper.getTableByName(db, "enterprisetable");
        while (c.moveToNext()) {
            if(c.getString(c.getColumnIndex("id")).equalsIgnoreCase(id))
            {
                value = c.getString(c.getColumnIndex("name"));
                return value;
            }
        }
        return value;
    }

    public String[] getLocoTypesData(DBHelper dbHelper)
    {
        ArrayList<String> data = new ArrayList<String>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = dbHelper.getTableByName(db, "locotypestable");
        data.add("Unknown");
        while (c.moveToNext())
        {
            data.add(c.getString(c.getColumnIndex("seria")));
        }
        db.close();
        return data.toArray(new String[data.size()]);
    }
    public String getValueByIdLocoTypes(DBHelper dbHelper, String id)
    {
        String value = "";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = dbHelper.getTableByName(db, "locotypestable");
        while (c.moveToNext()) {
            if(c.getString(c.getColumnIndex("id")).equalsIgnoreCase(id))
            {
                value = c.getString(c.getColumnIndex("seria"));
                return value;
            }
        }
        return value;
    }

    public String[] getLocoData(DBHelper dbHelper)
    {
        ArrayList<String> data = new ArrayList<String>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = dbHelper.getTableByName(db, "locotable");
        data.add("Unknown");
        while (c.moveToNext())
        {
            data.add(c.getString(c.getColumnIndex("num")));
        }
        db.close();
        return data.toArray(new String[data.size()]);
    }
    public String getValueByIdLoco(DBHelper dbHelper, String id)
    {
        String value = "";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = dbHelper.getTableByName(db, "locotable");
        while (c.moveToNext()) {
            if(c.getString(c.getColumnIndex("id")).equalsIgnoreCase(id))
            {
                value = c.getString(c.getColumnIndex("num"));
                return value;
            }
        }
        return value;
    }

    public Integer getLocoTypeIdByLocoId(DBHelper dbHelper, String idLoco)
    {
        Integer idType = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = dbHelper.getTableByName(db, "locotable");
        while (c.moveToNext()) {
            if (c.getString(c.getColumnIndex("id")).equalsIgnoreCase(idLoco)) {
                idType = Integer.parseInt(c.getString(c.getColumnIndex("id_loco_type")));
                return idType;
            }
        }
        return idType;
    }

    public String[] getSetPlaceData(DBHelper dbHelper)
    {
        ArrayList<String> data = new ArrayList<String>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = dbHelper.getTableByName(db, "setplacetable");
        data.add("Unknown");
        while (c.moveToNext())
        {
            data.add(c.getString(c.getColumnIndex("name")));
        }
        db.close();
        return data.toArray(new String[data.size()]);
    }
    public String getValueByIdSetPlace(DBHelper dbHelper, String id)
    {
        String value = "";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = dbHelper.getTableByName(db, "setplacetable");
        while (c.moveToNext()) {
            if(c.getString(c.getColumnIndex("id")).equalsIgnoreCase(id))
            {
                value = c.getString(c.getColumnIndex("name"));
                return value;
            }
        }
        return value;
    }

    public boolean checkTableIsNotEmpty(String tableName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT count(*) From "+tableName,null);
        cursor.moveToFirst();
        if(cursor.getInt(0) > 0)
        {
            return true;
        }
        return false;
    }
}
