package com.crotello.logitall_fly.ui;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.crotello.logitall_fly.R;
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
        Long departureDateAndTime = cursor.getLong(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_DEPARTURE_DATE_AND_TIME));
        Long arrivalDateAndTime = cursor.getLong(cursor.getColumnIndexOrThrow(FlightsContract.FlightEntry.COLUMN_ARRIVAL_DATE_AND_TIME));

        final Calendar theCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.getDefault());
        //theCalendar.setTimeInMillis(departureDateAndTime);
        // theCalendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        String departDate, departTime, arriveDate, arriveTime = null;

        departDate = dateFormat.format(departureDateAndTime);
        departTime = timeFormat.format(departureDateAndTime);

        arriveDate = dateFormat.format(arrivalDateAndTime);
        arriveTime = timeFormat.format(arrivalDateAndTime);

        // Populate fields with extracted properties
        tvID.setText(ID);
        tvNumber.setText(number);
        tvDepDate.setText(departDate);
        tvArrDate.setText(arriveDate);
        tvATD.setText(departTime);
        tvATA.setText(arriveTime);
    }
}