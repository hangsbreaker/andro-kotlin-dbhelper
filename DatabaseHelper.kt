package com.databasekotlin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.json.JSONArray
import org.json.JSONObject

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object{
        private val DB_NAME = "toko"
        private val DB_VERSION = 1
        private val TABLE_BARANG = "barang"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE_BARANG (kdbrg TEXT PRIMARY KEY, nama TEXT, harga NUMERIC, expired DATE)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val query = "DROP TABLE IF EXISTS $TABLE_BARANG"
        db?.execSQL(query)
        onCreate(db)
    }

    fun insert(table:String, columns:String, values:String): Boolean{
        val sql = "INSERT INTO $table ($columns) VALUES ($values)"
        try {
            val db = this.writableDatabase
            db?.execSQL(sql)
        }catch (e:java.lang.Exception){
            return false
        }
        return true
    }

    fun update(table:String, set:String, where:String): Boolean{
        val sql = "UPDATE $table SET $set WHERE $where"
        try {
            val db = this.writableDatabase
            db?.execSQL(sql)
        }catch (e:java.lang.Exception){
            return false
        }
        return true
    }

    fun delete(table:String, where:String): Boolean{
        val sql = "DELETE FROM $table WHERE $where"
        try {
            val db = this.writableDatabase
            db?.execSQL(sql)
        }catch (e:java.lang.Exception){
            return false
        }
        return true
    }

    fun select(sql: String, num_rows:Boolean=false): String {
        val db = this.readableDatabase
        val res = db.rawQuery("SELECT $sql", null)

        if(!num_rows) {
            val json = JSONArray()
            val num_cols: Int = res.getColumnCount()
            res?.moveToFirst()
            while (!res.isAfterLast()) {
                val row = JSONObject()

                for (i in 0..(num_cols - 1)) {
                    if (res.getColumnName(i) != null) {
                        try {
                            if (res.getString(i) != null) {
                                row.put(res.getColumnName(i), res.getString(i))
                            } else {
                                row.put(res.getColumnName(i), "")
                            }

                        } catch (e: Exception) {
                            e.printStackTrace();
                        }
                    }// end if
                }// end for
                json.put(row)
                res.moveToNext()
            }
            res.close()
            return json.toString()
        }else{
            // println("Num Rows: "+res.count)
            // res.count
            return res.count.toString()
        }

    }

}
