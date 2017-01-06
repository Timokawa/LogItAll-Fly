/*
 * Copyright (c) 2016. Crotello.  Please do not copy without authorisation.
 */

package com.crotello.logitall_fly.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.crotello.logitall_fly.R;
import com.crotello.logitall_fly.datastorage.FlightsContract;
import com.crotello.logitall_fly.datamanipulation.FlightDetails;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


//todo: Add comments.

public class editFlight extends AppCompatActivity {

    private FlightsContract dbManager;
    private TextView theActualTimeOfDepartureTextView, theFlightNumberTextView, theDepartureDateTV, theArrivalDateTV, theActualTimeOfArrivalTextView, theFlightTimeTotalTextView;
    private FlightDetails theFlight;
    private Boolean departDateSet = Boolean.FALSE, flightNumberSet = Boolean.FALSE, arrivalDateSet = Boolean.FALSE, arrivalTimeSet = Boolean.FALSE;


    //TODO: Make this able to modify a flight too.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_flight);

        dbManager = new FlightsContract(this);
        dbManager.open();

        theFlight = new FlightDetails();
        // Create some variables that represent the TextViews we are
        // going to retrieve data from later.
        theFlightNumberTextView = (TextView) findViewById(R.id.setFlightNumber);
        theDepartureDateTV = (TextView) findViewById(R.id.setFlightDepartureDate);
        theArrivalDateTV = (TextView) findViewById(R.id.setFlightArrivalDate);
        theActualTimeOfDepartureTextView = (TextView) findViewById(R.id.setFlightAtd);
        theActualTimeOfArrivalTextView = (TextView) findViewById(R.id.setFlightAta);
        theFlightTimeTotalTextView = (TextView) findViewById(R.id.totalFlightTime);

        theFlightTimeTotalTextView.setVisibility(View.INVISIBLE);

        setTheFlightTimeTotalTextView();

        theDepartureDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To show current date in the datepicker (theFlight is initialised with today's date).
                final Calendar theCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.getDefault());

                theCalendar.setTimeInMillis(theFlight.getDepartureDate());

                // This will be used to populate the TextView

                // The initialised values for the dialog.
                int mYear = theCalendar.get(Calendar.YEAR);
                int mMonth = theCalendar.get(Calendar.MONTH);
                int mDay = theCalendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;

