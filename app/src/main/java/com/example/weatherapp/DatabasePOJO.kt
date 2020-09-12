package com.example.weatherapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DatabasePOJO : Serializable{
    @SerializedName("keyid")
    @Expose
    private var keyid = 0

    @SerializedName("main")
    @Expose
    private var main: String? = null

    @SerializedName("description")
    @Expose
    private var description: String? = null

    @SerializedName("icon")
    @Expose
    private var icon: String? = null

    @SerializedName("temp")
    @Expose
    private var temp: String? = null

    @SerializedName("humidity")
    @Expose
    private var humidity: String? = null

    @SerializedName("temp_min")
    @Expose
    private var temp_min: String? = null

    @SerializedName("temp_max")
    @Expose
    private var temp_max: String? = null

    @SerializedName("speed")
    @Expose
    private var speed: String? = null

    @SerializedName("country")
    @Expose
    private var country: String? = null

    @SerializedName("sunrise")
    @Expose
    private var sunrise: String? = null

    @SerializedName("sunset")
    @Expose
    private var sunset: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    private var isFromDatabaseNCO = false


    fun getKeyid(): Int {
        return keyid
    }

    fun setKeyid(keyid: Int) {
        this.keyid = keyid
    }

    fun getMain(): String? {
        return main
    }

    fun setMain(main: String?) {
        this.main = main
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getIcon(): String? {
        return icon
    }

    fun setIcon(icon: String?) {
        this.icon = icon
    }

    fun getTemp(): String? {
        return temp
    }

    fun setTemp(temp: String?) {
        this.temp = temp
    }

    fun getHumidity(): String? {
        return humidity
    }

    fun setHumidity(humidity: String?) {
        this.humidity = humidity
    }

    fun getTemp_min(): String? {
        return temp_min
    }

    fun setTemp_min(temp_min: String?) {
        this.temp_min = temp_min
    }

    fun getTemp_max(): String? {
        return temp_max
    }

    fun setTemp_max(temp_max: String?) {
        this.temp_max = temp_max
    }

    fun getSpeed(): String? {
        return speed
    }

    fun setSpeed(speed: String?) {
        this.speed = speed
    }

    fun getCountry(): String? {
        return country
    }

    fun setCountry(country: String?) {
        this.country = country
    }

    fun getSunrise(): String? {
        return sunrise
    }

    fun setSunrise(sunrise: String?) {
        this.sunrise = sunrise
    }

    fun getSunset(): String? {
        return sunset
    }

    fun setSunset(sunset: String?) {
        this.sunset = sunset
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun isFromDatabaseQuestions(): Boolean {
        return isFromDatabaseNCO
    }

    fun setFromDatabaseQuestions(fromDatabase: Boolean) {
        isFromDatabaseNCO = fromDatabase
    }
}