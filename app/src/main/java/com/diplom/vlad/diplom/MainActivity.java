package com.diplom.vlad.diplom;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    private Button buttonAdd;
    private Button buttonView;
    private AlertDialog alertDialogAddPlombs;
    private Class selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        buttonAdd = (Button) findViewById(R.id.buttonAddPlomb);
        buttonView = (Button) findViewById(R.id.buttonViewPlomb);

        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddOnePlomb.class);
                startActivity(intent);
            }
        });

        buttonView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainTable.class);
                startActivity(intent);
            }
        });
    }

    /*private void OpenDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setSingleChoiceItems(R.array.addPlombs, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:
                        selected = AddOnePlomb.class;
                        break;
                    case 1:
                        selected = AddSomePlombs.class;
                        break;
                }
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialogAddPlombs.cancel();
                ShowActivity(MainActivity.this, selected);
            }
        });

        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialogAddPlombs.cancel();
            }
        });

        alertDialogAddPlombs = builder.create();
        alertDialogAddPlombs.show();
    }*/

    private void ShowActivity(Activity current, Class next)
    {
        Intent intent = new Intent(current, next);
        startActivity(intent);
        finish();
    }
}
