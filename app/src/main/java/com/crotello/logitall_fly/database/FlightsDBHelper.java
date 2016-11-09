package com.crotello.logitall_fly.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.crotello.logitall_fly.database.FlightsContract.FlightEntry;


/**
 * Created by Tim on 10/09/2016.
 */


public class FlightsDBHelper extends SQLiteOpenHelper {

    // database version
    //Version 5 changes format of Date and times to integer.
    //Version 6 splits date into Departure/Arrival
    //Version 7 was used to trigger a check of date and time creation
    // Version 8/9 was to change Date types to real
    // Version 10 went back to Integer
    // Version 11 was to reset
    // Version 12 was total schema update
    static final int DB_VERSION = 2;

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
                        + FlightEntry
                        ._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + FlightEntry.COLUMN_FLIGHT_NUMBER + " TEXT, "
                        + FlightEntry.COLUMN_DEPARTURE_DATE_AND_TIME + " INTEGER NOT NULL, "
                        + FlightEntry.COLUMN_ARRIVAL_DATE_AND_TIME + " INTEGER NOT NULL, "
                        + FlightEntry.COLUMN_NIGHT_HOURS + " INTEGER NOT NULL, "
                        + FlightEntry.COLUMN_DAY_HOURS + " INTEGER NOT NULL, "
                        + FlightEntry.COLUMN_TOTAL_HOURS + " INTEGER NOT NULL,"
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
