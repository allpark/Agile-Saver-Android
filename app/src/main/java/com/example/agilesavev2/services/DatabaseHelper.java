package com.example.agilesavev2.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.agilesavev2.assets.Presenter;

import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE = "AgileSaver.db";

    public static String ACCOUNT_TABLE = "account";
    public static String TRANSACTION_TABLE = "transactions";
    public static String BUDGET_TABLE = "budgets";
    public static String SUBSCRIPTIONS_TABLE = "subscriptions";
    public static String CURRENCY_CONVERSION_CHART = "currency_conversion_chart";
    public static String USER_PAY_INFO = "userPayInfo";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE if not exists "+TRANSACTION_TABLE+"(\n" +
                "    id  INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
                "    userID INTEGER,\n" +
                "    accountID INTEGER,\n" +
                "    transactionValue FLOAT,\n" +
                "    type TEXT,\n" +
                "    currency TEXT,\n" +
                "    subCategory TEXT,\n" +
                "    date TIMESTAMP,\n" +
                "    name TEXT,\n" +
                "    category TEXT,\n" +
                "    balance FLOAT,\n" +
                "    FOREIGN KEY (accountID) REFERENCES account(id)\n" +
                ");");

        db.execSQL("CREATE TABLE if not exists "+ACCOUNT_TABLE+"(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    userID INTEGER,\n" +
                "    currency TEXT,\n" +
                "    balance FLOAT,\n" +
                "    name  TEXT,\n" +
                "    type TEXT, \n" +
                "    main BOOLEAN \n" +
                ");");

        db.execSQL("CREATE TABLE if not exists "+BUDGET_TABLE+"(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    userID INTEGER,\n" +
                "    target FLOAT,\n" +
                "    name TEXT,\n" +
                "    passed BOOLEAN,\n" +
                "    date DATE, \n" +
                "    progress FLOAT, \n"+
                "    category TEXT \n"+
                ");");


        db.execSQL("CREATE TABLE if not exists "+SUBSCRIPTIONS_TABLE+"(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    userID INTEGER,\n" +
                "    name TEXT,\n" +
                "    category TEXT,\n" +
                "    started BOOLEAN,\n" +
                "    subscriptionValue FLOAT \n" +
                ");");

        db.execSQL("CREATE TABLE if not exists "+CURRENCY_CONVERSION_CHART+"(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    CURRENCY INTEGER,\n" +
                "    CONVERSION_VALUE FLOAT\n" +
                ");");

        db.execSQL("CREATE TABLE if not exists "+USER_PAY_INFO+"(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    userID INTEGER,\n" +
                "    payDayType  TEXT,\n" +
                "    nextPayDate  TIMESTAMP,\n" +
                "    pay FLOAT\n" +
                ");");

        db.execSQL("CREATE TABLE if not exists categories(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    userID INTEGER,\n" +
                "    categoryName TEXT\n" +
                ");");


        db.execSQL("CREATE TABLE if not exists subscription_avg(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    category TEXT,\n" +
                "    avg FLOAT\n" +
                "\n" +
                ");");

        db.execSQL("CREATE TABLE if not exists savings (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    userID INTEGER,\n" +
                "    goalName TEXT,\n" +
                "    targetDate DATE,\n" +
                "    startDate DATE,   \n" +
                "    reached BOOLEAN ,\n" +
                "    progress FLOAT,\n" +
                "    targetGoal FLOAT,\n" +
                "    achievedDate DATE\n" +
                "   \n" +
                ");");





        db.execSQL("CREATE TABLE if not exists saved_login(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    username TEXT,\n" +
                "    password TEXT\n" +
                ");");





    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +ACCOUNT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +TRANSACTION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +BUDGET_TABLE);
        onCreate(db);
    }

    public void populateTable (String table ,HashMap<String,String> map){
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
        ContentValues contentValues = new ContentValues();
        for(Map.Entry<String,String> entry : map.entrySet()){
            contentValues.put(entry.getKey(),entry.getValue());
        }
        db.insert(table, null, contentValues);
    }

    public boolean ifTableExists (String table, String userID){
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
        Cursor res = db.rawQuery("SELECT * FROM "+table+" WHERE userID = "+userID,null);
        return res.getCount()>0;

    }

    public Cursor doQuery(String query){
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
        Cursor res= db.rawQuery(query,null);
        return res;
    }

    public void executeSQL(String query){
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
        db.execSQL(query);
    }

    public void truncateTable(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
        db.execSQL("delete from "+ tableName+"; DBCC CHECKIDENT ('"+tableName+"', RESEED, 0)");
      //  db.execSQL("UPDATE `sqlite_sequence` SET `seq` = 0 WHERE `name` = '"+tableName+"';");
    }

    public boolean insertIntoTable(String table, ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
        long res = db.insert(table,null,contentValues);
        return res!=-1;
    }

    public boolean ifTableIsEmpty(String tableName, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
        Cursor res= db.rawQuery("SELECT COUNT(*) FROM "+tableName+" WHERE userID = "+id, null);
        res.moveToFirst();
        return res.getInt(0)==0;
    }

    public void update(String tableName,HashMap<String, String> map , String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for(Map.Entry<String, String> entry : map.entrySet()) contentValues.put(entry.getKey(),entry.getValue());
        db.update(tableName, contentValues, id, new String[]{id});
    }

    public void dropTable(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+tableName);
    }













}



