package com.crotello.logitall_fly.datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.crotello.logitall_fly.datastorage.FlightsContract.FlightEntry;


/**
 * Created by Tim on 10/09/2016.
 */


public class FlightsDBHelper extends SQLiteOpenHelper {

    static final int DB_VERSION = 3;
    //Version 3 sets Dates and Times to seperate columns.

    // Database Information
    static final String DB_NAME = "flights.db";

    public FlightsDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create table query
        final String CREATE_FLIGHT_TABLE =
                "create table "
                        + FlightEntry.TABLE_NAME + "("
                        + FlightEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + FlightEntry.COLUMN_FLIGHT_NUMBER + " TEXT, "
                        + FlightEntry.COLUMN_DEPARTURE_DATE + " INTEGER NOT NULL, "
                        + FlightEntry.COLUMN_DEPARTURE_TIME + " INTEGER NOT NULL, "
                        + FlightEntry.COLUMN_ARRIVAL_DATE + " INTEGER NOT NULL, "
                        + FlightEntry.COLUMN_ARRIVAL_TIME + " INTEGER NOT NULL, "
                        + FlightEntry.COLUMN_FLIGHT_TIME_NIGHT + " INTEGER NOT NULL, "
                        + FlightEntry.COLUMN_FLIGHT_TIME_DAY + " INTEGER NOT NULL, "
                        + FlightEntry.COLUMN_FLIGHT_TIME_TOTAL + " INTEGER NOT NULL,"
                        + FlightEntry.COLUMN_ROLE + " INTEGER NOT NULL,"
                        + FlightEntry.COLUMN_PIC + " INTEGER NOT NULL,"
                        + FlightEntry.COLUMN_ICAO_Departure + " INTEGER NOT NULL,"
                        + FlightEntry.COLUMN_ICAO_Destination + " INTEGER NOT NULL,"
                        + FlightEntry.COLUMN_Aircraft_Type + " INTEGER NOT NULL,"
                        + FlightEntry.COLUMN_Aircraft_Number + " INTEGER NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_FLIGHT_TABLE);

    }

    public static final String COLUMN_ICAO_Destination = "ICAO_Destination";
    public static final String COLUMN_ICAO_Departure = "ICAO_Departure";

    // Deleting the table
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FlightEntry.TABLE_NAME;

    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion, int newVersion) {

        database.execSQL(SQL_DELETE_ENTRIES);
        onCreate(database);
    }
}
