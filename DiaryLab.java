package com.bignerdranch.android.mydiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bignerdranch.android.mydiary.database.Database.database.DiaryBaseHelper;
import com.bignerdranch.android.mydiary.database.Database.database.DiaryCursorWrapper;
import com.bignerdranch.android.mydiary.database.Database.database.DiaryDbSchema.DiaryTable;
import com.bignerdranch.android.mydiary.database.Database.database.DiaryDbSchema.SettingsTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.R.attr.id;
import static com.bignerdranch.android.mydiary.database.Database.database.DiaryDbSchema.SettingsTable.*;

/**
 * Created by ujwalbajgain on 13/9/17.
 */

public class DiaryLab {
    private static DiaryLab sDiaryLab;



    private Context mContext;
    private SQLiteDatabase mDatabase;


    public static DiaryLab get (Context context){
        if (sDiaryLab == null){
            sDiaryLab = new DiaryLab(context);
        }
        return sDiaryLab;
    }
    private DiaryLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new DiaryBaseHelper(mContext)
                .getWritableDatabase();



    }
    private static ContentValues getDiaryValues(Diary diary) {
        ContentValues values = new ContentValues();
        values.put(DiaryTable.Cols.UUID, diary.getId().toString());
        values.put(DiaryTable.Cols.TITLE, diary.getTitle());
        values.put(DiaryTable.Cols.PLACE, diary.getPlace());
        values.put(DiaryTable.Cols.DATE, diary.getDate().getTime());
        values.put(DiaryTable.Cols.DURATION, diary.getDuration());
        values.put(DiaryTable.Cols.TYPE, diary.getType());
        values.put(DiaryTable.Cols.COMMENT, diary.getComment());
        values.put(DiaryTable.Cols.LAT, diary.getLatitude());
        values.put(DiaryTable.Cols.LON, diary.getLongtitude());


        return values;
    }


    public void addDiary(Diary d){
        ContentValues values = getDiaryValues(d);
        mDatabase.insert(DiaryTable.NAME, null, values);

    }
    public void updateDiary( Diary diary){
        String uuidString = diary.getId().toString();
        ContentValues values = getDiaryValues(diary);
        Log.d("UPDATE", "" + values.toString());

        mDatabase.update(DiaryTable.NAME, values,
                DiaryTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }
    public void deleteDiary(Diary diary) {
        String uuidString = diary.getId().toString();
        ContentValues values = getDiaryValues(diary);

        mDatabase.delete(DiaryTable.NAME, DiaryTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }



    private DiaryCursorWrapper queryDiaries(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                DiaryTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null

        );
        return new DiaryCursorWrapper(cursor);

    }

    public  List<Diary> getDiaries(){
        List<Diary> diaries = new ArrayList<>();

        DiaryCursorWrapper cursor = queryDiaries(null ,null);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            diaries.add(cursor.getDiary());
            cursor.moveToNext();
        }

            cursor.close();

        return diaries;
    }



    public Diary getDiary(UUID id){
        DiaryCursorWrapper cursor = queryDiaries(
                DiaryTable.Cols.UUID + " = ?",
                new String[]{id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getDiary();

        } finally {
            cursor.close();

        }

    }




    public File getPhotoFile (Diary diary){
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, diary.getPhotoFileName());
    }



    private ContentValues getSContentValues(Settings settings) {
        ContentValues values = new ContentValues();
        values.put(Cols.ID, settings.getId());
        values.put(Cols.NAME, settings.getName());
        values.put(Cols.EMAIL,settings.getEmail());
        values.put(Cols.GENDER, settings.getGender());
        values.put(Cols.COMMENT, settings.getComment());
        return values;
    }

    public void updateSettings(Settings settings) {
        ContentValues values = getSContentValues(settings);
        mDatabase.update(SettingsTable.NAME, values,
                SettingsTable.Cols.ID + " = ?",
                new String[] {String.valueOf(id)});
    }


    private DiaryCursorWrapper querySettings() {
        Cursor cursor = mDatabase.query(
                SettingsTable.NAME,
                null, // Columns - null selects all columns
                null,
                null,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new DiaryCursorWrapper(cursor);
    }












//    public List<Settings> getSettings(){
//        List<Settings> settings = new ArrayList<>();
//        try (SettingsCursorWrapper cursor = querySettings(null, null)) {
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                settings.add(cursor.getSettings());
//                cursor.moveToNext();
//            }
//        }
//        return settings;
//    }
//    public void addSetting(Settings s){
//        ContentValues values = getSContentValues(s);
//        mSettingsDb.insert(SettingsDbSchema.SettingsTable.SET, null, values);
//    }



    public Settings getSettings() {
        DiaryCursorWrapper cursor = querySettings();

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getSettings();
        } finally {
            cursor.close();
        }
    }

}
