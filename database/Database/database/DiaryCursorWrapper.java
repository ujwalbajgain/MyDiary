package com.bignerdranch.android.mydiary.database.Database.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.mydiary.Diary;
import com.bignerdranch.android.mydiary.Settings;
import com.bignerdranch.android.mydiary.database.Database.database.DiaryDbSchema.DiaryTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by ujwalbajgain on 6/10/17.
 */

public class DiaryCursorWrapper extends CursorWrapper {
    public DiaryCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Diary getDiary() {
        String uuidString = getString(getColumnIndex(DiaryTable.Cols.UUID));
        String title = getString(getColumnIndex(DiaryTable.Cols.TITLE));
        long date = getLong(getColumnIndex(DiaryTable.Cols.DATE));
        //String comment = getString(getColumnIndex(DiaryDbSchema.DiaryTable.Cols.COMMENT));
        String place = getString(getColumnIndex(DiaryTable.Cols.PLACE));
        String comment = getString(getColumnIndex(DiaryTable.Cols.COMMENT));
        String type = getString(getColumnIndex(DiaryTable.Cols.TYPE));
        String lat = getString(getColumnIndex(DiaryTable.Cols.LAT));
        String lon = getString(getColumnIndex(DiaryTable.Cols.LON));
        String duration = getString(getColumnIndex(DiaryTable.Cols.DURATION));



        Diary diary = new Diary(UUID.fromString(uuidString));
        diary.setTitle(title);
        diary.setDate(new Date(date));
        //diary.setComment(comment);
        diary.setPlace(place);
        diary.setComment(comment);
        diary.setType(type);
        diary.setLatitude(lat);
        diary.setLongtitude(lon);
        diary.setDuration(duration);



        return diary;
    }
    public Settings getSettings() {
        String id = getString(getColumnIndex(DiaryDbSchema.SettingsTable.Cols.ID));
        String name = getString(getColumnIndex(DiaryDbSchema.SettingsTable.Cols.NAME));
        String email = getString(getColumnIndex(DiaryDbSchema.SettingsTable.Cols.EMAIL));
        String gender = getString(getColumnIndex(DiaryDbSchema.SettingsTable.Cols.GENDER));
        String comment = getString(getColumnIndex(DiaryDbSchema.SettingsTable.Cols.COMMENT));

        Settings settings = new Settings();
        settings.setId(id);
        settings.setName(name);
        settings.setEmail(email);
        settings.setGender(gender);
        settings.setComment(comment);

        return settings;
    }
}

