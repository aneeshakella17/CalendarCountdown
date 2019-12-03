package com.example.calendarcountdown;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            HomeScreen homeScreen = new HomeScreen();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_screen, homeScreen, "AddnamePane");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


        }

        setContentView(R.layout.activity_main);


    }


    public void add(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        TextView title = new TextView(MainActivity.this);
        title.setText("Add Event/Directory");
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        builder.setPositiveButton("Cancel", null);
        builder.setNegativeButton("Add Directory", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.fragment_add_directory,null));
                builder.show();
            }
        });







        builder.setNeutralButton("Add Event", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatePickerDialog dpd = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, final int year, final int month, final int day) {

                        TimePickerDialog tpd = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                showEditScreen(year, month, day, hour, minute);
                            }
                        }, Calendar.HOUR, Calendar.MINUTE, true);


                        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                showEditScreen(year, month, day, 0, 0);
                            }
                        });

                        tpd.show();
                        Toast.makeText(MainActivity.this, "Please enter a time", Toast.LENGTH_SHORT).show();
                    }

                }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
                Toast.makeText(MainActivity.this, "Please enter a date", Toast.LENGTH_SHORT).show();
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }



    public void showEditScreen(int year, int month, int day, int hour, int minute) {
        EditScreen es = new EditScreen();
        Bundle bundle = new Bundle();
        bundle.putInt("year", year);
        bundle.putInt("month", month);
        bundle.putInt("day", day);
        bundle.putInt("hour", hour);
        bundle.putInt("minute", minute);
        es.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_screen, es)
                .addToBackStack(null)
                .commit();

    }


}

