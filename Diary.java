package com.bignerdranch.android.mydiary;

import java.util.Date;
import java.util.UUID;

/**
 * Created by ujwalbajgain on 12/9/17.
 */

public class Diary
{
    private UUID mId;

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }
    public String getPlace(){
        return mPlace;
    }
    public void setPlace(String place){
        mPlace = place;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getLongtitude() { return mLon; }
    public void setLongtitude(String lon) { mLon = lon; }

    public String getLatitude() {
        return mLat;
    }
    public void setLatitude(String lat) { mLat = lat; }

    public String getType() {
        return mType;
    }
    public void setType(String type) { mType = type; }

    public String getDuration() {
        return mDuration;
    }
    public void setDuration(String duration) {
        mDuration = duration;
    }



    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
    public String getComment() {
        return mComment;
     }

    public void setComment(String comment){
        mComment= comment;

    }
    public String getPhotoFileName(){
        return "IMG_" +getId().toString() +".jpg";
    }

    private String mTitle;
    private Date mDate;
    private String mComment;
    private String mPlace;
    private String mType;
    private String mLat;
    private String mLon;
    private String mDuration;


    public Diary(){
        this(UUID.randomUUID());
    }


    public Diary(UUID id){
        mId = id;
        mDate = new Date();


    }
}
