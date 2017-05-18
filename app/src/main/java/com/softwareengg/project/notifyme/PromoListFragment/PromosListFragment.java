package com.softwareengg.project.notifyme.PromoListFragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softwareengg.project.notifyme.PromoListFragment.PromoListOperations.Filter;
import com.softwareengg.project.notifyme.NotifyMeDatabase.NotifyMeContract.PromoEntry;
import com.softwareengg.project.notifyme.Promo;
import com.softwareengg.project.notifyme.NotifyMeDatabase.PromoDatabaseHelper;
import com.softwareengg.project.notifyme.PromoListFragment.PromoListOperations.Sort;
import com.softwareengg.project.notifyme.R;
import com.softwareengg.project.notifyme.textprocess.TextProcessing;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by shivanshu on 31/03/17.
 * Purpose:
 * - Show filtered and sorted list of promos of a particular category.
 * - User can tap on a promo to view full information.
 */

public class PromosListFragment extends Fragment {

    private static final String TAG = "NotifyMe";
    private static final String ARG_FILTER = "com.softwareengg.project.notifyme.PromoListFragment.PromosListFragment.filter";
    private static final String ARG_SORT = "com.softwareengg.project.notifyme.PromoListFragment.PromosListFragment.sort";
    public Filter mFilter;
    public Sort mSort;

    private RecyclerView mRecyclerView;
    private MyPromosRecyclerViewAdapter mAdapter;
    private OnListFragmentInteractionListener mListener;

    PromoDatabaseHelper mDbHelper;

    private ArrayList<Promo> mPromos;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PromosListFragment() {
    }

