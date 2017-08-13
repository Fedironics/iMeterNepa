package com.fedironics.imeter.imetercustomer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by madunaguekenedavid on 12/08/2017.
 */

public class MyDatabase extends SQLiteOpenHelper {
    private String DBNAME = "eedcutility";

    MyDatabase(Context context, String DBNAME) {
        super(context, DBNAME, null, 1);
        this.DBNAME = DBNAME;
    }

    private static final String SQL_CREATE_MAIN = "CREATE TABLE " +
            "main " +                       // Table's name
            "(" +                           // The columns in the table
            " _ID INTEGER PRIMARY KEY, " +
            " WORD TEXT"+
    " FREQUENCY INTEGER " +
            " LOCALE TEXT )";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
