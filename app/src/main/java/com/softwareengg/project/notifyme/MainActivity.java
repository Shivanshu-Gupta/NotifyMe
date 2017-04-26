package com.softwareengg.project.notifyme;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.softwareengg.project.notifyme.NotifyMeDatabase.NotifyMeContract;
import com.softwareengg.project.notifyme.PromoListFragment.PromoDetailsDialog;
import com.softwareengg.project.notifyme.PromoListFragment.PromoListOperations.FilterDialogFragment;
import com.softwareengg.project.notifyme.PromoListFragment.PromoListOperations.Filter;
import com.softwareengg.project.notifyme.PromoListFragment.PromoListOperations.Sort;
import com.softwareengg.project.notifyme.PromoListFragment.PromoListOperations.SortDialogFragment;
import com.softwareengg.project.notifyme.PromoListFragment.PromosFragment;
import com.softwareengg.project.notifyme.Settings.NotifyMeSettingsActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements PromosFragment.OnListFragmentInteractionListener,
        FilterDialogFragment.FilterDialogListener,
        SortDialogFragment.SortDialogListener {
    private static final String TAG = "NotifyMe";
    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_home);
        ab.setDisplayHomeAsUpEnabled(true);

        // Create the adapter that will return a fragment for each promo category.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        setupTabs();

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        Bundle b = getIntent().getExtras();
        if(b != null) {
            mViewPager.setCurrentItem(b.getInt("Category"));
        }
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();
    }

    private void setupTabs() {
        String[] categories = getResources().getStringArray(R.array.categories);
        for(int i = 0; i < categories.length; i++) {
            String category = categories[i];
            Filter filter = new Filter();
            if(i > 0) filter.setCategory(category);
            Sort sort = new Sort(0, 0);
            mSectionsPagerAdapter.addFragment(PromosFragment.newInstance(filter, sort), category);
        }
    }

    private void setupTabIcons() {
        TypedArray categoryIcons = getResources().obtainTypedArray(R.array.category_icons);
        for(int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setIcon(categoryIcons.getDrawable(i));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.v(TAG, "Filter : current tab : " + mViewPager.getCurrentItem());
        String name = makeFragmentName(mViewPager.getId(), mViewPager.getCurrentItem());
        PromosFragment promosFragment = (PromosFragment) getSupportFragmentManager().findFragmentByTag(name);

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_filter:
//                DialogFragment newFragment = new VendorFilterDialogFragment();
                if(promosFragment != null) {
                    FilterDialogFragment newFragment = FilterDialogFragment.newInstance(promosFragment.mFilter);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.add(android.R.id.content, newFragment)
                            .addToBackStack(null).commit();
                }
                return true;

            case R.id.action_sort:
                // User chose the "Settings" item, show the app settings UI...
                if(promosFragment != null) {
                    SortDialogFragment newFragment = SortDialogFragment.newInstance(promosFragment.mSort);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.add(android.R.id.content, newFragment)
                            .addToBackStack(null).commit();
                }
                return true;

//            case R.id.action_delete:
//                // User chose the "Settings" item, show the app settings UI...
//                return true;

            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Intent settingsIntent = new Intent(this, NotifyMeSettingsActivity.class);
                startActivity(settingsIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<PromosFragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public PromosFragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(PromosFragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private static String makeFragmentName(int viewId, int position) {
        return "android:switcher:" + viewId + ":" + position;
    }


    @Override
    public void onApplyFilter(DialogFragment dialog, Filter filter) {
        Log.v(TAG, TextUtils.join(", ", filter.getVendors()));
        String name = makeFragmentName(mViewPager.getId(), mViewPager.getCurrentItem());
        PromosFragment viewPagerFragment = (PromosFragment) getSupportFragmentManager().findFragmentByTag(name);
        viewPagerFragment.updateFilter(filter);
    }


    @Override
    public void onApplySort(DialogFragment dialog, Sort sort) {
        Log.v(TAG, String.valueOf(sort.getCriteria()));
        String name = makeFragmentName(mViewPager.getId(), mViewPager.getCurrentItem());
        PromosFragment viewPagerFragment = (PromosFragment) getSupportFragmentManager().findFragmentByTag(name);
        viewPagerFragment.updateSort(sort);
    }

    @Override
    public void onPromoSelected(Promo promo) {
        PromoDetailsDialog promoDetailsDialog = PromoDetailsDialog.newInstance(promo);
        promoDetailsDialog.show(getSupportFragmentManager(), "promo");
    }

    private class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
