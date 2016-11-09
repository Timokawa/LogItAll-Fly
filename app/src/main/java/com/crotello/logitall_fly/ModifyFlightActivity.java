package com.crotello.logitall_fly;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crotello.logitall_fly.database.FlightsContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Tim on 11/09/2016.
 */

public class ModifyFlightActivity extends AppCompatActivity implements View.OnClickListener {
    //TODO: This needsto be modified to use it's own time/date pickers like AddFLight.
    // TODO: Or I will re-do those as seperate classes
    // TODO: This appears to be subtracting two hours.
    private Button updateBtn, deleteBtn;
    private EditText modifyFlightNumberText;
    private TextView modifyFlightID, modifyArrivalDateText, modifyDepartureDateText, modifyFlightATDText, modifyFlightATAText;
    ;
    private FlightDetails theFlight;
    private long _id;

    private FlightsContract dbManager;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private Calendar theCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"),Locale.getDefault());

    //todo: COMMENT THIS ALL!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify Record");

        setContentView(R.layout.activity_modify_flight);

        dbManager = new FlightsContract(this);
        dbManager.open();

        // We are going to Modify this flight once we have populated it.
        theFlight = new FlightDetails();
        // Populate the flight with all the possible values.


        // Instantiate some TextViews to use.
        modifyFlightID = (TextView) findViewById(R.id.flight_id_textView);
        modifyFlightNumberText = (EditText) findViewById(R.id.setFlightNumber);
        modifyDepartureDateText = (TextView) findViewById(R.id.setFlightDepartureDate);
        modifyArrivalDateText = (TextView) findViewById(R.id.setFlightArrivalDate);
        modifyFlightATDText = (TextView) findViewById(R.id.setFlightAtd);
        modifyFlightATAText = (TextView) findViewById(R.id.setFlightAta);

        updateBtn = (Button) findViewById(R.id.btn_update);
        deleteBtn = (Button) findViewById(R.id.btn_delete);

        //TODO:Where does this all come from?
        Intent intent = getIntent();
        String flightId = intent.getStringExtra("id");
        String flightNumber = intent.getStringExtra("flightnumber");
        String departureDate = intent.getStringExtra("departuredate");
        String arrivalDate = intent.getStringExtra("arrivaldate");
        String flightATD = intent.getStringExtra("atd");
        String flightATA = intent.getStringExtra("ata");


        modifyFlightID.setText(flightId);
        modifyFlightNumberText.setText(flightNumber);
        modifyDepartureDateText.setText(departureDate);
        modifyArrivalDateText.setText(arrivalDate);
        modifyFlightATDText.setText(flightATD);
        modifyFlightATAText.setText(flightATA);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        modifyFlightATDText.setOnClickListener(this);
        modifyFlightATAText.setOnClickListener(this);
        modifyDepartureDateText.setOnClickListener(this);
        modifyArrivalDateText.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        Context context = getApplicationContext();
        DialogFragment timeFragment = new TimePickerFragment();
        DialogFragment dateFragment = new DatePickerFragment();
        long theID = 0;
        Bundle theBundle = new Bundle();
        String TimeChanged = null;
        String DateChanged = null;

        switch (v.getId()) {

            case R.id.btn_update:
                CharSequence text = "Flight Saved";
                int duration = Toast.LENGTH_SHORT;

                theFlight.set_id((Long.parseLong(modifyFlightID.getText().toString())));

                theCalendar.setTimeInMillis(0);

                theFlight.setFlight_Number(modifyFlightNumberText.getText().toString());

                // Departure Date
                try {
                    theCalendar.setTime(dateFormat.parse(modifyDepartureDateText.getText().toString()));// all done
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                theFlight.setDepartureDate(theCalendar.getTimeInMillis());

                // Arrival Date
                try {
                    theCalendar.setTime(dateFormat.parse(modifyArrivalDateText.getText().toString()));// all done
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                theFlight.setArrivalDate(theCalendar.getTimeInMillis());

                // Departure Time
                try {
                    theCalendar.setTime(timeFormat.parse(modifyFlightATDText.getText().toString()));// all done
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                theFlight.setAtd(theCalendar.getTimeInMillis());

                // Arrival Time
                try {
                    theCalendar.setTime(timeFormat.parse(modifyFlightATAText.getText().toString()));// all done
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                theFlight.setAta(theCalendar.getTimeInMillis());

                String timeToToast = String.valueOf(text);

                // TODO: Pass  a FlightDetails class.
                dbManager.update(theFlight);
                dbManager.close();
                Toast toast = Toast.makeText(context, timeToToast, duration);
                toast.show();

                this.returnHome();
                break;

            case R.id.btn_delete:
                theID = Long.parseLong(modifyFlightID.getText().toString());
                dbManager.delete(theID);
                dbManager.close();
                this.returnHome();
                break;

            case R.id.setFlightAtd:
                //newFragment.setViewToUse(1);
                TimeChanged = "modifyAtd";

                //The bundle will be interrogated by getArguments in the TimePickerFragment onCreate()
                theBundle.putString("TimeChanged", TimeChanged);

                // TODO:  Would a Singleton be better?
                // I could just set the flight (see all the getText in BTN Update)
                // All the data of a FLightDetail is available
                timeFragment.setArguments(theBundle);
                timeFragment.show(getFragmentManager(), "timePickerAtd");
                // TODO: Could I extract the details from a FlightDetails here and repopulate the TextView?
                // TODO: Test if anything is executed before or after the TimeFragment is done. (Suspect break is executed straightaway.)
                break;

            case R.id.setFlightAta:

                TimeChanged = "modifyAta";

                //The bundle will be interrogated by getArguments in the TimePickerFragment onCreate()
                theBundle.putString("TimeChanged", TimeChanged);

                timeFragment.setArguments(theBundle);
                timeFragment.show(getFragmentManager(), "timePickerAta");

                break;

            case R.id.setFlightDepartureDate:
                DateChanged = "modifyDepartDate";
                //toSend = this;


                //The bundle will be interrogated by getArguments in the TimePickerFragment onCreate()
                //Todo: Send this
                theBundle.putString("DateChanged", DateChanged);


                dateFragment.setArguments(theBundle);
                dateFragment.show(getFragmentManager(), "datePickerDeparture");

                break;

            case R.id.setFlightArrivalDate:
                DateChanged = "modifyArriveDate";

                //THe bundle will be interrogated by getArguments in the TimePickerFragment onCreate()
                theBundle.putString("DateChanged", DateChanged);

                dateFragment.setArguments(theBundle);
                dateFragment.show(getFragmentManager(), "datePickerArrive");

                break;
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), FlightListActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }

    private void populateTheFlight(){
        theFlight.setFlight_Number(modifyFlightNumberText.getText().toString());

        // Departure Date
        try {
            theCalendar.setTime(dateFormat.parse(modifyDepartureDateText.getText().toString()));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }
        theFlight.setDepartureDate(theCalendar.getTimeInMillis());

        // Arrival Date
        try {
            theCalendar.setTime(dateFormat.parse(modifyArrivalDateText.getText().toString()));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }
        theFlight.setArrivalDate(theCalendar.getTimeInMillis());

        // Departure Time
        try {
            theCalendar.setTime(timeFormat.parse(modifyFlightATDText.getText().toString()));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }
        theFlight.setAtd(theCalendar.getTimeInMillis());

        // Arrival Time
        try {
            theCalendar.setTime(timeFormat.parse(modifyFlightATAText.getText().toString()));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }
        theFlight.setAta(theCalendar.getTimeInMillis());


    }
}

