package com.bignerdranch.android.mydiary.database.Database.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.mydiary.database.Database.database.DiaryDbSchema.DiaryTable;


import static com.bignerdranch.android.mydiary.database.Database.database.DiaryDbSchema.*;

/**
 * Created by ujwalbajgain on 6/10/17.
 */

public class DiaryBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "diaryBase.db";

    public DiaryBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override

    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + DiaryTable.NAME + "("
                + " _id integer primary key autoincrement, " +
        DiaryTable.Cols.UUID + ", "+
        DiaryTable.Cols.TITLE + ", " +
        DiaryTable.Cols.DATE + ", " +
        DiaryTable.Cols.PLACE +  "," +
        DiaryTable.Cols.TYPE + "," +
        DiaryTable.Cols.LAT + "," +
        DiaryTable.Cols.DURATION + "," +
        DiaryTable.Cols.LON + ")"

        );
        db.execSQL("create table " + SettingsTable.NAME + "(" +
                SettingsTable.Cols.ID + ", " +
                SettingsTable.Cols.NAME + ", " +
                SettingsTable.Cols.EMAIL + ", " +
                SettingsTable.Cols.GENDER + ", " +
                SettingsTable.Cols.COMMENT +
                ")"
        );

        db.execSQL("insert into " + SettingsTable.NAME + " values ('22222', 'Thungsuk Wangdu', 'wangdu@umail.com', '2' , 'No comments')");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
