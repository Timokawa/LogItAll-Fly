package com.crotello.logitall_fly;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.crotello.logitall_fly.database.FlightsContract;


public class FlightListActivity extends AppCompatActivity {



    final String[] from = new String[]{
            FlightsContract.FlightEntry._ID,
            FlightsContract.FlightEntry.COLUMN_FLIGHT_NUMBER,
            FlightsContract.FlightEntry.COLUMN_DEPARTURE_DATE_AND_TIME,
            FlightsContract.FlightEntry.COLUMN_ARRIVAL_DATE_AND_TIME
    };
    // This details what will be displayed.
    // It is deliberately limited otherwise a whole flight will take up the screen.
    // The details given should be enough for the user to get all the detail they need.
    // If they want more, they can click on the flight.
    final int[] to = new int[]{
            R.id.flight_id_textView,
            R.id.flight_number_textView,
            R.id.departure_date_textView,
            R.id.arrival_date_textView,
            R.id.departure_time_textView,
            R.id.arrival_time_textView
    };
    private FlightsContract dbManager;
    private ListView listView;
    private FlightsCursorAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.flight_list_view);



        dbManager = new FlightsContract(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        listView = (ListView) findViewById(R.id.flight_list_view);
        listView.setEmptyView(findViewById(R.id.empty));

      //adapter = //new SimpleCursorAdapter(this, R.layout.flightdetails, cursor, from, to, 0);
        adapter = new FlightsCursorAdapter(this,cursor,R.layout.flightdetails,0);

        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // OnCLickListener For List Items
        // This listener is used so the user can update a flight.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.flight_id_textView);
                TextView flightNumberTextView = (TextView) view.findViewById(R.id.flight_number_textView);
                TextView flightDepartureDateTextView = (TextView) view.findViewById(R.id.departure_date_textView);
                TextView flightArrivalDateTextView = (TextView) view.findViewById(R.id.arrival_date_textView);
                TextView flightATDTextView = (TextView) view.findViewById(R.id.departure_time_textView);
                TextView flightATATextView = (TextView) view.findViewById(R.id.arrival_time_textView);

                Intent modify_intent = new Intent(getApplicationContext(), ModifyFlightActivity.class);
                modify_intent.putExtra("flightnumber", flightNumberTextView.getText().toString());
                modify_intent.putExtra("departuredate", flightDepartureDateTextView.getText().toString());
                modify_intent.putExtra("arrivaldate", flightArrivalDateTextView.getText().toString());
                modify_intent.putExtra("atd", flightATDTextView.getText().toString());
                modify_intent.putExtra("ata", flightATATextView.getText().toString());
                modify_intent.putExtra("id", idTextView.getText().toString());

                startActivity(modify_intent);
            }
        });
    }

    public void addFlightClicked(View view) {
        Intent intent = new Intent(this, addFlight.class);
        startActivity(intent);
    }
}