                mDatePicker = new DatePickerDialog(editFlight.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                        // We do not want any element of time in the return so set the Calendar to 0.
                        // The value returned can only be the date selected.
                        theCalendar.setTimeInMillis(0);
                        theCalendar.set(selectedYear, (selectedMonth), selectedDay);

                        theFlight.setDepartureDate(theCalendar.getTimeInMillis());
                        theDepartureDateTV.setText(theFlight.getFormattedDepartureDate());

                        // The arrival date is always going to be the same or later.
                        theFlight.setArrivalDate(theCalendar.getTimeInMillis());
                        theArrivalDateTV.setText(theFlight.getFormattedArrivalDate());

                        setTheFlightTimeTotalTextView();
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Departure Date");
                mDatePicker.show();
            }
        });

        theArrivalDateTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //To show current date in the datepicker (theFlight is initialised with today's date).
                final Calendar theCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.getDefault());

                theCalendar.setTimeInMillis(theFlight.getArrivalDate());

                // This will be used to populate the TextView
                final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                // The initialised values for the dialog.
                int mYear = theCalendar.get(Calendar.YEAR);
                int mMonth = theCalendar.get(Calendar.MONTH);
                int mDay = theCalendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;

                mDatePicker = new DatePickerDialog(editFlight.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                        theCalendar.setTimeInMillis(0);
                        theCalendar.set(selectedYear, (selectedMonth), selectedDay);

                        if (theCalendar.getTimeInMillis() < theFlight.getDepartureDate()) {// Arrival is before departure
                            // Set arrival same as departure
                            theCalendar.setTimeInMillis(theFlight.getDepartureDate());
                            Toast toast = Toast.makeText(editFlight.this, "Arrival date needs to be same or after Departure date. Changed.", Toast.LENGTH_LONG);
                            toast.show();
                        }

                        theFlight.setArrivalDate(theCalendar.getTimeInMillis());
                        theArrivalDateTV.setText(theFlight.getFormattedArrivalDate());

                        setTheFlightTimeTotalTextView();

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Arrival Date");
                mDatePicker.show();
            }
        });


        theActualTimeOfDepartureTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar theCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.getDefault());

                final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                theCalendar.setTimeInMillis(theFlight.getAtd());
                int hour = theCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = theCalendar.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker = new TimePickerDialog(editFlight.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        theCalendar.setTimeInMillis(0);

                        theCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        theCalendar.set(Calendar.MINUTE, selectedMinute);

                        theFlight.setAtd(theCalendar.getTimeInMillis());
                        theActualTimeOfDepartureTextView.setText(theFlight.getFormattedDepartureTime());

                        theFlight.setAta(theCalendar.getTimeInMillis());
                        theActualTimeOfArrivalTextView.setText(theFlight.getFormattedArrivalTime());

                        setTheFlightTimeTotalTextView();

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Departure Time");
                mTimePicker.show();
            }
        });


        theActualTimeOfArrivalTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar theCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.getDefault());

                theCalendar.setTimeInMillis(theFlight.getAta());
                int hour = theCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = theCalendar.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(editFlight.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        theCalendar.setTimeInMillis(0);
                        theCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        theCalendar.set(Calendar.MINUTE, selectedMinute);

                        if ((theCalendar.getTimeInMillis() < theFlight.getAtd()) & (theFlight.getDepartureDate() == theFlight.getArrivalDate())) {// Arrival is before departure
                            // Set arrival same as departure
                            theCalendar.setTimeInMillis(theFlight.getAtd());
                            Toast toast = Toast.makeText(editFlight.this, "Arrival time needs to be same or after Departure time. Changed.", Toast.LENGTH_LONG);
                            toast.show();
                        }

                        theFlight.setAta(theCalendar.getTimeInMillis());
                        theActualTimeOfArrivalTextView.setText(theFlight.getFormattedArrivalTime());
                        setTheFlightTimeTotalTextView();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Arrival Time");
                mTimePicker.show();
            }
        });
    }

    private void setTheFlightTimeTotalTextView() {

        if (theFlight.getFlightTimeTotal() > 0) {

            theFlightTimeTotalTextView.setVisibility(View.VISIBLE);
            theFlightTimeTotalTextView.setText(theFlight.getFormattedFlightTimeTotal());
        }
    }


    public void saveFlight(View view) throws IOException {
        theFlight.setFlight_Number(theFlightNumberTextView.getText().toString());

        Context context = getApplicationContext();

        if (theFlight.validToSave()) {
            dbManager.insert(theFlight);
            dbManager.close();

            Toast toast = Toast.makeText(context, "Flight Saved", Toast.LENGTH_SHORT);
            toast.show();

            Intent home_intent = new Intent(getApplicationContext(), FlightListActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(home_intent);

        } else {
            Toast toast = Toast.makeText(context, "The departure date and Flight Number must be entered.", Toast.LENGTH_LONG);
            toast.show();
        }
    }


    public void deleteFlight(View view) throws IOException {

        new AlertDialog.Builder(this)
                .setTitle("Delete Flight")
                .setMessage("Are you sure you want to delete this flight?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (theFlight.get_id() == null) {
                            // If the flight id is null then it's an "Add Flight" so just go back to the flight list.
                            Intent home_intent = new Intent(getApplicationContext(), FlightListActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(home_intent);
                        } else {
                            // The flight actually exists and we need to remove it from the database
                            dbManager.delete(theFlight.get_id());
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}





