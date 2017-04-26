package com.softwareengg.project.notifyme.PromoListFragment.PromoListOperations;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;

import com.softwareengg.project.notifyme.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by shivanshu on 26/04/17.
 */

public class FilterDialogFragment extends DialogFragment {
    private static final String TAG = "NotifyMe";

    private static final String ARG_FILTER = "com.softwareengg.project.notifyme.PromoListFragment.PromoListOperations.VendorFilterDialogFragment.filter";
    public Filter mFilter;
    public Calendar mReceipt = Calendar.getInstance();
    public Calendar mExpiry = Calendar.getInstance();

    private EditText mSelectedVendorsText;
    private EditText mReceiptText;
    private EditText mExpiryText;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        View rootView =  inflater.inflate(R.layout.filter_dialog, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Filter");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        }
        setHasOptionsMenu(true);

        if(getArguments() != null) {
            mFilter = (Filter) getArguments().getSerializable(ARG_FILTER);
            ArrayList<String> selectedVendors = mFilter.getVendors();
            if(selectedVendors == null) {
                final String[] vendors = getResources().getStringArray(R.array.vendors);
                selectedVendors = new ArrayList<>(vendors.length);
                for(int i = 0; i < vendors.length; i++) {
                    selectedVendors.add(vendors[i]);
                }
                mFilter.setVendors(selectedVendors);
            }
            SimpleDateFormat sdf = new SimpleDateFormat(getResources().getString(R.string.date_format));
            try {
                if(mFilter.getReceipt() != null) mReceipt.setTime(sdf.parse(mFilter.getReceipt()));
                if(mFilter.getExpiry() != null) mExpiry.setTime(sdf.parse(mFilter.getExpiry()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            dismiss();
        }

        mSelectedVendorsText = (EditText) rootView.findViewById(R.id.selected_vendors);
        mSelectedVendorsText.setText(TextUtils.join(", ", mFilter.getVendors()));
        mSelectedVendorsText.setClickable(true);
        mSelectedVendorsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showVendorSelectDialog();
            }
        });

        mReceiptText = (EditText) rootView.findViewById(R.id.receipt_date);
        if(mFilter.getReceipt() != null) mReceiptText.setText(mFilter.getReceipt());
        mReceiptText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                Log.v(TAG, "Year: " + selectedYear);
                                Log.v(TAG, "Month: " + selectedMonth);
                                Log.v(TAG, "Day: " + selectedDay);

                                mReceipt.set(selectedYear, selectedMonth, selectedDay);
                                SimpleDateFormat sdf = new SimpleDateFormat(getResources().getString(R.string.date_format));
                                mFilter.setReceipt(sdf.format(mReceipt.getTime()));
                                mReceiptText.setText(sdf.format(mReceipt.getTime()));
                            }
                        },
                        mReceipt.get(Calendar.YEAR),
                        mReceipt.get(Calendar.MONTH),
                        mReceipt.get(Calendar.DAY_OF_MONTH));
                datePicker.setCancelable(false);
                datePicker.setTitle("Select the date");
                datePicker.show();
            }
        });

        mExpiryText = (EditText) rootView.findViewById(R.id.expiry_date);
        if(mFilter.getExpiry() != null) mExpiryText.setText(mFilter.getExpiry());
        mExpiryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                Log.v(TAG, "Year: " + selectedYear);
                                Log.v(TAG, "Month: " + selectedMonth);
                                Log.v(TAG, "Day: " + selectedDay);
                                mExpiry.set(selectedYear, selectedMonth + 1, selectedDay);
                                SimpleDateFormat sdf = new SimpleDateFormat(getResources().getString(R.string.date_format));
                                mFilter.setExpiry(sdf.format(mExpiry.getTime()));
                                mExpiryText.setText(sdf.format(mExpiry.getTime()));
                            }
                        },
                        mExpiry.get(Calendar.YEAR),
                        mExpiry.get(Calendar.MONTH),
                        mExpiry.get(Calendar.DAY_OF_MONTH));
                datePicker.setCancelable(false);
                datePicker.show();
            }
        });
        return rootView;
    }

    private void showVendorSelectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] vendors = getResources().getStringArray(R.array.vendors);
        final boolean[] checked = new boolean[vendors.length];
        if(mFilter.getVendors().size() == vendors.length) {
            for(int i = 0; i < vendors.length; i++) {
                checked[i] = true;
            }
        } else if(mFilter.getVendors().size() > 0) {
            for(int i = 0; i < vendors.length; i++) {
                if(mFilter.getVendors().contains(vendors[i])) {
                    checked[i] = true;
                }
            }
        }
        builder.setTitle("Select Vendors")
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
                                    mFilter.getVendors().add(vendors[which]);
                                } else if (mFilter.getVendors().contains(vendors[which])) {
                                    // Else, if the item is already in the array, remove it
                                    mFilter.getVendors().remove(vendors[which]);
                                }
                            }
                        })
                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO: Apply filters
                        if(mFilter != null) {
                            mSelectedVendorsText.setText(TextUtils.join(", ", mFilter.getVendors()));
                        }

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        builder.create();
        builder.show();
    }

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_filter_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            // handle confirmation button click here
            Log.v(TAG, "dialog : " + TextUtils.join(", ", mFilter.getVendors()));
            mListener.onApplyFilter(FilterDialogFragment.this, mFilter);
            dismiss();
            return true;
        } else if (id == android.R.id.home) {
            // handle close button click here
            dismiss();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FilterDialogListener) {
            mListener = (FilterDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FilterDialogListener");
        }
    }

    public interface FilterDialogListener {
        void onApplyFilter(DialogFragment dialog, Filter filter);
    }
}
