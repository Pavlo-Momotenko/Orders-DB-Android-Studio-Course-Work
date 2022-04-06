package com.example.coursework_ordersdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class OrdersDBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Orders.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "orders";

    private static final String COLUMN_ID = "id_";
    private static final String COLUMN_ORDER_CODE = "order_code";
    private static final String COLUMN_OPERATOR_CODE = "operator_code";
    private static final String COLUMN_HOTEL_CODE = "hotel_code";
    private static final String COLUMN_ORDER_DATE = "order_date";
    private static final String COlUMN_NUMBER_OF_ORDERED_ROOMS = "number_of_ordered_rooms";

    OrdersDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ORDER_CODE + " INTEGER, " +
                COLUMN_OPERATOR_CODE + " INTEGER, " +
                COLUMN_HOTEL_CODE + " INTEGER, " +
                COLUMN_ORDER_DATE + " TEXT, " +
                COlUMN_NUMBER_OF_ORDERED_ROOMS + " INTEGER);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(sqLiteDatabase);
    }

    void addOrder(int order_code, int operator_code, int hotel_code, String order_date, int number_of_ordered_rooms) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ORDER_CODE, order_code);
        cv.put(COLUMN_OPERATOR_CODE, operator_code);
        cv.put(COLUMN_HOTEL_CODE, hotel_code);
        cv.put(COLUMN_ORDER_DATE, order_date);
        cv.put(COlUMN_NUMBER_OF_ORDERED_ROOMS, number_of_ordered_rooms);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Creating failed, try again", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_LONG).show();
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    void updateData(String row_id, int order_code, int operator_code, int hotel_code, String order_date, int number_of_ordered_rooms) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ORDER_CODE, order_code);
        cv.put(COLUMN_OPERATOR_CODE, operator_code);
        cv.put(COLUMN_HOTEL_CODE, hotel_code);
        cv.put(COLUMN_ORDER_DATE, order_date);
        cv.put(COlUMN_NUMBER_OF_ORDERED_ROOMS, number_of_ordered_rooms);
        long result = db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Updating failed, try again", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Updated successfully!", Toast.LENGTH_LONG).show();
        }
    }

    Cursor filterData(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete item", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_LONG).show();
        }
    }

    String createFilterQuery(String row_id, String order_code, String operator_code,
                             String hotel_code, String order_date, String number_of_ordered_rooms) {
        String query = "SELECT * FROM " + TABLE_NAME;

        if (row_id != null || order_code != null || operator_code != null || hotel_code != null || order_date != null || number_of_ordered_rooms != null) {
            query += " WHERE " + COLUMN_ID + " != -1";
        }

        if (!"".equals(order_code)) {
            query += " AND " + COLUMN_ORDER_CODE + " = " + operator_code;
        }

        if (!"".equals(operator_code)) {
            query += " AND " + COLUMN_OPERATOR_CODE + " = " + operator_code;
        }

        if (!"".equals(hotel_code)) {
            query += " AND " + COLUMN_HOTEL_CODE + " = " + hotel_code;
        }

        if (!"".equals(order_date)) {
            query += " AND " + COLUMN_ORDER_DATE + " = " + order_date;
        }

        if (!"".equals(number_of_ordered_rooms)) {
            query += " AND " + COlUMN_NUMBER_OF_ORDERED_ROOMS + " = " + number_of_ordered_rooms;
        }


        query += ";";
        return query;
    }
}
