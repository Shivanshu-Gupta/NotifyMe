package com.softwareengg.project.notifyme;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;

public class FilterDialogFragment extends DialogFragment {

    private static final String ARG_FILTER = "com.softwareengg.project.notifyme.FilterDialogFragment.filter";
    public Filter mFilter;

    ArrayList<String> mSelectedVendors;

    // Use this instance of the interface to deliver action events
    FilterDialogListener mListener;

    public static FilterDialogFragment newInstance(Filter filter) {
        FilterDialogFragment fragment = new FilterDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FILTER, filter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(getArguments() != null) {
            mFilter = (Filter) getArguments().getSerializable(ARG_FILTER);
            mSelectedVendors = mFilter.getVendors();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] vendors = getResources().getStringArray(R.array.vendors);
        final boolean[] checked = new boolean[vendors.length];
        if(mSelectedVendors == null) {
            mSelectedVendors = new ArrayList<String>();
            for(int i = 0; i < vendors.length; i++) {
                mSelectedVendors.add(vendors[i]);
                checked[i] = true;
            }
        } else if(mSelectedVendors.size() > 0) {
            for(int i = 0; i < vendors.length; i++) {
                if(mSelectedVendors.contains(vendors[i])) {
                    checked[i] = true;
                }
            }
        }

        builder.setTitle("Filter Promos")
                //.setMessage(R.string.dialog_fire_missiles)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(vendors, checked,
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
                        Filter filter = new Filter();
                        filter.setVendors(mSelectedVendors);
                        if(mFilter != null) {
                            filter.setCategory(mFilter.getCategory());
                        }
                        mListener.onApplyFilter(FilterDialogFragment.this, filter);
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
        public void onApplyFilter(DialogFragment dialog, Filter filter);
    }
}
