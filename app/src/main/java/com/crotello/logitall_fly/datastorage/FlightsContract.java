/*
 * Copyright (c) 2016. Crotello.  Please do not copy without authorisation.
 */

package com.crotello.logitall_fly.datastorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.crotello.logitall_fly.datamanipulation.FlightDetails;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class FlightsContract {

    private Context context;

    private SQLiteDatabase database;
    private com.crotello.logitall_fly.datastorage.FlightsDBHelper theDBHelper;

    private String[] columns = new String[]{
            FlightEntry._ID,
            FlightEntry.COLUMN_FLIGHT_NUMBER,
            FlightEntry.COLUMN_DEPARTURE_DATE,
            FlightEntry.COLUMN_DEPARTURE_TIME,
            FlightEntry.COLUMN_ARRIVAL_DATE,
            FlightEntry.COLUMN_ARRIVAL_TIME,
            FlightEntry.COLUMN_FLIGHT_TIME_NIGHT,
            FlightEntry.COLUMN_FLIGHT_TIME_DAY,
            FlightEntry.COLUMN_FLIGHT_TIME_TOTAL,
            FlightEntry.COLUMN_ROLE,
            FlightEntry.COLUMN_PIC,
            FlightEntry.COLUMN_Aircraft_Type,
            FlightEntry.COLUMN_Aircraft_Number,
            FlightEntry.COLUMN_ICAO_Departure,
            FlightEntry.COLUMN_ICAO_Destination};


    public static final class FlightEntry implements BaseColumns {
        // Table Name
        public static final String TABLE_NAME = "flights";

        // Flight number will be stored as text as I have no idea of the different formats of flight numbers.
        public static final String COLUMN_FLIGHT_NUMBER = "Flight_Number";

        // Dates are stored as Long
        public static final String COLUMN_DEPARTURE_DATE = "Departure_Date";
        public static final String COLUMN_DEPARTURE_TIME = "Departure_Time";

        public static final String COLUMN_ARRIVAL_DATE = "Arrival_Date";
        public static final String COLUMN_ARRIVAL_TIME = "Arrival_Time";

        public static final String COLUMN_FLIGHT_TIME_NIGHT = "Night_Hours";
        public static final String COLUMN_FLIGHT_TIME_DAY = "Day_Hours";
        public static final String COLUMN_FLIGHT_TIME_TOTAL = "Total_Hours";

        public static final String COLUMN_ROLE = "Role";
        public static final String COLUMN_PIC = "PIC";

        public static final String COLUMN_Aircraft_Type = "Aircraft_Type";
        public static final String COLUMN_Aircraft_Number = "Aircraft_Number";

        public static final String COLUMN_ICAO_Destination = "ICAO_Destination";
        public static final String COLUMN_ICAO_Departure = "ICAO_Departure";


    }

    public FlightsContract(Context c) {
        String theDB = com.crotello.logitall_fly.datastorage.FlightsDBHelper.DB_NAME;
        int theVersion = com.crotello.logitall_fly.datastorage.FlightsDBHelper.DB_VERSION;
        theDBHelper = new com.crotello.logitall_fly.datastorage.FlightsDBHelper(c);

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
        contentValue.put(FlightEntry.COLUMN_DEPARTURE_DATE, theFlight.getDepartureDate() );
        contentValue.put(FlightEntry.COLUMN_DEPARTURE_TIME, theFlight.getAtd() );
        contentValue.put(FlightEntry.COLUMN_ARRIVAL_DATE, theFlight.getArrivalDate());
        contentValue.put(FlightEntry.COLUMN_ARRIVAL_TIME, theFlight.getAta());
        contentValue.put(FlightEntry.COLUMN_FLIGHT_TIME_NIGHT, theFlight.getFlightTimeNight());
        contentValue.put(FlightEntry.COLUMN_FLIGHT_TIME_DAY, theFlight.getFlightTimeDay());
        contentValue.put(FlightEntry.COLUMN_FLIGHT_TIME_TOTAL, theFlight.getFlightTimeTotal());
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

        Cursor cursor = database.query(FlightEntry.TABLE_NAME, columns, null, null, null, null, FlightEntry.COLUMN_DEPARTURE_DATE + " DESC");

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

        contentValues.put(FlightEntry.COLUMN_DEPARTURE_DATE, theFlight.getDepartureDate());
        contentValues.put(FlightEntry.COLUMN_DEPARTURE_TIME, theFlight.getAtd());

        contentValues.put(FlightEntry.COLUMN_ARRIVAL_DATE, theFlight.getArrivalDate());
        contentValues.put(FlightEntry.COLUMN_ARRIVAL_TIME, theFlight.getAta());

        contentValues.put(FlightEntry.COLUMN_FLIGHT_TIME_DAY, timeFormat.format(theFlight.getFlightTimeDay()));
        contentValues.put(FlightEntry.COLUMN_FLIGHT_TIME_NIGHT, timeFormat.format(theFlight.getFlightTimeNight()));
        contentValues.put(FlightEntry.COLUMN_FLIGHT_TIME_TOTAL, timeFormat.format(theFlight.getFlightTimeTotal()));

        contentValues.put(FlightEntry.COLUMN_ROLE, theFlight.getRole());
        contentValues.put(FlightEntry.COLUMN_PIC, theFlight.getPic());

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
        database.delete(FlightEntry.TABLE_NAME, FlightEntry._ID + "=" + Long.toString(_id), null);

    }

    public Cursor getFlight (long _id) {
       // FlightDetails theFlight = new FlightDetails();
        String whereClause = FlightEntry._ID + " = "  + Long.toString(_id);


        Cursor cursor =  database.query(FlightEntry.TABLE_NAME,  columns, whereClause, null, null, null,FlightEntry._ID);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

}
