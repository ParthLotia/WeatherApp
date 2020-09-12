package com.example.weatherapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayout
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    com.google.android.gms.location.LocationListener {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var context: Context
    private val MY_PERMISSIONS_REQUEST_LOCATION = 99
    private var lat: Double = 0.0
    private var lng: Double = 0.0
    private lateinit var txt_city_name: TextView
    private lateinit var img_refresh: ImageView
    private lateinit var image1: ImageView
    private lateinit var tabLayout: TabLayout
    private lateinit var recycler_today: RecyclerView
    private lateinit var txt_climate: TextView
    private lateinit var txt_wind: TextView
    private lateinit var txt_pressure: TextView
    private lateinit var txt_humadity: TextView

    private lateinit var txt_tomorrow: TextView


    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLastLocation: Location
    private var weatherList: ArrayList<WeatherModel>? = null
    private var mWeatherDatabase: WeatherDatabase? = null

    lateinit var weatherAdapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        permissiondata()
        click()
    }

    private fun click() {
        img_refresh.setOnClickListener {

            apicallToday()

        }
    }

    private fun init() {
        context = this
        txt_city_name = findViewById(R.id.txt_city_name)
        img_refresh = findViewById(R.id.img_refresh)
        image1 = findViewById(R.id.image1)
        tabLayout = findViewById(R.id.tabLayout)
        recycler_today = findViewById(R.id.recycler_today)
        txt_climate = findViewById(R.id.txt_climate)
        txt_wind = findViewById(R.id.txt_wind)
        txt_pressure = findViewById(R.id.txt_pressure)
        txt_humadity = findViewById(R.id.txt_humadity)
        txt_tomorrow = findViewById(R.id.txt_tomorrow)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        weatherList = ArrayList()
        mWeatherDatabase = WeatherDatabase(this)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {

                    recycler_today.visibility = View.VISIBLE
                    txt_tomorrow.visibility = View.GONE
                }
                if (tab.position == 1) {
                    recycler_today.visibility = View.GONE
                    txt_tomorrow.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }


    private fun apicallToday() {

        val api = Constant().getCon(this)
        api.getWeather(
            lat,
            lng,
            "metric", "2c50325f427689340a03ff16215d8fc4"
        ).enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                Log.e("response", "" + response.body())
                if (response.isSuccessful) {
                    if (response.body() != null) {


                        var weather = response.body()!!.get("weather").asJsonArray
                        var mainObject = response.body()!!.get("main").asJsonObject
                        var temp = mainObject.get("temp").asString
                        var feels_like = mainObject.get("feels_like").asString
                        var pressure = mainObject.get("pressure").asString
                        var temp_min = mainObject.get("temp_min").asString
                        var temp_max = mainObject.get("temp_max").asString
                        var humidity = mainObject.get("humidity").asString
                        var sea_level = mainObject.get("sea_level").asString
                        var grnd_level = mainObject.get("grnd_level").asString

                        var sys = response.body()!!.get("sys").asJsonObject
                        var country = sys.get("country").asString
                        var sunrise = sys.get("sunrise").asString
                        var sunset = sys.get("sunset").asString

                        var windObject = response.body()!!.get("wind").asJsonObject
                        var speed = windObject.get("speed").asString

                        var name = response.body()!!.get("name").asString
                        weatherList!!.clear()
                        for (i in 0 until weather.size()) {

                            var weatherJsonObject = weather.get(i).asJsonObject
                            var main = weatherJsonObject.get("main").asString

                            if (mWeatherDatabase?.TableNotEmpty()!!) {

                                mWeatherDatabase!!.updateValues(
                                    "1", main, weatherJsonObject.get("description").asString,
                                    weatherJsonObject.get("icon").asString,
                                    temp,
                                    humidity,
                                    temp_min,
                                    temp_max,
                                    speed,
                                    country,
                                    sunrise,
                                    sunset,
                                    name

                                )

                            } else {

                                mWeatherDatabase!!.addDataInDB(

                                    main,
                                    weatherJsonObject.get("description").asString,
                                    weatherJsonObject.get("icon").asString,
                                    temp,
                                    humidity,
                                    temp_min,
                                    temp_max,
                                    speed,
                                    country,
                                    sunrise,
                                    sunset,
                                    name


                                )
                            }

                            var icon = weatherJsonObject.get("icon").asString

                            when (weatherJsonObject.get("icon").asString) {
                                "01d" -> image1.setImageResource(R.drawable.sunny)
                                "02d" -> image1.setImageResource(R.drawable.cloud)
                                "03d" -> image1.setImageResource(R.drawable.cloud)
                                "04d" -> image1.setImageResource(R.drawable.cloud)
                                "04n" -> image1.setImageResource(R.drawable.cloud)
                                "10d" -> image1.setImageResource(R.drawable.rain)
                                "11d" -> image1.setImageResource(R.drawable.storm)
                                "13d" -> image1.setImageResource(R.drawable.snowflake)
                                "01n" -> image1.setImageResource(R.drawable.cloud)
                                "02n" -> image1.setImageResource(R.drawable.cloud)
                                "03n" -> image1.setImageResource(R.drawable.cloud)
                                "10n" -> image1.setImageResource(R.drawable.cloud)
                                "11n" -> image1.setImageResource(R.drawable.rain)
                                "13n" -> image1.setImageResource(R.drawable.snowflake)
                            }




                            txt_climate.text = main
                            txt_humadity.text = humidity + " per cent"
                            txt_pressure.text = pressure
                            txt_wind.text = speed


                            var weatherModel= WeatherModel(

                                main,
                                humidity,
                                pressure,
                                speed,
                                icon

                            )

                            weatherList?.add(weatherModel)


                            weatherAdapter = WeatherAdapter(
                                this@MainActivity,
                                weatherList
                            )
                            recycler_today.adapter = weatherAdapter
                            recycler_today.layoutManager =
                                LinearLayoutManager(context)

                            weatherAdapter.notifyDataSetChanged()

                        }


                    }
                }
            }

        })

        apicallContinue()

    }

    private fun apicallContinue() {

        val ha = Handler()
        ha.postDelayed(object : Runnable {
            override fun run() {
                apicallToday()
                ha.postDelayed(this, 900000)
            }
        }, 90000)
    }

    private fun permissiondata() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                buildGoogleApiClient()
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
        } else {
            buildGoogleApiClient()
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                if (location != null) {

                    lat = location.latitude
                    lng = location.longitude
                    getAddress(lat, lng)
                }
            }
    }

    private fun getAddress(lat: Double, lng: Double) {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses: List<Address> = geocoder.getFromLocation(lat, lng, 1)
            val obj: Address = addresses[0]
            var add: String = """
                ${obj.getLocality()}
                """.trimIndent()

            var add1: String = """
                ${obj.countryName}
                """.trimIndent()
            txt_city_name.text = add1 + " : " + add

        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        apicallToday()
    }

    private fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(context)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        mGoogleApiClient.connect()
    }

    override fun onConnected(p0: Bundle?) {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 1000
        mLocationRequest.fastestInterval = 1000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                mLocationRequest,
                this
            )
        }
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onLocationChanged(p0: Location?) {
        mLastLocation = p0!!
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {


                        buildGoogleApiClient()

                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location: Location? ->
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {

                                    lat = location.latitude
                                    lng = location.longitude
                                    getAddress(lat, lng)
                                }
                            }
                    }

                } else {
                    Toast.makeText(context, "permission denied", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }
}