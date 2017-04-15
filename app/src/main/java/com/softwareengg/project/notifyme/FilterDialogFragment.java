package com.softwareengg.project.notifyme;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;

public class FilterDialogFragment extends DialogFragment {

    ArrayList<String> mSelectedVendors = new ArrayList();

    // Use this instance of the interface to deliver action events
    FilterDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] vendors = getResources().getStringArray(R.array.vendors);

        builder.setTitle("Filter Promos")
                //.setMessage(R.string.dialog_fire_missiles)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(vendors, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    mSelectedVendors.add(vendors[which]);
                                } else if (mSelectedVendors.contains(vendors[which])) {
                                    // Else, if the item is already in the array, remove it
                                    mSelectedVendors.remove(vendors[which]);
                                }
                            }
                        })
                .setPositiveButton(R.string.filter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO: Apply filters
                        mListener.onApplyFilter(FilterDialogFragment.this, mSelectedVendors);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FilterDialogListener) {
            mListener = (FilterDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    /* The activity that creates an instance of this dialog fragment must
    * implement this interface in order to receive event callbacks.
            * Each method passes the DialogFragment in case the host needs to query it. */
    public interface FilterDialogListener {
        public void onApplyFilter(DialogFragment dialog, ArrayList<String> selectedVendors);
    }
}
