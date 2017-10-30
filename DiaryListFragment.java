package com.bignerdranch.android.mydiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static com.bignerdranch.android.mydiary.R.menu.diary;

/**
 * Created by ujwalbajgain on 13/9/17.
 */

public class DiaryListFragment extends Fragment {

    private RecyclerView mDiaryRecyclerView;
    private DiaryAdapter mAdapter;

    @Override

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary_list, container, false);

        mDiaryRecyclerView = (RecyclerView) view
                .findViewById(R.id.diary_recycler_view);
        mDiaryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }
    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_diary_list, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.new_diary:
                Diary diary =  new Diary();

                DiaryLab.get(getActivity()).addDiary(diary);
                Intent intent;
                intent = DiaryActivity
                        .diary(getActivity(), diary.getId());
                startActivity(intent);
                return true;
            case R.id.user_settings:
                Settings mSettings = DiaryLab.get(getActivity()).getSettings();

                Intent settingsIntent = SettingsActivity
                        .settings(getActivity(), mSettings.getId());
                startActivity(settingsIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void updateUI(){
        DiaryLab diaryLab = DiaryLab.get(getActivity());

        List<Diary> diaries;
        diaries = diaryLab.getDiaries();

        if (mAdapter == null) {
            mAdapter = new DiaryAdapter(diaries);
            mDiaryRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setDiaries(diaries);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class DiaryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mPlaceTextView;
        private Diary mDiary;


        public DiaryHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.diary_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.diary_date);
            mPlaceTextView = (TextView) itemView.findViewById(R.id.diary_place);

        }
        private void bindDiary(Diary diary){
            mDiary = diary;
            mTitleTextView.setText(mDiary.getTitle());

            mPlaceTextView.setText(mDiary.getPlace());
            mDateTextView.setText(mDiary.getDate().toString());

        }
        @Override
        public void onClick(View v){
            Intent intent = DiaryActivity.diary(getActivity(), mDiary.getId());
            startActivity(intent);
        }
    }

    private class DiaryAdapter extends RecyclerView.Adapter<DiaryHolder> {
        private List<Diary> mDiaries;

        public DiaryAdapter(List<Diary> diaries) {
            mDiaries = diaries;
        }

        @Override
        public DiaryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_diary, parent, false);
            return new DiaryHolder(view);
        }

        @Override
        public void onBindViewHolder(DiaryHolder holder, int position) {
            Diary diary = mDiaries.get(position);
            holder.bindDiary(diary);

        }

        @Override
        public int getItemCount() {
            return mDiaries.size();
        }

        public void setDiaries(List<Diary> diaries){
            mDiaries = diaries;
        }


    }
}
