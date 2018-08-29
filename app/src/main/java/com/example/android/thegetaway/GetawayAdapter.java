package com.example.android.thegetaway;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.thegetaway.Dates.Eleven;
import com.example.android.thegetaway.Dates.Fourteen;
import com.example.android.thegetaway.Dates.Ninth;
import com.example.android.thegetaway.Dates.Ten;
import com.example.android.thegetaway.Dates.Thirteen;
import com.example.android.thegetaway.Dates.Twelve;

public class GetawayAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] { "10/9", "10/10", "10/11", "10/12", "10/13", "10/14" };

    public GetawayAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Ninth();
        } else if (position == 1){
            return new Ten();
        } else if (position == 2){
            return new Eleven();
        }else if (position == 3){
            return new Twelve();
        }else if (position == 4){
            return new Thirteen();
        } else {
            return new Fourteen();
        }
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
