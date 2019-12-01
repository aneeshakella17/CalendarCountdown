package com.example.calendarcountdown;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mac on 11/19/19.
 */



public class EventDbHelper extends SQLiteOpenHelper {

    public EventDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static final String CREATE_TABLE =
            "CREATE TABLE " + EventHandler.EventProfile.TABLE_NAME + " (" +
                    EventHandler.EventProfile.EVENT_NAME + " TEXT," +
                    EventHandler.EventProfile.DATE + " TEXT," +
                    EventHandler.EventProfile.TIME + " TEXT);";

    private static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + EventHandler.EventProfile.TABLE_NAME;


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "entries.db";

    public EventDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public void addEvent(String name, String date, String time, SQLiteDatabase sqlDb){
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventHandler.EventProfile.EVENT_NAME, name);
        contentValues.put(EventHandler.EventProfile.DATE, date);
        contentValues.put(EventHandler.EventProfile.TIME, time);
        sqlDb.insert(EventHandler.EventProfile.TABLE_NAME, null, contentValues);
    }

    public Cursor readEvent(SQLiteDatabase database){
        String[] projections = {EventHandler.EventProfile.EVENT_NAME, EventHandler.EventProfile.DATE, EventHandler.EventProfile.TIME};
        Cursor cursor = database.query(EventHandler.EventProfile.TABLE_NAME, projections, null, null, null, null, null);
        return cursor;
    }


    public void updateEvent(String name, String date, String time, SQLiteDatabase database){

        ContentValues contentValues = new ContentValues();
        contentValues.put(EventHandler.EventProfile.EVENT_NAME, name);
        contentValues.put(EventHandler.EventProfile.DATE, date);
        contentValues.put(EventHandler.EventProfile.TIME, time);
        String selection = EventHandler.EventProfile.EVENT_NAME + " = '"+ name + "'";

        database.update(EventHandler.EventProfile.TABLE_NAME, contentValues, selection, null);

    }

    public void deleteEvent(String name, SQLiteDatabase database){
        String selection = EventHandler.EventProfile.EVENT_NAME + " = '"+ name + "'";
        database.delete(EventHandler.EventProfile.TABLE_NAME, selection, null);
    }

    public void deleteAll(SQLiteDatabase db){
        db.execSQL(DROP_TABLE);
        db.execSQL(CREATE_TABLE);
    }
}
