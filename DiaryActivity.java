package com.bignerdranch.android.mydiary;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.UUID;

public class DiaryActivity extends AppCompatActivity {
    private static final String EXTRA_DIARY_ID =
            "com.bignerdranch.android.mydiary.diary_id";

    private ViewPager mViewPager;
    private List<Diary> mDiaries;
    public static Intent diary (Context packageContext, UUID diaryId){
        Intent intent = new Intent(packageContext, DiaryActivity.class);
        intent.putExtra(EXTRA_DIARY_ID, diaryId);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_pager);

        UUID diaryId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_DIARY_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_diary_pager_view_pager);

        mDiaries = DiaryLab.get(this).getDiaries();
        FragmentManager fragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Diary diary = mDiaries.get(position);
                return DiaryFragment.newInstance(diary.getId());
            }

            @Override
            public int getCount() {
                return mDiaries.size();
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                Diary diary = mDiaries.get(position);
                if (diary.getTitle() != null) {
                    setTitle(diary.getTitle());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        for (int i = 0; i < mDiaries.size(); i++) {
            if (mDiaries.get(i).getId().equals(diaryId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }


}
