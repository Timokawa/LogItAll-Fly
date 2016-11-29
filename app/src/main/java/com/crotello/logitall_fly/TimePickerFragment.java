package com.crotello.logitall_fly;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Tim on 18/09/2016.
 */

//Users of this Fragment must setArguments() within  a bundle for "TimeChanged"
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private String viewToUse;

    //@Override
    public Dialog onCreateDialog(TextView theDepartureTimeTextViewToUpdate, TextView theArrivalTimeTextViewToUpdate,Bundle savedInstanceState ) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //We are just about to show the dialog.
        //Get which time is being changed.
        viewToUse = this.getArguments().getString("TimeChanged");

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void  onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView tvA,tvD = null;
        //View addFlightview =

        switch (viewToUse){
            case "modifyAtd":
                //Do something with the user chosen time
                //Get reference of host activity (XML Layout File) TextView widget
                tvD = (TextView) getActivity().findViewById(R.id.setFlightAtd);
                tvA = (TextView) getActivity().findViewById(R.id.setFlightAta);
                //Add this time to the tv.
                tvD.setText(String.format(Locale.getDefault(),"%02d:%02d", hourOfDay, minute ));
                // Set the Arrival time to be the departure time.
                tvA.setText(String.format(Locale.getDefault(),"%02d:%02d", hourOfDay, minute ) );

                break;

            case "modifyAta" :
                //Do something with the user chosen time
                //Get reference of host activity (XML Layout File) TextView widget
                tvA = (TextView) getActivity().findViewById(R.id.setFlightAta);
                // TODO: Set the FlightDetails to this.
                //Add this time to the tv.
                tvA.setText(String.format(Locale.getDefault(),"%02d:%02d", hourOfDay, minute) );
                break;

            case "addAta" :
                //Do something with the user chosen time
                //Get reference of host activity (XML Layout File) TextView widget
                // TODO: 27/09/2016 Change to match add field
                tvA = (TextView) getActivity().findViewById(R.id.setFlightAta);

                //Add this time to the tv.
                tvA.setText(String.format("%02d:%02d", hourOfDay, minute, Locale.getDefault()));
                break;

            case "addAtd" :
                //Do something with the user chosen time
                //Get reference of host activity (XML Layout File) TextView widget
                // TODO: 27/09/2016 Change to match add field
                tvD = (TextView) getActivity().findViewById(R.id.setFlightAtd);
                tvA = (TextView) getActivity().findViewById(R.id.setFlightAta);

                //Add this time to the tv.
                tvD.setText(String.format("%02d:%02d", hourOfDay, minute, Locale.getDefault()));
                // Set the Arrival time to be the departure time.
                tvA.setText(String.format(Locale.getDefault(),"%02d:%02d", hourOfDay, minute ) );
                break;
        }



    }
}
