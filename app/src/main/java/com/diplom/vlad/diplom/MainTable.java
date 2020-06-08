package com.diplom.vlad.diplom;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainTable extends AppCompatActivity {

    private DBHelper dbHelper;
    private PlombData pd;
    private Review review;
    private AlertDialog alertDialogFilter;

    private EditText textId;
    private EditText plombNum;
    private EditText dateIssue;
    private Spinner whomIssue;
    private EditText dateSet;
    private Spinner enterprise;
    private Spinner seria;
    private Spinner locoNum;
    private Spinner setPlace;
    private EditText dateOff;
    private Spinner adjuster;
    private EditText causeOff;
    private Spinner getter;
    private EditText comment;
    private EditText dateStocked;

    private Button buttonPrev;
    private Button buttonNext;
    private MenuItem stopFilter;

    private String[] typeValues = {"Дата выдачи","Дата установки","Предприятие","Серия","Номер",
            "Место установки","Дата снятия","Дата сдачи на склад"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_table);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHelper = new DBHelper(this, "Plo");

        initButtons();
        initEditText();
        initSpinner();

        pd = new PlombData(dbHelper);
        review = new Review(pd);
        printData();

        buttonPrev.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (review.checkSwitch(false)) {
                    review.getPrev();
                    printData();
                    return;
                }
                Toast toast = Toast.makeText(MainTable.this, "Вы на первой позиции!", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (review.checkSwitch(true)) {
                    review.getNext();
                    printData();
                    return;
                }
                Toast toast = Toast.makeText(MainTable.this, "Вы на последней позиции!", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        locoNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seria.setSelection(dbHelper.getLocoTypeIdByLocoId(dbHelper,String.valueOf(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        stopFilter = (MenuItem) menu.findItem(R.id.stopFilter);
        stopFilter.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.update:
                if(pd != null)
                {
                    DataChanger dc = new DataChanger(pd, dbHelper);
                    dc.setDataToUpdate(
                            plombNum.getText().toString(),
                            dateIssue.getText().toString(),
                            String.valueOf(whomIssue.getSelectedItemPosition()),
                            dateSet.getText().toString(),
                            String.valueOf(enterprise.getSelectedItemPosition()),
                            String.valueOf(seria.getSelectedItemPosition()),
                            String.valueOf(locoNum.getSelectedItemPosition()),
                            String.valueOf(setPlace.getSelectedItemPosition()),
                            dateOff.getText().toString(),
                            String.valueOf(adjuster.getSelectedItemPosition()),
                            causeOff.getText().toString(),
                            String.valueOf(getter.getSelectedItemPosition()),
                            comment.getText().toString(),
                            dateStocked.getText().toString(),
                            review.getCurrentIndex(),
                            MainTable.this);
                    dc.updateDatabase(String.valueOf(review.getCurrentIndex()+1));
                }
                break;
            case R.id.filter:
                OpenDialog();
                break;
            case R.id.stopFilter:
                review = new Review(pd);
                printData();
                stopFilter.setVisible(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void OpenDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_filter,null);
        final AutoCompleteTextView textValue = (AutoCompleteTextView) mView.findViewById(R.id.textValue);
        final Spinner spinnerTypes = (Spinner) mView.findViewById(R.id.spinnerTypes);
        ArrayAdapter<String> adapterSpinnerTypes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeValues);
        adapterSpinnerTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypes.setAdapter(adapterSpinnerTypes);

        spinnerTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<String> adapterEnterprise = new ArrayAdapter<String>(MainTable.this,android.R.layout.simple_spinner_item, dbHelper.getEnterpriseData(dbHelper));
                ArrayAdapter<String> adapterLocoTypes = new ArrayAdapter<String>(MainTable.this,android.R.layout.simple_spinner_item, dbHelper.getLocoTypesData(dbHelper));
                ArrayAdapter<String> adapterLoco = new ArrayAdapter<String>(MainTable.this,android.R.layout.simple_spinner_item, dbHelper.getLocoData(dbHelper));
                ArrayAdapter<String> adapterSetPlace = new ArrayAdapter<String>(MainTable.this,android.R.layout.simple_spinner_item, dbHelper.getSetPlaceData(dbHelper));
                switch (position)
                {
                    case 0:
                        textValue.setAdapter(null);
                        break;
                    case 1:
                        textValue.setAdapter(null);
                        break;
                    case 2:
                        textValue.setAdapter(adapterEnterprise);
                        break;
                    case 3:
                        textValue.setAdapter(adapterLocoTypes);
                        break;
                    case 4:
                        textValue.setAdapter(adapterLoco);
                        break;
                    case 5:
                        textValue.setAdapter(adapterSetPlace);
                        break;
                    case 6:
                        textValue.setAdapter(null);
                        break;
                    case 7:
                        textValue.setAdapter(null);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Filter filter = new Filter(pd,dbHelper);
                if(filter.checkIsFilled(textValue.getText().toString(),MainTable.this)
                        && filter.checkDateFormat(spinnerTypes.getSelectedItemPosition(), textValue.getText().toString(),MainTable.this)
                        && filter.checkDataExist(spinnerTypes.getSelectedItemPosition(),textValue.getText().toString(),MainTable.this))
                {
                    review = new Review(filter.filterData(spinnerTypes.getSelectedItemPosition(), textValue.getText().toString()));
                    printData();
                    stopFilter.setVisible(true);
                }
            }
        });

        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialogFilter.cancel();
            }
        });

        builder.setView(mView);
        alertDialogFilter = builder.create();
        alertDialogFilter.show();
    }

    private void printData()
    {
        if(!dbHelper.checkTableIsNotEmpty("plombtable"))
        {
            review.printDefault(
                    textId,
                    plombNum,
                    dateIssue,
                    whomIssue,
                    dateSet,
                    enterprise,
                    seria,
                    locoNum,
                    setPlace,
                    dateOff,
                    adjuster,
                    causeOff,
                    getter,
                    comment,
                    dateStocked);
            Toast toast = Toast.makeText(MainTable.this, "Записи отсутствуют!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        review.printData(
                textId,
                plombNum,
                dateIssue,
                whomIssue,
                dateSet,
                enterprise,
                seria,
                locoNum,
                setPlace,
                dateOff,
                adjuster,
                causeOff,
                getter,
                comment,
                dateStocked);
    }

    private void initButtons()
    {
        buttonPrev = (Button) findViewById(R.id.buttonPrev);
        buttonNext = (Button) findViewById(R.id.buttonNext);
    }

    private void initEditText()
    {
        textId = (EditText) findViewById(R.id.textId);
        plombNum = (EditText) findViewById(R.id.plombNum);
        dateIssue = (EditText) findViewById(R.id.dateIssue);
        dateSet = (EditText) findViewById(R.id.dateSet);
        dateOff = (EditText) findViewById(R.id.dateOff);
        comment = (EditText) findViewById(R.id.comment);
        dateStocked = (EditText) findViewById(R.id.stocked);
        causeOff = (EditText) findViewById(R.id.causeOff);
    }

    private void initSpinner()
    {
        whomIssue = (Spinner) findViewById(R.id.whomIssue);
        ArrayAdapter<String> adapterWhomIssue = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dbHelper.getEmployeerData(dbHelper));
        adapterWhomIssue.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        whomIssue.setAdapter(adapterWhomIssue);
        whomIssue.setEnabled(false);

        enterprise = (Spinner) findViewById(R.id.enterprise);
        ArrayAdapter<String> adapterEnterprise = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dbHelper.getEnterpriseData(dbHelper));
        adapterEnterprise.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        enterprise.setAdapter(adapterEnterprise);

        seria = (Spinner) findViewById(R.id.seria);
        ArrayAdapter<String> adapterSeria = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dbHelper.getLocoTypesData(dbHelper));
        adapterEnterprise.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seria.setAdapter(adapterSeria);
        seria.setEnabled(false);

        locoNum = (Spinner) findViewById(R.id.locoNum);
        ArrayAdapter<String> adapterLocoNum = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dbHelper.getLocoData(dbHelper));
        adapterEnterprise.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locoNum.setAdapter(adapterLocoNum);

        setPlace = (Spinner) findViewById(R.id.setPlace);
        ArrayAdapter<String> adapterSetPlace= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dbHelper.getSetPlaceData(dbHelper));
        adapterEnterprise.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setPlace.setAdapter(adapterSetPlace);

        adjuster = (Spinner) findViewById(R.id.adjuster);
        ArrayAdapter<String> adapterAdjuster = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dbHelper.getEmployeerData(dbHelper));
        adapterWhomIssue.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adjuster.setAdapter(adapterAdjuster);
        adjuster.setEnabled(false);

        getter = (Spinner) findViewById(R.id.getter);
        ArrayAdapter<String> adapterGetter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dbHelper.getEmployeerData(dbHelper));
        adapterWhomIssue.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getter.setAdapter(adapterGetter);
        getter.setEnabled(false);
    }

}

