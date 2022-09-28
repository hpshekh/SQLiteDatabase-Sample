package com.hpshekh.sqlitedatabase_sample.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hpshekh.sqlitedatabase_sample.model.Contact;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contact_db";
    private static final String TABLE_CONTACT = "contact";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_MOBILE = "mobile";
    private static final String COL_EMAIL = "email";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + TABLE_CONTACT + " ("
                + COL_ID + " integer primary key, "
                + COL_NAME + " text, "
                + COL_MOBILE + " text, "
                + COL_EMAIL + " text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_CONTACT);
        onCreate(db);
    }

    public boolean insert(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, contact.getName());
        contentValues.put(COL_MOBILE, contact.getName());
        contentValues.put(COL_EMAIL, contact.getEmail());
        long result = db.insert(TABLE_CONTACT, null, contentValues);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Contact> contactsList() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_CONTACT, null);

        ArrayList<Contact> contactList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(0));
                contact.setName(cursor.getString(1));
                contact.setMobile(cursor.getString(2));
                contact.setEmail(cursor.getString(3));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return contactList;
    }

    public Contact contactSingle(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACT + " WHERE " + COL_ID + " = '" + id + "'";
        Cursor cursor = db.rawQuery(query, null);

        Contact contact = new Contact();

        if (cursor != null) {
            cursor.moveToFirst();
            contact.setId(cursor.getInt(0));
            contact.setName(cursor.getString(1));
            contact.setMobile(cursor.getString(2));
            contact.setEmail(cursor.getString(3));
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }

        return contact;
    }

    public boolean checkMobile(String mobile) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_CONTACT + " where " + COL_MOBILE + " = ?", new String[]{mobile});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_CONTACT, COL_ID + " = ?", new String[]{String.valueOf(id)});
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_CONTACT, "1", null);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean update(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, contact.getName());
        contentValues.put(COL_MOBILE, contact.getMobile());
        contentValues.put(COL_EMAIL, contact.getEmail());
        long result = db.update(TABLE_CONTACT, contentValues, COL_ID + " ?", new String[]{String.valueOf(contact.getId())});
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }
}
