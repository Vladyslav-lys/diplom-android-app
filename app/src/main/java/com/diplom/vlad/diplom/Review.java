package com.diplom.vlad.diplom;

import android.widget.EditText;
import android.widget.Spinner;

public class Review {
    private PlombData pd;
    private int currentIndex;

    public Review(PlombData pd)
    {
        this.pd = pd;
        currentIndex = 0;
    }

    public void getNext()
    {
        currentIndex += 1;
    }

    public void getPrev()
    {
        currentIndex -= 1;
    }

    public int getCurrentIndex()
    {
        return currentIndex;
    }

    //private int getIndexBySpinner(Spinner spinner, String value)
    //{
    //    for(int i = 0; i < spinner.getCount(); i++)
    //    {
    //        if(spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value))
    //        {
    //            return i;
    //        }
    //    }
    //    return -1;
    //}

    public void printData(EditText textId, EditText plombNum, EditText dateIssue, Spinner whomIssue, EditText dateSet,
                          Spinner enterprise, Spinner seria, Spinner locoNum, Spinner setPlace, EditText dateOff,
                          Spinner adjuster, EditText causeOff, Spinner getter, EditText comment, EditText dateStocked)
    {
        textId.setText(pd.getByIndex(currentIndex).getId());
        plombNum.setText(pd.getByIndex(currentIndex).getPlomb_num());
        dateIssue.setText(pd.getByIndex(currentIndex).getDate_issue());
        whomIssue.setSelection(Integer.parseInt(pd.getByIndex(currentIndex).getIdWhom_issue()));
        dateSet.setText(pd.getByIndex(currentIndex).getDate_set());
        enterprise.setSelection(Integer.parseInt(pd.getByIndex(currentIndex).getIdEnterprise()));
        seria.setSelection(Integer.parseInt(pd.getByIndex(currentIndex).getIdLoco_seria()));
        locoNum.setSelection(Integer.parseInt(pd.getByIndex(currentIndex).getIdLoco_num()));
        setPlace.setSelection(Integer.parseInt(pd.getByIndex(currentIndex).getIdPlace_set()));
        dateOff.setText(pd.getByIndex(currentIndex).getDate_off());
        adjuster.setSelection(Integer.parseInt(pd.getByIndex(currentIndex).getIdAdjuster()));
        causeOff.setText(pd.getByIndex(currentIndex).getCause_off());
        getter.setSelection(Integer.parseInt(pd.getByIndex(currentIndex).getIdGetter()));
        comment.setText(pd.getByIndex(currentIndex).getComment());
        dateStocked.setText(pd.getByIndex(currentIndex).getDate_stocked());
    }

    public void printDefault(EditText textId, EditText plombNum, EditText dateIssue, Spinner whomIssue, EditText dateSet,
                             Spinner enterprise, Spinner seria, Spinner locoNum, Spinner setPlace, EditText dateOff,
                             Spinner adjuster, EditText causeOff, Spinner getter, EditText comment, EditText dateStocked)
    {
        textId.setText("");
        plombNum.setText("");
        dateIssue.setText("");
        whomIssue.setSelection(0);
        dateSet.setText("");
        enterprise.setSelection(0);
        seria.setSelection(0);
        locoNum.setSelection(0);
        setPlace.setSelection(0);
        dateOff.setText("");
        adjuster.setSelection(0);
        causeOff.setText("");
        getter.setSelection(0);
        comment.setText("");
        dateStocked.setText("");
    }

    public boolean checkSwitch(boolean right)
    {
        if(right && currentIndex < pd.getSize()-1)
        {
            return true;
        }
        else if(!right && currentIndex > 0)
        {
            return true;
        }
        return false;
    }
}