//int idColIndex = cPlomb.getColumnIndex("id");
//int plombNumColIndex = cPlomb.getColumnIndex("plomb_num");
//int dateIssueColIndex = cPlomb.getColumnIndex("date_issue");
//int whomIssueIssueColIndex = cPlomb.getColumnIndex("whom_issue");
//int dateSetColIndex = cPlomb.getColumnIndex("date_set");
//int enterpriseColIndex = cPlomb.getColumnIndex("enterprise");
//int locoSeriaColIndex = cPlomb.getColumnIndex("loco_seria");
//int locoNumColIndex = cPlomb.getColumnIndex("loco_num");
//int placeSetColIndex = cPlomb.getColumnIndex("place_set");
//int dateOffColIndex = cPlomb.getColumnIndex("date_off");
//int adjusterColIndex = cPlomb.getColumnIndex("adjuster");
//int causeOffColIndex = cPlomb.getColumnIndex("cause_off");
//int getterColIndex = cPlomb.getColumnIndex("getter");
//int commentColIndex = cPlomb.getColumnIndex("comment");
//int dateStockedColIndex = cPlomb.getColumnIndex("date_stocked");

//textId.setText(cPlomb.getString(idColIndex));
//plombNum.setText(cPlomb.getString(plombNumColIndex));
//dateIssue.setText(cPlomb.getString(dateIssueColIndex));
//whomIssue.setSelection(getIndex(whomIssue,cPlomb.getString(whomIssueIssueColIndex)));
//dateSet.setText(cPlomb.getString(dateSetColIndex));
//enterprise.setSelection(getIndex(enterprise,cPlomb.getString(enterpriseColIndex)));
//seria.setSelection(getIndex(seria,cPlomb.getString(locoSeriaColIndex)));
//locoNum.setSelection(getIndex(locoNum,cPlomb.getString(locoNumColIndex)));
//setPlace.setSelection(getIndex(setPlace,cPlomb.getString(placeSetColIndex)));
//dateOff.setText(cPlomb.getString(dateOffColIndex));
//adjuster.setSelection(getIndex(adjuster,cPlomb.getString(adjusterColIndex)));
//causeOff.setText(cPlomb.getString(causeOffColIndex));
//getter.setSelection(getIndex(getter,cPlomb.getString(getterColIndex)));
//comment.setText(cPlomb.getString(commentColIndex));
//dateStocked.setText(cPlomb.getString(dateStockedColIndex));