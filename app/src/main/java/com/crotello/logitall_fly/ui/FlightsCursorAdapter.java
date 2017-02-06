package com.crotello.logitall_fly.ui;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.crotello.logitall_fly.R;
import com.crotello.logitall_fly.datamanipulation.FlightDetails;
import com.crotello.logitall_fly.datastorage.FlightsContract;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Tim on 11/09/2016.
 */
public class FlightsCursorAdapter extends ResourceCursorAdapter {
    public FlightsCursorAdapter(Context context, Cursor cursor, int layout, int flags) {
        super(context, layout, cursor, 0);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvID = (TextView) view.findViewById(R.id.flight_id_textView);
        TextView tvNumber = (TextView) view.findViewById(R.id.flight_number_textView);
        TextView tvDepDate = (TextView) view.findViewById(R.id.departure_date_textView);
        TextView tvArrDate = (TextView) view.findViewById(R.id.arrival_date_textView);
        TextView tvATD = (TextView) view.findViewById(R.id.departure_time_textView);
        TextView tvATA = (TextView) view.findViewById(R.id.arrival_time_textView);
        // Extract properties from cursor
        String ID = cursor.getString(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry._ID));
        String number = cursor.getString(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_FLIGHT_NUMBER));

        Long departureDate = cursor.getLong(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_DEPARTURE_DATE));
        Long departureTime = cursor.getLong(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_DEPARTURE_TIME));

        Long arrivalDate = cursor.getLong(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_ARRIVAL_DATE));
        Long arrivalTime = cursor.getLong(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_ARRIVAL_TIME));

        final Calendar theCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.getDefault());
        //theCalendar.setTimeInMillis(departureDateAndTime);
        // theCalendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        String departDate, departTime, arriveDate, arriveTime = null;

        departDate = dateFormat.format(departureDate);
        departTime = timeFormat.format(departureTime);

        arriveDate = dateFormat.format(arrivalDate);
        arriveTime = timeFormat.format(arrivalTime);

        // Populate fields with extracted properties
        tvID.setText(ID);
        tvNumber.setText(number);
        tvDepDate.setText(departDate);
        tvArrDate.setText(arriveDate);
        tvATD.setText(departTime);
        tvATA.setText(arriveTime);
    }

    public FlightDetails bindFlight(Cursor cursor) {
        FlightDetails theFlight = new FlightDetails();

        theFlight.set_id(cursor.getLong(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry._ID)));
        theFlight.setFlightNumber(cursor.getString(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_FLIGHT_NUMBER)));
        theFlight.setDepartureDate(cursor.getLong(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_DEPARTURE_DATE)));
        theFlight.setAtd(cursor.getLong(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_DEPARTURE_TIME)));
        theFlight.setArrivalDate(cursor.getLong(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_ARRIVAL_DATE)));
        theFlight.setAta(cursor.getLong(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_ARRIVAL_TIME)));
        theFlight.setFlightTimeTotal(cursor.getLong(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_FLIGHT_TIME_TOTAL)));
        theFlight.setFlightTimeDay(cursor.getLong(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_FLIGHT_TIME_DAY)));
        theFlight.setFlightTimeNight(cursor.getLong(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_FLIGHT_TIME_NIGHT)));
        theFlight.setRole(cursor.getString(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_ROLE)));
        theFlight.setPic(cursor.getString(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_PIC)));
        theFlight.setICAO_Departure(cursor.getInt(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_ICAO_Departure)));
        theFlight.setICAO_Destination(cursor.getInt(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_ICAO_Destination)));
        theFlight.setAircraft_Number(cursor.getString(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_Aircraft_Number)));
        theFlight.setAircraftType(cursor.getInt(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_Aircraft_Type)));
        theFlight.setValidToSave(Boolean.parseBoolean((cursor.getString(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_Aircraft_Type)))));

        return theFlight;
    }

}