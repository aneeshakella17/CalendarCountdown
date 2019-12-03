package com.example.calendarcountdown;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DirectoryDbHelper extends SQLiteOpenHelper {
    public DirectoryDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static final String CREATE_TABLE =
            "CREATE TABLE " + EventHandler.EventProfile.TABLE_NAME + " (" +
                    DirectoryHandler.DirectoryProfile.DIRECTORY_NAME + " TEXT);";

    private static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + EventHandler.EventProfile.TABLE_NAME;


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "directories.db";

    public DirectoryDbHelper(Context context) {
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


    public void addDirectory(String name, SQLiteDatabase sqlDb){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DirectoryHandler.DirectoryProfile.DIRECTORY_NAME, name);
        sqlDb.insert(DirectoryHandler.DirectoryProfile.TABLE_NAME, null, contentValues);
    }

    public Cursor readDirectory(SQLiteDatabase database){
        String[] projections = {DirectoryHandler.DirectoryProfile.DIRECTORY_NAME};
        Cursor cursor = database.query(DirectoryHandler.DirectoryProfile.TABLE_NAME, projections, null, null, null, null, null);
        return cursor;
    }


    public void updateDirectory(String name, String date, String time, SQLiteDatabase database){

        ContentValues contentValues = new ContentValues();
        contentValues.put(DirectoryHandler.DirectoryProfile.DIRECTORY_NAME, name);
        String selection = DirectoryHandler.DirectoryProfile.DIRECTORY_NAME + " = '"+ name + "'";
        database.update(DirectoryHandler.DirectoryProfile.TABLE_NAME, contentValues, selection, null);

    }

    public void updateDirectory(String name, SQLiteDatabase database){
        String selection = DirectoryHandler.DirectoryProfile.DIRECTORY_NAME + " = '"+ name + "'";
        database.delete(DirectoryHandler.DirectoryProfile.TABLE_NAME, selection, null);
    }

    public void deleteAll(SQLiteDatabase db){
        db.execSQL(DROP_TABLE);
        db.execSQL(CREATE_TABLE);
    }
}
