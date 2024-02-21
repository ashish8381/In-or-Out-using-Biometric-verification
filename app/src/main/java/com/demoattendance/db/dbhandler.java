package com.demoattendance.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.demoattendance.modal.attendanceModel;

import java.util.ArrayList;

public class dbhandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "demoattendance";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "attendance";
    private static final String ID_COL = "id";
    private static final String IN_TIME = "in_timestamp";

    static Context context;

    public dbhandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String query = "CREATE TABLE " + TABLE_NAME + " ("
                    + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + IN_TIME + " TEXT)";
            db.execSQL(query);
            Toast.makeText(context, "Database created", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error creating database", Toast.LENGTH_SHORT).show();
        }
    }

    public void addNewActivity(String in_time) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(IN_TIME, in_time);

        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }


    public boolean isTableEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count=0;
        try {
             count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
//            Toast.makeText(context, "count= "+count, Toast.LENGTH_SHORT).show();
            return count == 0;
        } finally {
//            Toast.makeText(context, "empty", Toast.LENGTH_SHORT).show();
            db.close();
        }

    }

    // we have created a new method for reading all the courses.
    public ArrayList<attendanceModel> readCourses()
    {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to
        // read data from database.
        Cursor cursor
                = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<attendanceModel> attendanceModelArrayList
                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                // on below line we are adding the data from
                // cursor to our array list.
                attendanceModelArrayList.add(new attendanceModel(
                        cursor.getInt(0),
                        cursor.getString(1)
                        ));
            } while (cursor.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursor.close();
        return attendanceModelArrayList;
    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
