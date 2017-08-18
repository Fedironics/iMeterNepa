package com.fedironics.imeter.imetercustomer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by madunaguekenedavid on 18/08/2017.
 */

public class DbHelper {
    public final String DB_NAME = "EEDCUtility";
    public final String TABLE_NAME = "blog_posts_eedc";

    public DbHelper(Context context){
        //super(context,DB_NAME,null,1);
    }
 //   @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT, BODY TEXT, ADDED INTEGER, MEDIA TEXT, LIKES INTEGER) ";
   //     db.execSQL(CREATE_SQL);
    }

 //   @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String upgradeSQL = "DROP TABLE IF EXISTS "+ TABLE_NAME;
 //       onCreate(db);
    }
    public boolean insertData(){
        return true;
    }
}
