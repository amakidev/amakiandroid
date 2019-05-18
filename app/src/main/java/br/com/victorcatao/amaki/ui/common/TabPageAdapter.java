package br.com.victorcatao.amaki.ui.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabPageAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();

    public TabPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return mFragmentList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mTitles.add(title);
    }

    public void setFragment(Fragment fragment, String title, int position) {
        mFragmentList.set(position, fragment);
        mTitles.set(position, title);
    }

    public void clearList() {
        mFragmentList.clear();
        mTitles.clear();
    }

    public String getTitleAtIndex(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

}