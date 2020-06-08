package com.diplom.vlad.diplom;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AddOnePlomb extends AppCompatActivity {
    private static final int REQUEST_PHONE = 1;
    private DBHelper dbHelper;
    private Button buttonAdd;
    private EditText plombNumber;
    private Spinner spinnerIssued;
    private CalendarView date_issued;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_one_plomb);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHelper = new DBHelper(this, "Plo");

        initButtons();
        initEditText();
        initSpinner();

        //setPermissionPhone();
        spinnerIssued.setSelection(dbHelper.getEmployeerIdByPhone(dbHelper,AddOnePlomb.this));
        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Record record = new Record(dbHelper);
                if(record.checkIsFilled(plombNumber, date_issued, spinnerIssued, AddOnePlomb.this)
                        && record.checkUser(spinnerIssued, AddOnePlomb.this))
                {
                    record.addPlomb(plombNumber.getText().toString(),spinnerIssued.getSelectedItemPosition(),new Date(date_issued.getDate()));

                    Toast toast = Toast.makeText(AddOnePlomb.this, "Объект успешно добавлен!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    private void setPermissionPhone()
    {
        if(ContextCompat.checkSelfPermission(AddOnePlomb.this,Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(AddOnePlomb.this,Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED)
        {
            Toast toast = Toast.makeText(AddOnePlomb.this, "Дайте доступ к данным телефона!", Toast.LENGTH_LONG);
            toast.show();
            ActivityCompat.requestPermissions(AddOnePlomb.this,new String[] {Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE);
            return;
        }
        TelephonyManager tManager = (TelephonyManager) getSystemService(AddOnePlomb.TELEPHONY_SERVICE);
        Toast toast = Toast.makeText(AddOnePlomb.this, tManager.getLine1Number(), Toast.LENGTH_LONG);
        toast.show();
    }

    private void initEditText()
    {
        plombNumber = (EditText) findViewById(R.id.plombNum);
        date_issued = (CalendarView) findViewById(R.id.calendarView1);
    }

    private void initButtons()
    {
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
    }

    private void initSpinner()
    {
        spinnerIssued = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dbHelper.getEmployeerData(dbHelper));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIssued.setEnabled(false);
        spinnerIssued.setAdapter(adapter);
    }
}
