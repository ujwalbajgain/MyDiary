package com.bignerdranch.android.mydiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import static android.R.attr.id;

/**
 * Created by ujwalbajgain on 13/10/17.
 */

public class SettingsFragment extends Fragment{
    private static final String ARG_SETTINGS_ID = "settings_id";
    private Settings mSettings = DiaryLab.get(getActivity()).getSettings();
    private EditText mNameField;
    private EditText mIdField;
    private EditText mEmailField;
    private EditText mCommentField;
    private EditText mGenderField;

    public static SettingsFragment newInstance(String settingId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_SETTINGS_ID, id);

        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override

    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mSettings = new Settings();
        //UUID settingsId = (UUID) getArguments().getSerializable(ARG_SETTINGS_ID);
        mSettings = DiaryLab.get(getActivity()).getSettings();
    }



    @Override
    public void onPause() {
        super.onPause();
        DiaryLab.get(getActivity())
                .updateSettings(mSettings);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        mNameField = (EditText)v.findViewById(R.id.settings_name);
        mNameField.setText(mSettings.getName());
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mSettings.setName(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mIdField = (EditText)v.findViewById(R.id.settings_id);
        mIdField.setText(mSettings.getId());
        mIdField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
// This space intentionally left blank
            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mSettings.setId(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
// This one too
            }
        });

        mEmailField = (EditText)v.findViewById(R.id.settings_email);
        mEmailField.setText(mSettings.getEmail());
        mEmailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
// This space intentionally left blank
            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mSettings.setEmail(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
// This one too
            }
        });


        mCommentField= (EditText)v.findViewById(R.id.settings_comments);
        mCommentField.setText(mSettings.getComment());
        mCommentField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
// This space intentionally left blank
            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mSettings.setComment(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }
    }

}


