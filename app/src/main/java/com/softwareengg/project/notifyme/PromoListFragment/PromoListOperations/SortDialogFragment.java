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

public class SortDialogFragment extends DialogFragment {
    private static final String TAG = "NotifyMe";

    private static final String ARG_SORT = "com.softwareengg.project.notifyme.PromoListFragment.PromoListOperations.VendorSortDialogFragment.sort";
    public Sort mSort;

    private EditText mCriteriaText;
    private EditText mDirectionText;

    // Use this instance of the interface to deliver action events
    SortDialogFragment.SortDialogListener mListener;

    public static SortDialogFragment newInstance(Sort sort) {
        SortDialogFragment fragment = new SortDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SORT, sort);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        View rootView =  inflater.inflate(R.layout.sort_dialog, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Sort");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        }
        setHasOptionsMenu(true);

        if(getArguments() != null) {
            mSort = (Sort) getArguments().getSerializable(ARG_SORT);

        } else {
            dismiss();
        }

        final String[] sort_criteria = getResources().getStringArray(R.array.sort_criteria);
        mCriteriaText = (EditText) rootView.findViewById(R.id.criteria);
        mCriteriaText.setText(sort_criteria[mSort.getCriteria()]);
        mCriteriaText.setClickable(true);
        mCriteriaText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Vendors")
                        //.setMessage(R.string.dialog_fire_missiles)
                        // Specify the list array, the items to be selected by default (null for none),
                        // and the listener through which to receive callbacks when items are selected
                        .setSingleChoiceItems(sort_criteria, mSort.getCriteria(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int criteria) {
                                mSort.setCriteria(criteria);
                                mCriteriaText.setText(sort_criteria[criteria]);
                            }
                        })
                        .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int criteria) {

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
        });

        final String[] sort_direction = {"Descending", "Ascending"};
        mDirectionText = (EditText) rootView.findViewById(R.id.direction);
        mDirectionText.setText(sort_direction[mSort.getOrder()]);
        mDirectionText.setClickable(true);
        mDirectionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final String[] sort_criteria = getResources().getStringArray(R.array.sort_criteria);
                builder.setTitle("Select Vendors")
                        //.setMessage(R.string.dialog_fire_missiles)
                        // Specify the list array, the items to be selected by default (null for none),
                        // and the listener through which to receive callbacks when items are selected
                        .setSingleChoiceItems(sort_direction, mSort.getOrder(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int order) {
                                mSort.setOrder(order);
                                mDirectionText.setText(sort_direction[order]);
                            }
                        })
                        .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int order) {

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
        });
        return rootView;
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
        getActivity().getMenuInflater().inflate(R.menu.menu_sort_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            // handle confirmation button click here
            Log.v(TAG, "dialog : " + mSort.getCriteria());
            mListener.onApplySort(SortDialogFragment.this, mSort);
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
        if (context instanceof SortDialogFragment.SortDialogListener) {
            mListener = (SortDialogFragment.SortDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SortDialogListener");
        }
    }

    public interface SortDialogListener {
        void onApplySort(DialogFragment dialog, Sort sort);
    }
}
