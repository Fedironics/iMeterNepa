package com.fedironics.imeter.imetercustomer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by madunaguekenedavid on 18/08/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    public final String DB_NAME = "EEDCUtility";
    public final String TABLE_NAME = "blog_posts_eedc";

    public DbHelper(Context context){
        super(context,"EEDCUtility",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TITLE TEXT, " +
                "BODY TEXT, " +
                "ADDED INTEGER, " +
                "MEDIA TEXT, " +
                "LIKES INTEGER) ";
        db.execSQL(CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String upgradeSQL = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        onCreate(db);
    }
    public boolean insertData(String title,String body,String media, int added, int id,int likes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID",id);
        contentValues.put("TITLE",title);
        contentValues.put("BODY",body);
        contentValues.put("MEDIA",media);
        contentValues.put("ADDED",added);
        contentValues.put("LIKES",likes);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result==-1){
            return false;
        }
        return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return res;
    }
    public boolean updateData(String id,String title, String body, String media, int added, int likes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID",id);
        contentValues.put("TITLE",title);
        contentValues.put("BODY",body);
        contentValues.put("MEDIA",media);
        contentValues.put("ADDED",added);
        contentValues.put("LIKES",likes);
        db.update(TABLE_NAME,contentValues,"ID = ?", new String[]{id});
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return   db.delete(TABLE_NAME,"id = ?",new String[]{id});
    }
}
