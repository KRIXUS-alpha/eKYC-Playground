package com.example.ekyc_playground;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {

    public static final String DBNAME = "Register.db";
    public DB(@Nullable Context context) {
        super(context, "Register.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users(FullName TEXT primary key,postaladdress TEXT,pan TEXT,Aadhar TEXT,Email TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
    }

    public boolean insertData(String username, String postaladdress, String pan,Number Aadhar ,String Email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("postaladdress",postaladdress );
        values.put("pan", pan);
        values.put("Aadhar", (Integer) Aadhar);
        values.put("Email", Email);

        long result = sqLiteDatabase.insert("users", null,values);
        if (result==-1) return false;
        else
            return true;
    }
    public Boolean checkusername(String username){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from users where username=?", new String[] {username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public Boolean checkpostaladdress(String postaladdress){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String username = null;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from users where username=? and postaladdress=?", new String[] {username,postaladdress});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public Boolean checkpan(String pan){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String username = null;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from users where username=? and pan=?", new String[] {username,pan});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public Boolean checkAadhar(Number Aadhar){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String username = null;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from users where username=? and Aadhar=?", new String[] {username, String.valueOf(Aadhar)});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public Boolean checkEmail(String Email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String username = null;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from users where username=? and Email=?", new String[] {username,Email});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
}
