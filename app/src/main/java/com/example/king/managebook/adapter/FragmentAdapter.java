package com.example.king.managebook.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.king.managebook.R;
import com.example.king.managebook.view.confirmation.ConfirmFragment;
import com.example.king.managebook.view.shop.fragment.category.CategoryFragment;
import com.example.king.managebook.view.shop.fragment.search.SearchFragment;
import com.example.king.managebook.view.statictis.StaticFragment;

import java.util.ArrayList;
import java.util.List;



public class FragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> lsFragment;
    Context context;

    public FragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        lsFragment = new ArrayList<>();
        lsFragment.add(new CategoryFragment());
        lsFragment.add(new SearchFragment());
        lsFragment.add(new ConfirmFragment());
        lsFragment.add(new StaticFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return lsFragment.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: {
                return context.getString(R.string.category);
            }
            case 1: {
                return context.getString(R.string.searching);
            }
            case 2: {
                return context.getString(R.string.confirm);
            }
            case 3: {
                return context.getString(R.string.statictis);
            }
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return lsFragment.size();
    }

    public static int getItemID(int position) {
        switch (position) {
            case 0: {
                return R.id.nav_category;
            }
            case 1: {
                return R.id.nav_search;
            }
            case 2: {
                return R.id.nav_confirm;
            }
            case 3: {
                return R.id.nav_statictis;
            }
            default:{
                return -1;
            }
        }
    }
}
