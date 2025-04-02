package com.example.mysqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "ContactsDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE contacts (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS contacts")
        onCreate(db)
    }

    fun addContact(name: String, phone: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("phone", phone)
        }
        val result = db.insert("contacts", null, values)
        return result != -1L
    }

    fun updateContact(name: String, phone: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("phone", phone)
        }
        val result = db.update("contacts", values, "name=?", arrayOf(name))
        return result > 0
    }

    fun deleteContact(name: String): Boolean {
        val db = this.writableDatabase
        val result = db.delete("contacts", "name=?", arrayOf(name))
        return result > 0
    }

    fun getAllContacts(): String {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM contacts", null)
        val result = StringBuilder()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val phone = cursor.getString(2)
            result.append("ID: $id - $name: $phone\n")
        }
        cursor.close()
        return result.toString()
    }
}
