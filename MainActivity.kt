package com.databasekotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var db: DatabaseHelper ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = DatabaseHelper(this, "toko")
        println("INSERT DB")
        var columns = "kdbrg, nama, harga, expired"
        var values = "'SNK123', 'Potato Chips', 500, '2022-12-12'"
        var ins = db!!.insert("barang", columns, values)
        if(ins >= 0){
            println("Success SNK123")
        }else{
            println("Failed SNK123")
        }

        values = "'USB123', 'USB Super', 1000.55, '0000-00-00'"
        ins = db!!.insert("barang", columns, values)
        if(ins >= 0){
            println("Success USB123")
        }else{
            println("Failed USB123")
        }


        println("GET DB")
        println("NUM_ROWS: " +db!!.select("* FROM barang",true))
        println(db!!.select("* FROM barang"))

        var del = db!!.delete("barang", "kdbrg='USB123'")
        if(del){
            println("Delete Success USB123")
        }else{
            println("Delete Failed USB123")
        }

        println("GET DB")
        println("NUM_ROWS: " +db!!.select("* FROM barang",true))
        println(db!!.select("* FROM barang"))

        var upd = db!!.update("barang", "nama='Potato Cheese'", "kdbrg='SNK123'")
        if(upd){
            println("Update Success SNK123")
        }else{
            println("Update Failed SNK123")
        }

        println("GET DB")
        println("NUM_ROWS: " +db!!.select("* FROM barang",true))
        println(db!!.select("* FROM barang"))
    }
}
