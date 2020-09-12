package com.example.weatherapp

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.*

class WeatherDatabase(context: Context?) :
    SQLiteOpenHelper(
        context,
        Constant.WeatherDatabaseTable.DB_NAME,
        null,
        Constant.WeatherDatabaseTable.DB_VERSION
    ) {
    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(Constant.WeatherDatabaseTable.CREATE_TABLE_QUERY)
        } catch (ex: SQLException) {
            Log.d(TAG, ex.message)
        }
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL(Constant.WeatherDatabaseTable.DROP_QUERY)
        onCreate(db)
        db.close()
    }

    fun deleteProducts() {
        val db = this.writableDatabase
        db.delete(Constant.WeatherDatabaseTable.TABLE_NAME, null, null)
        db.close()
    }

    fun addDataInDB(
        s: String?,
        s0: String?,
        s1: String?,
        s2: String?,
        s3: String?,
        s4: String?,
        s5: String?,
        s6: String?,
        s7: String?,
        s8: String?,
        s9: String?,
        s10: String?
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Constant.WeatherDatabaseTable.MAIN, s)
        values.put(Constant.WeatherDatabaseTable.DESCRIPTION, s0)
        values.put(Constant.WeatherDatabaseTable.ICON, s1)
        values.put(Constant.WeatherDatabaseTable.TEMP, s2)
        values.put(Constant.WeatherDatabaseTable.HUMIDITY, s3)
        values.put(Constant.WeatherDatabaseTable.TEMP_MIN, s4)
        values.put(Constant.WeatherDatabaseTable.TEMP_MAX, s5)
        values.put(Constant.WeatherDatabaseTable.SPEED, s6)
        values.put(Constant.WeatherDatabaseTable.COUNTRY, s7)
        values.put(Constant.WeatherDatabaseTable.SUNRISE, s8)
        values.put(Constant.WeatherDatabaseTable.SUNSET, s9)
        values.put(Constant.WeatherDatabaseTable.NAME, s10)
        try {
            db.insert(Constant.WeatherDatabaseTable.TABLE_NAME, null, values)
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
        db.close()
    }

    val allData: List<Any>
        get() {
            val contactList: MutableList<DatabasePOJO> =
                ArrayList<DatabasePOJO>()
            val db = this.writableDatabase
            val cursor =
                db.rawQuery(Constant.WeatherDatabaseTable.GET_PRODUCTS_QUERY, null)
            if (cursor.moveToFirst()) {
                do {
                    val contact = DatabasePOJO()
                    contact.setMain(cursor.getString(cursor.getColumnIndex(Constant.WeatherDatabaseTable.MAIN)))
                    contact.setDescription(cursor.getString(cursor.getColumnIndex(Constant.WeatherDatabaseTable.DESCRIPTION)))
                    contact.setIcon(cursor.getString(cursor.getColumnIndex(Constant.WeatherDatabaseTable.ICON)))
                    contact.setTemp(cursor.getString(cursor.getColumnIndex(Constant.WeatherDatabaseTable.TEMP)))
                    contact.setHumidity(cursor.getString(cursor.getColumnIndex(Constant.WeatherDatabaseTable.HUMIDITY)))
                    contact.setTemp_min(cursor.getString(cursor.getColumnIndex(Constant.WeatherDatabaseTable.TEMP_MIN)))
                    contact.setTemp_max(cursor.getString(cursor.getColumnIndex(Constant.WeatherDatabaseTable.TEMP_MAX)))
                    contact.setSpeed(cursor.getString(cursor.getColumnIndex(Constant.WeatherDatabaseTable.SPEED)))
                    contact.setCountry(cursor.getString(cursor.getColumnIndex(Constant.WeatherDatabaseTable.COUNTRY)))
                    contact.setSunrise(cursor.getString(cursor.getColumnIndex(Constant.WeatherDatabaseTable.SUNRISE)))
                    contact.setSunset(cursor.getString(cursor.getColumnIndex(Constant.WeatherDatabaseTable.SUNSET)))
                    contact.setName(cursor.getString(cursor.getColumnIndex(Constant.WeatherDatabaseTable.NAME)))
                    contactList.add(contact)
                } while (cursor.moveToNext())
            }
            cursor.close()
            db.close()
            return contactList
        }

    fun updateValues(
        x: String,
        s: String?,
        s0: String?,
        s1: String?,
        s2: String?,
        s3: String?,
        s4: String?,
        s5: String?,
        s6: String?,
        s7: String?,
        s8: String?,
        s9: String?,
        s10: String?
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Constant.WeatherDatabaseTable.MAIN, s)
        values.put(Constant.WeatherDatabaseTable.DESCRIPTION, s0)
        values.put(Constant.WeatherDatabaseTable.ICON, s1)
        values.put(Constant.WeatherDatabaseTable.TEMP, s2)
        values.put(Constant.WeatherDatabaseTable.HUMIDITY, s3)
        values.put(Constant.WeatherDatabaseTable.TEMP_MIN, s4)
        values.put(Constant.WeatherDatabaseTable.TEMP_MAX, s5)
        values.put(Constant.WeatherDatabaseTable.SPEED, s6)
        values.put(Constant.WeatherDatabaseTable.COUNTRY, s7)
        values.put(Constant.WeatherDatabaseTable.SUNRISE, s8)
        values.put(Constant.WeatherDatabaseTable.SUNSET, s9)
        values.put(Constant.WeatherDatabaseTable.NAME, s10)

        // updating row
        db.update(
            Constant.WeatherDatabaseTable.TABLE_NAME,
            values,
            Constant.WeatherDatabaseTable.KEY_ID.toString() + " = ?",
            arrayOf(x)
        )
        db.close()
    }

    fun TableNotEmpty(): Boolean {
        val db = writableDatabase
        val mCursor =
            db.rawQuery("SELECT * FROM " + Constant.WeatherDatabaseTable.TABLE_NAME, null)
        val rowExists: Boolean
        if (mCursor.moveToFirst()) {
            // DO SOMETHING WITH CURSOR
            mCursor.close()
            rowExists = true
            db.close()
        } else {
            // I AM EMPTY
            mCursor.close()
            rowExists = false
            db.close()
        }
        db.close()
        return rowExists
    }

    companion object {
        private val TAG = WeatherDatabase::class.java.simpleName
        private val TABLE_NAME = WeatherDatabase::class.java.name
    }
}