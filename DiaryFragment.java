package com.bignerdranch.android.mydiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.Date;



/**
 * Created by ujwalbajgain on 13/9/17.
 */

public class DiaryFragment extends Fragment {

    private static final String ARG_DIARY_ID = "diary_id";
    private static final int REQUEST_PHOTO = 2;

    private static final int REQUEST_MAP = 0;

    private LocationManager locationManager;
    private Location location;

    private Diary mDiary;
    private File mPhotoFile;
    private EditText mTitleField;
    private Button mDateButton;
    private EditText mCommentField;
    private Spinner mTypeField;
    private EditText mPlaceField;
    ///private Spinner mSpinner;
    private ImageButton mPhotoButton;

    private EditText mGpsField;
    private ImageView mPhotoView;
    private ImageView mDeleteButton;
    private ImageView mSaveButton;
    private ImageView mCancelButton;
    private ImageView mGpsButton;
    private EditText mDurationField;

    public static DiaryFragment newInstance(UUID diaryId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DIARY_ID, diaryId);

        DiaryFragment fragment = new DiaryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//
//        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        UUID diaryId = (UUID) getArguments().getSerializable(ARG_DIARY_ID);
        mDiary = DiaryLab.get(getActivity()).getDiary(diaryId);
        mPhotoFile = DiaryLab.get(getActivity()).getPhotoFile(mDiary);
    }
    @Override
    public void onPause(){
        super.onPause();
        if (mDiary.getTitle() != null){
            DiaryLab.get(getActivity()).updateDiary(mDiary);
        } else {
            DiaryLab.get(getActivity()).deleteDiary(mDiary);
        }

        DiaryLab.get(getActivity())
                .updateDiary(mDiary);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_diary, container, false);
        ViewStub stub = (ViewStub) v.findViewById(R.id.layout_btn);

        if (mDiary.getTitle() != null) {
            stub.setLayoutResource(R.layout.diary_edit);
            View inflated = stub.inflate();

            mGpsButton = (ImageView) v.findViewById(R.id.diary_map);
            mGpsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = MapsActivity
                            .maps(getActivity(), mDiary.getId());

                    startActivityForResult(intent, REQUEST_MAP);

                }
            });
            mDeleteButton = (ImageView) v.findViewById(R.id.diary_delete);
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DiaryLab.get(getActivity())
                            .deleteDiary(mDiary);
                    getActivity().onBackPressed();
                }
            });
        } else {
            stub.setLayoutResource(R.layout.diary_add);
            View inflated = stub.inflate();

            mCancelButton= (ImageView) v.findViewById(R.id.diary_cancel);
            mCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DiaryLab.get(getActivity())
                            .deleteDiary(mDiary);
                    getActivity().onBackPressed();
                }
            });

            mSaveButton = (ImageView) v.findViewById(R.id.diary_save);
            mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DiaryLab.get(getActivity())
                            .updateDiary(mDiary);
                    getActivity().onBackPressed();
                }
            });
        }

        mTitleField = (EditText) v.findViewById(R.id.diary_title);
        mTitleField.setText(mDiary.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after){

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDiary.setTitle(s.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mDurationField = (EditText) v.findViewById(R.id.diary_duration);
        mDurationField.setText(mDiary.getDuration());
        mDurationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDiary.setDuration(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mPlaceField = (EditText) v.findViewById(R.id.diary_place);
        mPlaceField.setText(mDiary.getPlace());
        mPlaceField.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after){

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDiary.setPlace(s.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mCommentField = (EditText) v.findViewById(R.id.diary_comments);
        mCommentField.setText(mDiary.getComment());
        mCommentField.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(
                CharSequence s, int start, int count, int after){

        }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            mDiary.setComment(s.toString());

        }

            @Override
            public void afterTextChanged(Editable editable) {

        }
        });

        mGpsField = (EditText) v.findViewById(R.id.diary_gps);
        if (mDiary.getLatitude() == null){
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            mDiary.setLatitude(Double.toString(latitude));
            mDiary.setLongtitude(Double.toString(longitude));
            DiaryLab.get(getActivity())
                    .updateDiary(mDiary);
        }
        mGpsField.setText(mDiary.getLatitude() + ";" + mDiary.getLongtitude());
        mGpsField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTypeField = (Spinner) v.findViewById(R.id.diary_type);
        if (mDiary.getType() != null)
            mTypeField.setSelection(Integer.parseInt(mDiary.getType()));

        mTypeField.setOnItemSelectedListener(new DiaryTypeOnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                mDiary.setType(Integer.toString(pos));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*Spinner mSpinner = (Spinner) findViewById(R.id.activity_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.activity_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(adapter);
*/
        mDateButton = (Button) v.findViewById(R.id.diary_date);
        mDateButton.setText(mDiary.getDate().toString());
        mDateButton.setEnabled(false);

        PackageManager packageManager = getActivity().getPackageManager();

        mPhotoButton = (ImageButton) v.findViewById(R.id.diary_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "com.bignerdranch.android.mydiary.fileprovider",
                        mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = (ImageView) v.findViewById(R.id.diary_photo);
        updatePhotoView();


        return v;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.bignerdranch.android.mydiary.fileprovider",
                    mPhotoFile);

            getActivity().revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updatePhotoView();
        }
    }
    private void updateGps() {
        Log.d("TAG", "" + mDiary.getLongtitude());
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }
}


