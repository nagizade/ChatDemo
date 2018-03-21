package com.nagizade.chatdemo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nagizade.chatdemo.AppTabs.ChatsFragment;
import com.nagizade.chatdemo.AppTabs.ContactsFragment;

/**
 * Created by Hasan Nagizade on 3/21/2018.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public FragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ContactsFragment();
        } else {
            return new ChatsFragment();
        }
    }

    // Number of tabs
    @Override
    public int getCount() {
        return 2;
    }

    // Titles for tabs
    @Override
    public CharSequence getPageTitle(int position) {
        // Getting title from position
        switch (position) {
            case 0:
                return mContext.getString(R.string.contacts_tab_title);
            case 1:
                return mContext.getString(R.string.chats_tab_title);
            default:
                return null;
        }
    }

}
