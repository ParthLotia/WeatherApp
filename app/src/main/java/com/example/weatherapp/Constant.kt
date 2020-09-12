package com.example.weatherapp

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Constant {


    fun getCon(context: Context): Api {


        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(30000, TimeUnit.SECONDS)
            .readTimeout(3000, TimeUnit.SECONDS)
            .writeTimeout(3000, TimeUnit.SECONDS)
            .build()


        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder().baseUrl(context.getString(R.string.url))
            .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).build()
        return retrofit.create(Api::class.java)


    }

    object WeatherDatabaseTable {
        const val DB_NAME = "weather"
        const val TABLE_NAME = "weathertable"
        const val DB_VERSION = +1
        const val DROP_QUERY = "DROP TABLE IF EXISTS $TABLE_NAME"
        const val GET_PRODUCTS_QUERY = "SELECT * FROM $TABLE_NAME"
        const val KEY_ID = "keyid"
        const val MAIN = "main"
        const val DESCRIPTION = "description"
        const val ICON = "icon"
        const val TEMP = "temp"
        const val HUMIDITY = "humidity"
        const val TEMP_MIN = "temp_min"
        const val TEMP_MAX = "temp_max"
        const val SPEED = "speed"
        const val COUNTRY = "country"
        const val SUNRISE = "sunrise"
        const val SUNSET = "sunset"
        const val NAME = "name"
        const val CREATE_TABLE_QUERY =
            ("CREATE TABLE " + TABLE_NAME + "("
                    + KEY_ID + " INTEGER PRIMARY KEY,"
                    + MAIN + " TEXT,"
                    + DESCRIPTION + " TEXT,"
                    + ICON + " TEXT,"
                    + TEMP + " TEXT,"
                    + HUMIDITY + " TEXT,"
                    + TEMP_MIN + " TEXT,"
                    + TEMP_MAX + " TEXT,"
                    + SPEED + " TEXT,"
                    + COUNTRY + " TEXT,"
                    + SUNRISE + " TEXT,"
                    + SUNSET + " TEXT,"
                    + NAME + " TEXT" + ")")
    }
}
