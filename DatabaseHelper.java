package com.my.toko;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

public class DatabaseHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "tokokasir.db";
  private static final int DATABASE_VERSION = 1;

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    // Buat tabel
    String sql =
      "create table produk (np INTEGER PRIMARY KEY AUTOINCREMENT, nama text, harga_beli numeric, harga_jual numeric);";
    Log.d("DATA", sql);
    db.execSQL(sql);

    // Insert data
    sql =
      "INSERT INTO produk (nama, harga_beli, harga_jual) VALUES ('Permen Mint', 200, 500);";
    db.execSQL(sql);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    db.execSQL("DROP TABLE IF EXISTS 'produk'");
    onCreate(db);
  }

  public String siud(String query) {
    SQLiteDatabase db = this.getWritableDatabase();
    try {
      db.execSQL(query);
      return "OK";
    } catch (Exception e) {
      return "";
    }
  }

  public String getData(String table) {
    String selectQuery = "SELECT * FROM " + table;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor c = db.rawQuery(selectQuery, null);

    JSONArray jsonArray = new JSONArray();
    if (c.moveToFirst()) {
      Integer ncol = c.getColumnCount();
      do {
        JSONObject row = new JSONObject();
        for (int i = 0; i < ncol; i++) {
          if (c.getColumnName(i) != null) {
            try {
              if (c.getString(i) != null) {
                row.put(c.getColumnName(i), c.getString(i));
              } else {
                row.put(c.getColumnName(i), "");
              }
            } catch (Exception e) {}
          } // end if
        }
        jsonArray.put(row);
      } while (c.moveToNext());
    }
    return jsonArray.toString();
  }
}
