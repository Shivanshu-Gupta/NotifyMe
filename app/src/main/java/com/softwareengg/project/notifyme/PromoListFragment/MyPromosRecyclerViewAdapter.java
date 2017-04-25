package com.softwareengg.project.notifyme.PromoListFragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softwareengg.project.notifyme.Promo;
import com.softwareengg.project.notifyme.PromoListFragment.PromosFragment.OnListFragmentInteractionListener;
import com.softwareengg.project.notifyme.R;

import java.text.DateFormat;
import java.util.ArrayList;

public class MyPromosRecyclerViewAdapter extends RecyclerView.Adapter<MyPromosRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "NotifyMe";

    private ArrayList<Promo> mPromos;
    private final OnListFragmentInteractionListener mListener;

    public MyPromosRecyclerViewAdapter(ArrayList<Promo> promos, OnListFragmentInteractionListener listener) {
        mPromos = promos;
        mListener = listener;
    }


    public void updatePromos(ArrayList<Promo> promos) {
        mPromos = promos;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_promos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mPromo = mPromos.get(position);
        holder.mTitleView.setText("HOT DEAL");
        holder.mVendorView.setText(mPromos.get(position).getVendor());
        DateFormat df = DateFormat.getDateInstance();
        holder.mReceiptView.setText(df.format(mPromos.get(position).getReceipt()));
        holder.mVendorLogoView.setImageResource(R.drawable.ic_star);
    }

    @Override
    public int getItemCount() {
        return mPromos.size();
    }

    // TODO: modify this ViewHolder as required
    public class ViewHolder extends RecyclerView.ViewHolder {
        public Promo mPromo;
        public final View mView;
        public final TextView mTitleView;
        public final TextView mVendorView;
        public final TextView mReceiptView;
        public final ImageView mVendorLogoView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        Log.v(TAG, "Promo clicked: " + mPromo.getPromoMsg());
                        mListener.onPromoSelected(mPromo);
                    }
                }
            });
            mTitleView = (TextView)view.findViewById(R.id.title); // promo title
            mVendorView = (TextView)view.findViewById(R.id.vendor); // vendor
            mReceiptView = (TextView)view.findViewById(R.id.receipt); // receipt
            mVendorLogoView = (ImageView)view.findViewById(R.id.list_image); // thumb image (vendor logo)
        }
    }
}
