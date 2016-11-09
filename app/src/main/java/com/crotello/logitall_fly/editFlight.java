/*
 * Copyright (c) 2016. Crotello.  Please do not copy without authorisation.
 */

package com.crotello.logitall_fly;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.crotello.logitall_fly.database.FlightsContract;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


//todo: Add comments.

public class editFlight extends AppCompatActivity {

    private FlightsContract dbManager;
    private TextView theActualTimeOfDeparture, theFlightNumber, theDepartureDate, theArrivalDate, theActualTimeOfArrival, theTotalFlightTime;
    private FlightDetails theFlight;
    private Boolean departDateSet = Boolean.FALSE, departTimeSet = Boolean.FALSE, arrivalDateSet = Boolean.FALSE, arrivalTimeSet = Boolean.FALSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_modify_flight);

        //final Context context = getApplicationContext();
        dbManager = new FlightsContract(this);
        dbManager.open();

        theFlight = new FlightDetails();
        // Create some variables that represent the TextViews we are
        // going to retrieve data from later.
        theFlightNumber = (TextView) findViewById(R.id.setFlightNumber);
        theDepartureDate = (TextView) findViewById(R.id.setFlightDepartureDate);
        theArrivalDate = (TextView) findViewById(R.id.setFlightArrivalDate);
        theActualTimeOfDeparture = (TextView) findViewById(R.id.setFlightAtd);
        theActualTimeOfArrival = (TextView) findViewById(R.id.setFlightAta);
        theTotalFlightTime = (TextView) findViewById(R.id.totalFlightTime);

        theTotalFlightTime.setVisibility(View.INVISIBLE);

        setTheTotalFlightTimeText();

        theDepartureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To show current date in the datepicker (theFlight is initialised with today's date).
                final Calendar theCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.getDefault());

                theCalendar.setTimeInMillis(theFlight.getDepartureDate());

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
                        // We do not want any element of time in the return so set the Calendar to 0.
                        // The value returned can only be the date selected.
                        theCalendar.setTimeInMillis(0);
                        theCalendar.set(selectedYear, (selectedMonth), selectedDay);

                        theDepartureDate.setText(dateFormat.format(theCalendar.getTimeInMillis()));
                        theFlight.setDepartureDate(theCalendar.getTimeInMillis());
                        departDateSet = Boolean.TRUE;

                        // Set arrival date to the same.
                        theArrivalDate.setText(dateFormat.format(theCalendar.getTimeInMillis()));
                        theFlight.setArrivalDate(theCalendar.getTimeInMillis());
                        arrivalDateSet = Boolean.TRUE;
                        setTheTotalFlightTimeText();
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Departure Date");
                mDatePicker.show();
            }
        });

        theArrivalDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //To show current date in the datepicker (theFlight is initialised with today's date).
                final Calendar theCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"),Locale.getDefault());

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

                        theArrivalDate.setText(dateFormat.format(theCalendar.getTimeInMillis()));
                        theFlight.setArrivalDate(theCalendar.getTimeInMillis());
                        arrivalDateSet = Boolean.TRUE;
                        setTheTotalFlightTimeText();

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Arrival Date");
                mDatePicker.show();
            }
        });


        theActualTimeOfDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar theCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.getDefault());

                final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                theCalendar.setTimeInMillis(theFlight.getAtd());
                int hour = theCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = theCalendar.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(editFlight.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        theCalendar.setTimeInMillis(0);

                        theCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        theCalendar.set(Calendar.MINUTE, selectedMinute);

                        theActualTimeOfDeparture.setText(timeFormat.format(theCalendar.getTimeInMillis()));
                        theFlight.setAtd(theCalendar.getTimeInMillis());
                        departTimeSet = Boolean.TRUE;

                        theActualTimeOfArrival.setText(timeFormat.format(theCalendar.getTimeInMillis()));
                        theFlight.setAta(theCalendar.getTimeInMillis());

                        arrivalTimeSet = Boolean.TRUE;
                        setTheTotalFlightTimeText();

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Departure Time");
                mTimePicker.show();
            }
        });


        theActualTimeOfArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar theCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.getDefault());

                final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

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

                        theActualTimeOfArrival.setText(timeFormat.format(theCalendar.getTimeInMillis()));
                        theFlight.setAta(theCalendar.getTimeInMillis());
                        arrivalTimeSet = Boolean.TRUE;
                        setTheTotalFlightTimeText();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Arrival Time");
                mTimePicker.show();
            }
        });


    }

    private void setTheTotalFlightTimeText() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Calendar theCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"),Locale.getDefault());

        theCalendar.setTimeInMillis(theFlight.getFlightTimeTotal());

        // Force the update.
        theCalendar.getTime();

        theTotalFlightTime.setText(timeFormat.format(theCalendar.getTimeInMillis()));

        if (departDateSet & departTimeSet & arrivalDateSet & arrivalTimeSet) {
            theTotalFlightTime.setVisibility(View.VISIBLE);
        }
    }


    public void saveFlight(View view) throws IOException {
        Context context = getApplicationContext();
        if (departDateSet & departTimeSet & arrivalDateSet & arrivalTimeSet) {


            CharSequence text = "Flight Saved";
            int duration = Toast.LENGTH_SHORT;

            theFlight.setFlight_Number(theFlightNumber.getText().toString());

            dbManager.insert(theFlight);
            //Tidy up.
            dbManager.close();

            //Let the user know all is good.
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            //Go back to the list of flights
            Intent home_intent = new Intent(getApplicationContext(), FlightListActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(home_intent);
        } else {
            Toast toast = Toast.makeText(context, "All dates and times must be entered", Toast.LENGTH_LONG);
            toast.show();
        }
    }


}