    // The right way to create new instances of a fragment.
    public static PromosListFragment newInstance(Filter filter, Sort sort) {
        PromosListFragment fragment = new PromosListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FILTER, filter);
        args.putSerializable(ARG_SORT, sort);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mFilter = (Filter) getArguments().getSerializable(ARG_FILTER);
            mSort = (Sort) getArguments().getSerializable(ARG_SORT);
        }

        mDbHelper = PromoDatabaseHelper.getInstance(getContext());
        mPromos = new ArrayList<>();
        readPromos();
    }

    // User selected new filters - repopulate list
    public void updateFilter(Filter filter) {
        mFilter = filter;
        readPromos();
        mAdapter.updatePromos(mPromos);
    }

    // User selected new sorting criteria - repopulate list
    public void updateSort(Sort sort) {
        mSort = sort;
        readPromos();
        mAdapter.updatePromos(mPromos);
    }

    // add a dummy promos to database - not for production.
    private void addDummies(String dummyPromo){
        Promo promo = TextProcessing.parsePromo(dummyPromo, getResources().getStringArray(R.array.vendors));
        SimpleDateFormat sdf = new SimpleDateFormat(getResources().getString(R.string.date_format));
        ContentValues cv = new ContentValues();
        cv.put(PromoEntry.COLUMN_NAME_CATEGORY, promo.getCategory());
        cv.put(PromoEntry.COLUMN_NAME_VENDOR,promo.getVendor());
        cv.put(PromoEntry.COLUMN_NAME_CODE, promo.getCode());
        cv.put(PromoEntry.COLUMN_NAME_DISCOUNT_AMOUNT,promo.getDiscountAmount());
        cv.put(PromoEntry.COLUMN_NAME_DISCOUNT_PERCENT,promo.getDiscountPercentage());
        cv.put(PromoEntry.COLUMN_NAME_MAX_USES, promo.getMaxUses());
        cv.put(PromoEntry.COLUMN_NAME_SCORE, promo.getScore());
        cv.put(PromoEntry.COLUMN_NAME_RECEIPT, sdf.format(new java.util.Date()));
        if(promo.getExpiry() != null) cv.put(PromoEntry.COLUMN_NAME_EXPIRY, sdf.format(promo.getExpiry()));
        else cv.put(PromoEntry.COLUMN_NAME_EXPIRY, "2100-12-31");
        cv.put(PromoEntry.COLUMN_NAME_PROMO_MSG, promo.getPromoMsg());
        Log.v(TAG, "add dummies: " + cv.toString());

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.insert(PromoEntry.TABLE_NAME,null,cv);
    }

    private void readPromos() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //Check if table is empty, insert dummies if empty - not for production
        Cursor mCursor = db.rawQuery("SELECT * FROM " + PromoEntry.TABLE_NAME, null);
        if(mCursor.getCount() == 0) {
            addDummies("Dear Rider, POOL is at flat Rs49 in NCR only till tomorrow 9 Feb! Valid on trips within Gurgaon, Delhi, Noida-Ghaziabad & Faridabad, upto 8km. t.uber.com/49ncr");
            addDummies("Delicious foodpanda offer - 40% off on your first order. Use code NEWPANDA. Pay via wallets & 15% cash back too. Order Now: https://chk.bz/9k8pe69wzb");
            addDummies("Dear Rider, use code DELWEEK & get Rs. 50 off 2 rides on uberGO or uberX. Valid only for you till midnight of Wed. 25 Jan, in Delhi NCR. Uber on!");
            addDummies("Don't miss this! Use code JAN50 before 24 Jan & get Rs.50 cashback on Rs.50 transaction for deepanker27mishra@gmail.com. T&C Apply: http://frch.in/kk");
            addDummies("Dominos Super Value Offer \\\"Only For You\\\";Buy 1 Medium/Large Pizza &Get 30% OFF.WalkIn/Order@ 68886888/ goo.gl/CQThqp Cpn: CRMF182F4EC80 Valid till 08 Jan T&C");
        }

        // which columns to fetch
        String[] projection = null;     // read all columns

        // Filter results: WHERE "category" = mCategory AND "vendor" IN (mVendors) AND receipt > 'mReceipt' AND expiry > 'mExpiry';
        ArrayList<String> selections = new ArrayList<>();
        ArrayList<String> selectionArgsList = new ArrayList<>();

        // category filter
        String mCategory = mFilter.getCategory();
        if(mCategory != null) {
            selections.add(PromoEntry.COLUMN_NAME_CATEGORY + "=?");
            selectionArgsList.add(mCategory);
        }

        // vendor filter
        ArrayList<String> mVendors = mFilter.getVendors();
        if(mVendors != null && mVendors.size() > 0) {
            selections.add(PromoEntry.COLUMN_NAME_VENDOR + " IN (" + placeholders(mVendors.size()) + ") ");
            selectionArgsList.addAll(mVendors);
        }

        // date of receipt filter
        String mReceipt = mFilter.getReceipt();
        if(mReceipt != null) {
            selections.add(PromoEntry.COLUMN_NAME_RECEIPT  + ">?");
            selectionArgsList.add(mReceipt);
        }

        // expiry date filter
        String mExpiry = mFilter.getExpiry();
        if(mExpiry != null) {
            selections.add(PromoEntry.COLUMN_NAME_EXPIRY + ">?");
            selectionArgsList.add(mExpiry);
        }

        String selection = TextUtils.join("AND ", selections);
        String[] selectionArgs = selectionArgsList.toArray(new String[selectionArgsList.size()]);

        // Sorting criteria
        final String[] sort_criteria = getResources().getStringArray(R.array.sort_criteria);
        String sortOrder = sort_criteria[mSort.getCriteria()];
        if(mSort.getOrder() == 0) {
            sortOrder += " DESC";
        } else {
            sortOrder += " ASC";
        }

        // query the database and populate promo list with results
        Cursor cursor = db.query(
                PromoEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        mPromos.clear();
        while(cursor.moveToNext()) {
            Promo promo = new Promo();
            promo.setCategory(cursor.getString(cursor.getColumnIndex(PromoEntry.COLUMN_NAME_CATEGORY)));
            promo.setVendor(cursor.getString(cursor.getColumnIndex(PromoEntry.COLUMN_NAME_VENDOR)));
            promo.setCode(cursor.getString(cursor.getColumnIndex(PromoEntry.COLUMN_NAME_CODE)));
            promo.setDiscountAmount(cursor.getDouble(cursor.getColumnIndex(PromoEntry.COLUMN_NAME_DISCOUNT_AMOUNT)));
            promo.setDiscountPercentage(cursor.getDouble(cursor.getColumnIndex(PromoEntry.COLUMN_NAME_DISCOUNT_PERCENT)));
            promo.setMaxUses(cursor.getInt(cursor.getColumnIndex(PromoEntry.COLUMN_NAME_MAX_USES)));
            promo.setScore(cursor.getDouble(cursor.getColumnIndex(PromoEntry.COLUMN_NAME_SCORE)));
            String receipt = cursor.getString(cursor.getColumnIndex(PromoEntry.COLUMN_NAME_RECEIPT));
            String expiry = cursor.getString(cursor.getColumnIndex(PromoEntry.COLUMN_NAME_EXPIRY));
            SimpleDateFormat sdf = new SimpleDateFormat(getResources().getString(R.string.date_format));
            try {
                if(receipt != null) promo.setReceipt(new Date(sdf.parse(receipt).getTime()));
                if(expiry != null) promo.setExpiry(new Date(sdf.parse(expiry).getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            promo.setPromoMsg(cursor.getString(cursor.getColumnIndex(PromoEntry.COLUMN_NAME_PROMO_MSG)));
            mPromos.add(promo);
        }

        cursor.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promos_list, container, false);

        // Set the adapter that handles the data to be shown.
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
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
        void onPromoSelected(Promo promo);
    }

    // Utility function for generating a string of placeholders(?) for android sqlite.
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
