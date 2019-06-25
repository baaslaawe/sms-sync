package com.taba.apps.smssync.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;

import com.taba.apps.smssync.sms.Sms;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    protected static final String NAME = "sms_sync.db";
    protected static final int VERSION = 2;

    private Context context;

    public Database(Context context) {
        super(context, Database.NAME, null, Database.VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(getSmsTableCreationQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS sms");
        this.onCreate(sqLiteDatabase);
    }

    private static final String getSmsTableCreationQuery() {
        String query = "";

        query += "CREATE TABLE IF NOT EXISTS sms ";
        query += "(";
        query += "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,";
        query += "senderPhone VARCHAR(16) NOT NULL,";
        query += "message TEXT NOT NULL,";
        query += "receivedTime TIMESTAMP NOT NULL DEFAULT current_timestamp,";
        query += "status INTEGER(1) DEFAULT 0";
        query += ")";

        return query;
    }

    public boolean addSms(Sms sms) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("senderPhone", sms.getSenderPhone());
        values.put("message", sms.getMessage());
        values.put("receivedTime", sms.getReceivedTime());
        boolean success = database.insert("sms", null, values) != -1;
        database.close();
        return success;
    }



    public List<Sms> getMessages() {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "";
        query += "SELECT * FROM sms";
        Cursor cursor = database.rawQuery(query, null);

        List<Sms> messages= new ArrayList<>();

        while (cursor.moveToNext()) {
            Sms sms = new Sms();
            sms.setId(cursor.getInt(cursor.getColumnIndex("id")));
            sms.setSenderPhone(cursor.getString(cursor.getColumnIndex("senderPhone")));
            sms.setMessage(cursor.getString(cursor.getColumnIndex("message")));
            sms.setReceivedTime(cursor.getString(cursor.getColumnIndex("receivedTime")));
            sms.setStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex("status"))));
            messages.add(sms);
        }

        cursor.close();
        database.close();
        return messages;
    }

    public boolean synchronizeSms(int smsId) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", Sms.STATUS_SYNCHRONIZED);
        int updateStatus = database.update("sms", values, " id = " + smsId, null);
        boolean success = updateStatus != -1;
        database.close();
        return success;
    }



}
