/*
 * Copyright (c) 2016. Crotello.  Please do not copy without authorisation.
 */

package com.crotello.logitall_fly.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.crotello.logitall_fly.FlightDetails;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class FlightsContract {

    private Context context;

    private SQLiteDatabase database;
    private FlightsDBHelper theDBHelper;


    public static final class FlightEntry implements BaseColumns {
        // Table Name
        public static final String TABLE_NAME = "flights";

        // Flight number will be stored as text as I have no idea of the different formats of flight numbers.
        public static final String COLUMN_FLIGHT_NUMBER = "Flight_Number";

        // Dates are stored as Long
        public static final String COLUMN_DEPARTURE_DATE_AND_TIME = "Departure_Date_And_Time";
        public static final String COLUMN_ARRIVAL_DATE_AND_TIME = "Arrival_Date_And_Time";

        public static final String COLUMN_NIGHT_HOURS = "Night_Hours";
        public static final String COLUMN_DAY_HOURS = "Day_Hours";
        public static final String COLUMN_TOTAL_HOURS = "Total_Hours";

        public static final String COLUMN_ROLE = "Role";
        public static final String COLUMN_PIC = "PIC";

        public static final String COLUMN_Aircraft_Type = "Aircraft_Type";
        public static final String COLUMN_Aircraft_Number = "Aircraft_Number";

        public static final String COLUMN_ICAO_Destination = "ICAO_Destination";
        public static final String COLUMN_ICAO_Departure = "ICAO_Departure";


    }

    public FlightsContract(Context c) {
        String theDB = FlightsDBHelper.DB_NAME;
        int theVersion = FlightsDBHelper.DB_VERSION;
        theDBHelper = new FlightsDBHelper(c);

    }

    public void open() throws SQLException {
        database = theDBHelper.getWritableDatabase();
    }

    public void close() {

        theDBHelper.close();
    }


    public void insert(FlightDetails theFlight) {

        long theDepartureDateAndTimeToBeSaved = (theFlight.getDepartureDate() + theFlight.getAtd());
        long theArrivalDateAndTimeToBeSaved = (theFlight.getArrivalDate() + theFlight.getAta());
        ContentValues contentValue = new ContentValues();
        contentValue.put(FlightEntry.COLUMN_FLIGHT_NUMBER, theFlight.getFlight_Number());
        contentValue.put(FlightEntry.COLUMN_DEPARTURE_DATE_AND_TIME, theDepartureDateAndTimeToBeSaved);
        contentValue.put(FlightEntry.COLUMN_ARRIVAL_DATE_AND_TIME, theArrivalDateAndTimeToBeSaved);
        contentValue.put(FlightEntry.COLUMN_NIGHT_HOURS, theFlight.getFlightTimeNight());
        contentValue.put(FlightEntry.COLUMN_DAY_HOURS, theFlight.getFlightTimeDay());
        contentValue.put(FlightEntry.COLUMN_TOTAL_HOURS, theFlight.getFlightTimeTotal());
        contentValue.put(FlightEntry.COLUMN_ROLE, theFlight.getRole());
        contentValue.put(FlightEntry.COLUMN_PIC, theFlight.getRole());
        contentValue.put(FlightEntry.COLUMN_Aircraft_Type, theFlight.getAircraftType());
        contentValue.put(FlightEntry.COLUMN_Aircraft_Number, theFlight.getAircraft_Number());
        contentValue.put(FlightEntry.COLUMN_ICAO_Departure, theFlight.getICAO_Departure());
        contentValue.put(FlightEntry.COLUMN_ICAO_Destination, theFlight.getICAO_Destination());





        database.insert(FlightEntry.TABLE_NAME, null, contentValue);

    }

    // User of this method will get raw data.
    // It is for them to reformat as required.
    public Cursor fetch() {
        int howManyRows = 0;
        String[] columns = new String[]{
                FlightEntry._ID,
                FlightEntry.COLUMN_FLIGHT_NUMBER,
                FlightEntry.COLUMN_DEPARTURE_DATE_AND_TIME,
                FlightEntry.COLUMN_ARRIVAL_DATE_AND_TIME,
                FlightEntry.COLUMN_NIGHT_HOURS,
                FlightEntry.COLUMN_DAY_HOURS,
                FlightEntry.COLUMN_TOTAL_HOURS,
                FlightEntry.COLUMN_ROLE,
                FlightEntry.COLUMN_PIC,
                FlightEntry.COLUMN_Aircraft_Type,
                FlightEntry.COLUMN_Aircraft_Number,
                FlightEntry.COLUMN_ICAO_Departure,
                FlightEntry.COLUMN_ICAO_Destination};

        Cursor cursor = database.query(FlightEntry.TABLE_NAME, columns, null, null, null, null, FlightEntry.COLUMN_DEPARTURE_DATE_AND_TIME + " DESC");


        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }


    public int update(FlightDetails theFlight) {
        ContentValues contentValues = new ContentValues();

        //todo zulu/local
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        long theDepartureDateAndTimeToBeSaved = (theFlight.getDepartureDate() + theFlight.getAtd());
        long theArrivalDateAndTimeToBeSaved = (theFlight.getArrivalDate() + theFlight.getAta());

        Long _id = theFlight.get_id();
        contentValues.put(FlightEntry.COLUMN_FLIGHT_NUMBER, theFlight.getFlight_Number());

        contentValues.put(FlightEntry.COLUMN_DEPARTURE_DATE_AND_TIME, theDepartureDateAndTimeToBeSaved);
        contentValues.put(FlightEntry.COLUMN_ARRIVAL_DATE_AND_TIME, theArrivalDateAndTimeToBeSaved);

        contentValues.put(FlightEntry.COLUMN_DAY_HOURS, timeFormat.format(theFlight.getFlightTimeDay()));
        contentValues.put(FlightEntry.COLUMN_NIGHT_HOURS, timeFormat.format(theFlight.getFlightTimeNight()));
        contentValues.put(FlightEntry.COLUMN_TOTAL_HOURS, timeFormat.format(theFlight.getFlightTimeTotal()));

        contentValues.put(FlightEntry.COLUMN_ROLE, theFlight.getRole());
        contentValues.put(FlightEntry.COLUMN_PIC    , theFlight.getPic());

        contentValues.put(FlightEntry.COLUMN_Aircraft_Type, theFlight.getAircraftType());
        contentValues.put(FlightEntry.COLUMN_Aircraft_Number    , theFlight.getAircraft_Number());

        contentValues.put(FlightEntry.COLUMN_ICAO_Departure, theFlight.getICAO_Departure());
        contentValues.put(FlightEntry.COLUMN_ICAO_Destination    , theFlight.getICAO_Destination());


        int i = database.update(FlightEntry.TABLE_NAME, contentValues, FlightEntry._ID + " = " + _id, null);
        database.close();
        return i;

    }

    public void delete(long _id) {

        //
        database.delete(FlightEntry.TABLE_NAME, FlightEntry._ID + "=" + _id, null);

    }

}
