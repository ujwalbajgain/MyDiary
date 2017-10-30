package com.bignerdranch.android.mydiary;

import android.support.v4.app.Fragment;

/**
 * Created by ujwalbajgain on 13/9/17.
 */

public class DiaryListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new DiaryListFragment();
    }
}

