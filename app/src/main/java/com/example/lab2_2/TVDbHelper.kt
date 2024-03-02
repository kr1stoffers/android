package com.example.lab2_2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TVDbHelper (context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object{
        private val DATABASE_NAME = "TVS"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "TVs_table"
        val ID_COL = "id"
        val NAME_COl = "name"
        val TIME_COL = "time"
        val CHANNEL_COL = "channel"
        val FIO_COL = "fio"
        val PICTURE_COL = "picture"
    }
    override fun onCreate(db: SQLiteDatabase) {
        val query = (
                "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY autoincrement, "
                + NAME_COl + " TEXT,"
                + TIME_COL + " TEXT,"
                + CHANNEL_COL + " TEXT,"
                + FIO_COL + " TEXT,"
                + PICTURE_COL + " TEXT)"
                )
        db.execSQL(query)
    }
    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }
    fun getCurcor(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
    }
    fun isEmpty(): Boolean {
        val cursor = getCurcor()
        return !cursor!!.moveToFirst()
    }

    fun printDB(){
        val cursor = getCurcor()
        if (!isEmpty()) {
            cursor!!.moveToFirst()
            val nameColIndex = cursor.getColumnIndex(NAME_COl)
            val timeColIndex = cursor.getColumnIndex(TIME_COL)
            val channelColIndex = cursor.getColumnIndex(CHANNEL_COL)
            val fioColIndex = cursor.getColumnIndex(FIO_COL)
            val pictureColIndex = cursor.getColumnIndex(PICTURE_COL)
            do {
                print("${cursor.getString(nameColIndex)} ")
                print("${cursor.getString(timeColIndex)} ")
                print("${cursor.getString(channelColIndex)} ")
                print("${cursor.getString(fioColIndex)} ")
                println("${cursor.getString(pictureColIndex)} ")
            } while (cursor.moveToNext())
        } else println("DB is empty")
    }
    fun addArrayToDB(tv: ArrayList<TV>){
        tv.forEach {
            addTV(it)
        }
    }
    fun addTV(tv: TV){
        val values = ContentValues()

        values.put(NAME_COl, tv.name)
        values.put(TIME_COL, tv.time)
        values.put(CHANNEL_COL, tv.channel)
        values.put(FIO_COL, tv.fio)
        values.put(PICTURE_COL, tv.picture)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun removeTV(name: String){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "name=?", Array(1){name})
        db.close()
    }

    fun del(){
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }
    fun changeImgForTV(name: String, img: String){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(PICTURE_COL, img)
        println(values)

        db.update(TABLE_NAME, values, "$NAME_COl = '$name'", null)
        db.close()
    }
    fun getTVArray(): ArrayList<TV>{

        var TVsArray = ArrayList<TV>()
        val cursor = getCurcor()

        if (!isEmpty()) {
            cursor!!.moveToFirst()
            val nameColIndex = cursor.getColumnIndex(NAME_COl)
            val timeColIndex = cursor.getColumnIndex(TIME_COL)
            val channelColIndex = cursor.getColumnIndex(CHANNEL_COL)
            val fioColIndex = cursor.getColumnIndex(FIO_COL)
            val pictureColIndex = cursor.getColumnIndex(PICTURE_COL)
            do {
                val name = cursor.getString(nameColIndex)
                val time = cursor.getString(timeColIndex)
                val channel = cursor.getString(channelColIndex)
                val fio = cursor.getString(fioColIndex)
                val picture = cursor.getString(pictureColIndex)
                TVsArray.add(TV(name, time, channel, fio, picture))
            } while (cursor.moveToNext())
        } else println("DB is empty")
        return TVsArray
    }
}