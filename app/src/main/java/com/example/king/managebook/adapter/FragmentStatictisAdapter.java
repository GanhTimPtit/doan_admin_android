package com.example.king.managebook.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.king.managebook.R;
import com.example.king.managebook.view.statictis.fragment.bill.BillStaticFragment;
import com.example.king.managebook.view.statictis.fragment.customer.CustomerStaticFragment;
import com.example.king.managebook.view.statictis.fragment.product.ProductStatictisFragment;

import java.util.ArrayList;
import java.util.List;


public class FragmentStatictisAdapter extends FragmentPagerAdapter {
    List<Fragment> lsFragment;
    Context context;

    public FragmentStatictisAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        lsFragment = new ArrayList<>();
        lsFragment.add(new CustomerStaticFragment());
        lsFragment.add(new ProductStatictisFragment());
        lsFragment.add(new BillStaticFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return lsFragment.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: {
                return context.getString(R.string.customer);
            }
            case 1: {
                return context.getString(R.string.clothes);
            }
            case 2: {
                return context.getString(R.string.income);
            }
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return lsFragment.size();
    }

}
