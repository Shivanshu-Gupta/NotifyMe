package com.softwareengg.project.notifyme.PromoListFragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softwareengg.project.notifyme.Filter;
import com.softwareengg.project.notifyme.NotifyMeContract.PromoEntry;
import com.softwareengg.project.notifyme.Promo;
import com.softwareengg.project.notifyme.PromoDatabaseHelper;
import com.softwareengg.project.notifyme.R;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PromosFragment extends Fragment {

    private static final String ARG_CATEGORY = "category";
    private String mCategory;
    private static final String ARG_VENDORS = "vendors";
    private ArrayList<String> mVendors;
    private static final String ARG_RECEIPT = "receipt";
    private Date mReceipt;
    private static final String ARG_EXPIRY = "expiry";
    private Date mExpiry;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private OnListFragmentInteractionListener mListener;

    PromoDatabaseHelper mDbHelper;

    ArrayList<Promo> mPromos;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PromosFragment() {
    }

    public static PromosFragment newInstance(Filter filter) {
        PromosFragment fragment = new PromosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, filter.getCategory());
        args.putStringArrayList(ARG_VENDORS, filter.getVendors());
        DateFormat df = DateFormat.getDateInstance();
        args.putString(ARG_RECEIPT, df.format(filter.getReceipt()));
        args.putString(ARG_EXPIRY, df.format(filter.getExpiry()));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCategory = getArguments().getString(ARG_CATEGORY);
            mVendors = getArguments().getStringArrayList(ARG_VENDORS);
            DateFormat df = DateFormat.getDateInstance();
            try {
                mReceipt = new Date(df.parse(getArguments().getString(ARG_RECEIPT)).getTime());
                mExpiry = new Date(df.parse(getArguments().getString(ARG_EXPIRY)).getTime());
            } catch (ParseException e) {}
        }

        mDbHelper = PromoDatabaseHelper.getInstance(getContext());
        readPromos();
    }

    private void readPromos() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // which columns to fetch
        String[] projection = null;     // read all columns

        // Filter results WHERE "vendor" IN (mVendors) AND receipt > 'mReceipt' AND expiry > 'mExpiry';
        ArrayList<String> selections = new ArrayList<>();
        ArrayList<String> selectionArgsList = new ArrayList<>();
        if(mVendors != null && mVendors.size() > 0) {
            selections.add(PromoEntry.COLUMN_NAME_VENDOR + " IN (" + placeholders(mVendors.size()) + ") ");
            selectionArgsList.addAll(mVendors);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        if(mReceipt != null) {
            selections.add(PromoEntry.COLUMN_NAME_RECEIPT  + "> ? ");
            selectionArgsList.add(sdf.format(mReceipt));
        }
        if(mExpiry != null) {
            selections.add(PromoEntry.COLUMN_NAME_EXPIRY + "> ? ");
            selectionArgsList.add(sdf.format(mExpiry));
        }

        String selection = TextUtils.join("AND ", selections);
        String[] selectionArgs = selectionArgsList.toArray(new String[selectionArgsList.size()]);

        String sortOrder =
                PromoEntry.COLUMN_NAME_SCORE + " DESC";

        Cursor cursor = db.query(
                PromoEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        while(cursor.moveToNext()) {
            Promo promo = new Promo();
            //TODO: handle null values
            promo.setCategory(cursor.getString(cursor.getColumnIndex(PromoEntry.COLUMN_NAME_CATEGORY)));
            promo.setVendor(cursor.getString(cursor.getColumnIndex(PromoEntry.COLUMN_NAME_VENDOR)));
            promo.setCode(cursor.getString(cursor.getColumnIndex(PromoEntry.COLUMN_NAME_CODE)));
            promo.setDiscountAmount(cursor.getDouble(cursor.getColumnIndex(PromoEntry.COLUMN_NAME_DISCOUNT_AMOUNT)));
            promo.setDiscountPercentage(cursor.getDouble(cursor.getColumnIndex(PromoEntry.COLUMN_NAME_DISCOUNT_PERCENT)));
            promo.setMaxUses(cursor.getInt(cursor.getColumnIndex(PromoEntry.COLUMN_NAME_MAX_USES)));
            promo.setScore(cursor.getDouble(cursor.getColumnIndex(PromoEntry.COLUMN_NAME_SCORE)));
            String receipt = cursor.getString(cursor.getColumnIndex(PromoEntry.COLUMN_NAME_RECEIPT));
            String expiry = cursor.getString(cursor.getColumnIndex(PromoEntry.COLUMN_NAME_EXPIRY));
            try {
                promo.setReceipt((Date) sdf.parse(receipt));
                promo.setExpiry((Date) sdf.parse(expiry));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mPromos.add(promo);
        }

        cursor.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promos_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            // recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            mAdapter = new MyPromosRecyclerViewAdapter(mPromos, mListener);
            mRecyclerView.setAdapter(mAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDbHelper.close();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPromoSelected(Promo promo);
    }

    String placeholders(int len) {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }
}
