package com.example.prasi.wordchallenges.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.prasi.wordchallenges.R;
import com.example.prasi.wordchallenges.fragment.AddFragment.AddWordFragment;
import com.example.prasi.wordchallenges.fragment.AddFragment.AddWordTodayFragment;
import com.example.prasi.wordchallenges.utils.Contextor;

public class AddWordPagerAdapter extends FragmentStatePagerAdapter {
    Context context;

    private final int PAGE_NUM = 2;
    public AddWordPagerAdapter(FragmentManager fm,Context mContext) {
        super(fm);
        context = mContext;
    }
    @Override
    public int getCount() {
        return PAGE_NUM;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new AddWordFragment();
        }else if (position == 1){
            return new AddWordTodayFragment();
        }
        return null;
    }
    @Override
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0 :
                return context.getString(R.string.add_word_pager);
            case 1 :
                return context.getString(R.string.add_word_today_pager);
        }
        return null;
    }
}
