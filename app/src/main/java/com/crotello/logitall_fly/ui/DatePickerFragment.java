package com.crotello.logitall_fly.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.crotello.logitall_fly.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Tim on 18/09/2016.
 */

// Users of this Fragment must setArguments() within  a bundle for "DateChanged"
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private TextView modifyArrivalDateText,modifyDepartureDateText;;
    private String viewToUse;

    public Dialog onCreateDialog(TextView departDateTextView,TextView arrivalDateTextView, Boolean settingTheDepartureDate,  Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        //We are just about to show the dialog.
        //Get which time is being changed.
        // TODO: Can I pass an object...? Will make this method much tidier...
        viewToUse = this.getArguments().getString("DateChanged");

        modifyDepartureDateText = (TextView) getActivity().findViewById(R.id.setFlightDepartureDate);
        modifyArrivalDateText = (TextView) getActivity().findViewById(R.id.setFlightArrivalDate);
        Context theContext;

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, dayOfMonth);
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {



































        TextView tvA, tvD = null;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Context context = getActivity();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        switch (viewToUse) {
            case "modifyDepartDate":
                //Do something with the user chosen time
                //Get reference of host activity (XML Layout File) TextView widget
                tvD = (TextView) getActivity().findViewById(R.id.setFlightDepartureDate);
                tvA = (TextView) getActivity().findViewById(R.id.setFlightArrivalDate);
                //Add this time to the tv.
                //Set Arrival to same date
               //tvD.setText(String.format(Locale.ENGLISH, "%02d/%02d/%4d", dayOfMonth, (month + 1), year));
                tvA.setText(String.format(Locale.getDefault(), "%02d/%02d/%4d", dayOfMonth, (month + 1), year));

                cal.set(Calendar.YEAR,year);
                cal.set(Calendar.MONTH,month);
                cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                Date theSetDate = cal.getTime();

                tvD.setText(format.format(theSetDate));
                break;

            case "modifyArriveDate":
                //Get reference of host activity (XML Layout File) TextView widget
                tvA = (TextView) getActivity().findViewById(R.id.setFlightArrivalDate);
                tvD = (TextView) getActivity().findViewById(R.id.setFlightDepartureDate);

                //Put the user value in the TextView so we can error check it.
                //tvA.setText(String.format(Locale.ENGLISH, "%02d/%02d/%4d", dayOfMonth, (month + 1), year));

                //Format the user inout so we can check it is valid.
                String userSetArrival = (String.format(Locale.getDefault(), "%02d/%02d/%4d", dayOfMonth, (month + 1), year));
                String currentlySetDeparture = tvD.getText().toString();

                Date arrivalModifyDateSet = new Date();
                Date departModifyDateSet = new Date();

                try {
                    arrivalModifyDateSet = format.parse(userSetArrival);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    departModifyDateSet = format.parse(currentlySetDeparture);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (departModifyDateSet.after(arrivalModifyDateSet)) {
                    //Make the user set the Arrival date again
                    tvA.setText(tvD.getText());
                    Toast toast;
                    toast = Toast.makeText(context, "Cannot arrive before departure.\nArrival Date set to departure date.", Toast.LENGTH_LONG);
                    toast.show();

                    break;
                }

                //Add this time to the tv.
                tvA.setText(String.format(Locale.getDefault(), "%02d/%02d/%4d", dayOfMonth, (month + 1), year));
                break;

            case "addDepartDate":
                //Do something with the user chosen time
                //Get reference of host activity (XML Layout File) TextView widget
                //TODO: SOrt for add
                tvD = (TextView) getActivity().findViewById(R.id.setFlightDepartureDate);
                tvA = (TextView) getActivity().findViewById(R.id.setFlightArrivalDate);
                //Add this time to the tv.
                //Set Arrival to same date
                tvD.setText(String.format(Locale.getDefault(), "%02d/%02d/%4d", dayOfMonth, (month + 1), year));
                tvA.setText(String.format(Locale.getDefault(), "%02d/%02d/%4d", dayOfMonth, (month + 1), year));
                break;

            case "addArriveDate":
                //TODO: Sort for add
                //Get reference of host activity (XML Layout File) TextView widget
                tvA = (TextView) getActivity().findViewById(R.id.setFlightArrivalDate);
                tvD = (TextView) getActivity().findViewById(R.id.setFlightDepartureDate);

                //Put the user value in the TextView so we can error check it.
                //tvA.setText(String.format(Locale.ENGLISH, "%02d/%02d/%4d", dayOfMonth, (month + 1), year));

                //Format the user inout so we can check it is valid.
                String userSetAddArrival = (String.format(Locale.getDefault(), "%02d/%02d/%4d", dayOfMonth, (month + 1), year));
                String currentlySetAddDeparture = tvD.getText().toString();


                Date arrivalAddDateSet = new Date();
                Date departAddDateSet = new Date();

                try {
                    arrivalAddDateSet = format.parse(userSetAddArrival);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    departAddDateSet = format.parse(currentlySetAddDeparture);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (departAddDateSet.after(arrivalAddDateSet)) {
                    //Make the user set the Arrival date again
                    tvA.setText(tvD.getText());
                    Toast toast;
                    toast = Toast.makeText(context, "Cannot arrive before departure.\n Arrival Date set to departure date.", Toast.LENGTH_LONG);
                    toast.show();

                    break;
                }

                //Add this time to the tv.
                tvA.setText(String.format(Locale.getDefault(), "%02d/%02d/%4d", dayOfMonth, (month + 1), year));
                break;
        }


    }
}
