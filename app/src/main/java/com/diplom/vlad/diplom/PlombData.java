package com.diplom.vlad.diplom;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PlombData {
    private ArrayList<Plomb> plombCollection;
    private DBHelper dbHelper;

    public PlombData()
    {
        plombCollection = new ArrayList<Plomb>();
    }

    public PlombData(DBHelper dbHelper)
    {
        plombCollection = new ArrayList<Plomb>();
        this.dbHelper = dbHelper;
        loadFromDatabase();
    }

    public Plomb getByIndex(int index)
    {
        return this.plombCollection.get(index);
    }

    public int getSize()
    {
        return this.plombCollection.size();
    }

    public void loadFromDatabase()
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cPlomb = dbHelper.getTableByName(db, "plombtable");

        int idColIndex = cPlomb.getColumnIndex("id");
        int plombNumColIndex = cPlomb.getColumnIndex("plomb_num");
        int dateIssueColIndex = cPlomb.getColumnIndex("date_issue");
        int idWhomIssueColIndex = cPlomb.getColumnIndex("id_whom_issue");
        int dateSetColIndex = cPlomb.getColumnIndex("date_set");
        int idEnterpriseColIndex = cPlomb.getColumnIndex("id_enterprise");
        int idLocoSeriaColIndex = cPlomb.getColumnIndex("id_loco_seria");
        int idLocoNumColIndex = cPlomb.getColumnIndex("id_loco_num");
        int idPlaceSetColIndex = cPlomb.getColumnIndex("id_place_set");
        int dateOffColIndex = cPlomb.getColumnIndex("date_off");
        int idAdjusterColIndex = cPlomb.getColumnIndex("id_adjuster");
        int causeOffColIndex = cPlomb.getColumnIndex("cause_off");
        int idGetterColIndex = cPlomb.getColumnIndex("id_getter");
        int commentColIndex = cPlomb.getColumnIndex("comment");
        int dateStockedColIndex = cPlomb.getColumnIndex("date_stocked");

        while(cPlomb.moveToNext())
        {
            Plomb plomb = new Plomb(cPlomb.getString(idColIndex), cPlomb.getString(plombNumColIndex), cPlomb.getString(idWhomIssueColIndex),
                    cPlomb.getString(dateIssueColIndex), cPlomb.getString(dateSetColIndex), cPlomb.getString(idEnterpriseColIndex), cPlomb.getString(idLocoSeriaColIndex),
                    cPlomb.getString(idLocoNumColIndex), cPlomb.getString(idPlaceSetColIndex), cPlomb.getString(dateOffColIndex), cPlomb.getString(idAdjusterColIndex),
                    cPlomb.getString(causeOffColIndex), cPlomb.getString(idGetterColIndex), cPlomb.getString(commentColIndex), cPlomb.getString(dateStockedColIndex));
            add(plomb);
        }

        db.close();
        cPlomb.close();
    }

    public void add(Plomb plomb)
    {
        this.plombCollection.add(plomb);
    }

    public void updatePlomb_num(String plomb_num, int index)
    {
        this.plombCollection.get(index).setPlomb_num(plomb_num);
    }

    public void updateDate_issue(String date_issue, int index)
    {
        this.plombCollection.get(index).setDate_issue(date_issue);
    }

    public void updateIdWhom_issue(String id_whom_issue, int index)
    {
        this.plombCollection.get(index).setIdWhom_issue(id_whom_issue);
    }

    public void updateDate_set(String date_set, int index)
    {
        this.plombCollection.get(index).setDate_set(date_set);
    }

    public void updateIdEnterprise(String id_enterprise, int index)
    {
        this.plombCollection.get(index).setIdEnterprise(id_enterprise);
    }

    public void updateIdLoco_seria(String id_loco_seria, int index)
    {
        this.plombCollection.get(index).setIdLoco_seria(id_loco_seria);
    }

    public void updateIdLoco_num(String id_loco_num, int index)
    {
        this.plombCollection.get(index).setIdLoco_num(id_loco_num);
    }

    public void updateIdPlace_set(String id_place_set, int index)
    {
        this.plombCollection.get(index).setIdPlace_set(id_place_set);
    }

    public void updateDate_off(String date_off, int index)
    {
        this.plombCollection.get(index).setDate_off(date_off);
    }

    public void updateIdAdjuster(String id_adjuster, int index)
    {
        this.plombCollection.get(index).setIdAdjuster(id_adjuster);
    }

    public void updateCause_off(String cause_off, int index)
    {
        this.plombCollection.get(index).setCause_off(cause_off);
    }

    public void updateIdGetter(String id_getter, int index)
    {
        this.plombCollection.get(index).setIdGetter(id_getter);
    }

    public void updateComment(String comment, int index)
    {
        this.plombCollection.get(index).setComment(comment);
    }

    public void updateDate_stocked(String date_stocked, int index)
    {
        this.plombCollection.get(index).setDate_stocked(date_stocked);
    }
}
