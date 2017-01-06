package com.crotello.logitall_fly.datamanipulation;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Tim on 14/09/2016.
 */

public class FlightDetails {

    //Variables that every flight has.
    //TODO Can this be Int?
    private Long _id;
    private String flightNumber;
    private Long departureDate;
    private Long arrivalDate;
    private Long ata;
    private Long atd;
    private Long flightTimeTotal;
    private Long flightTimeDay;
    private Long flightTimeNight;
    private String role;
    private String pic;
    // Lookup to another database
    private int ICAO_Departure;
    private int ICAO_Destination;
    private int Aircraft_Number;
    private int AircraftType;
    private boolean ValidToSave;

    // Constructor
    public FlightDetails() {
        Calendar theCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.getDefault());
        Date rightNow = new Date();
        _id = null;
        flightNumber = "";
        departureDate = theCalendar.getTimeInMillis();
        arrivalDate = theCalendar.getTimeInMillis();
        ata = theCalendar.getTimeInMillis();
        atd = theCalendar.getTimeInMillis();
        flightTimeTotal = Long.MIN_VALUE;
        flightTimeDay = Long.MIN_VALUE;
        flightTimeNight = Long.MIN_VALUE;
        ICAO_Departure = 0;
        ICAO_Destination = 0;
        role = "";
        pic = "";
        AircraftType = 0;
        Aircraft_Number = 0;
        ValidToSave = false;
    }

    public int getAircraft_Number() {
        return Aircraft_Number;
    }

    public void setAircraft_Number(int aircraft_Number) {
        Aircraft_Number = aircraft_Number;
    }

    public int getAircraftType() {
        return AircraftType;
    }

    public void setAircraftType(int aircraftType) {
        AircraftType = aircraftType;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getICAO_Departure() {
        return ICAO_Departure;
    }

    public void setICAO_Departure(int ICAO_Departure) {
        this.ICAO_Departure = ICAO_Departure;
    }

    public int getICAO_Destination() {
        return ICAO_Destination;
    }

    public void setICAO_Destination(int ICAO_Destination) {
        this.ICAO_Destination = ICAO_Destination;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public long getFlightTimeTotal() {
        return flightTimeTotal;
    }

    public void setFlightTimeTotal(Long flightTimeTotal) {
        this.flightTimeTotal = flightTimeTotal;
    }

    public long getFlightTimeDay() {
        return flightTimeDay;
    }

    public void setFlightTimeDay(Long flightTimeDay) {
        this.flightTimeDay = flightTimeDay;
    }

    public long getFlightTimeNight() {
        return flightTimeNight;
    }

    public void setFlightTimeNight(Long flightTimeNight) {
        this.flightTimeNight = flightTimeNight;
    }

    private void updateTotal_Time() {
        if ((this.arrivalDate + this.ata) - (this.departureDate + this.atd) > 0) {

            this.flightTimeTotal = ((this.arrivalDate + this.ata) - (this.departureDate + this.atd));
            Log.i("FLY ATA", Long.toString(this.ata));
            Log.i("FLY ATD", Long.toString(this.atd));
        }
    }

    public Long get_id() {

        return _id;
    }

    public void set_id(Long _id) {

        this._id = _id;
    }

    public String getFlight_Number() {

        return flightNumber;
    }

    public void setFlight_Number(String flightNumber) {

        this.flightNumber = flightNumber;
    }

    public long getDepartureDate() {

        return departureDate;
    }

    public void setDepartureDate(Long departureDate) {
        this.departureDate = departureDate;
        updateTotal_Time();
    }

    public long getArrivalDate() {

        return arrivalDate;
    }

    public void setArrivalDate(Long arrivalDate) {
        this.arrivalDate = arrivalDate;
        updateTotal_Time();
    }

    public long getAtd() {

        return atd;
    }

    public void setAtd(Long atd) {
        this.atd = atd;
        updateTotal_Time();
    }

    public long getAta() {

        return ata;
    }

    public void setAta(Long ata) {
        this.ata = ata;
        updateTotal_Time();
    }

    public String getFormattedDepartureDate() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        return dateFormat.format(getDepartureDate());
    }

    public String getFormattedArrivalDate() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        return dateFormat.format(getArrivalDate());

    }

    public String getFormattedArrivalTime() {
        final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        return timeFormat.format(getAta());

    }

    public String getFormattedDepartureTime() {
        final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        return timeFormat.format(getAtd());
    }

    public String getFormattedFlightTimeTotal() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        return timeFormat.format(getFlightTimeTotal());
    }

    public boolean validToSave() {
        return (
                (getDepartureDate() > 0) &
                        (getFlight_Number().equals("")));
    }

}
