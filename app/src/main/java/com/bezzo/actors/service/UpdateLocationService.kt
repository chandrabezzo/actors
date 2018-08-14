package com.bezzo.actors.service

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.IBinder
import com.bezzo.actors.MvpApp
import com.bezzo.actors.data.session.SessionConstants
import com.bezzo.actors.data.session.SessionHelper
import com.bezzo.actors.di.component.DaggerServiceComponent
import com.bezzo.actors.util.AppLogger
import com.bezzo.actors.util.Gps
import com.google.android.gms.location.*
import javax.inject.Inject

/**
 * Created by bezzo on 07/03/18.
 */
class UpdateLocationService : Service(), LocationListener {

    @Inject
    lateinit var sessionHelper : SessionHelper

    lateinit var mLocationRequest: LocationRequest
    val UPDATE_INTERVAL = (10*1000).toLong() // 10 sec
    val FASTEST_INTERVAL = 2000.toLong()
    var currentLatitude = 0.toDouble()
    var currentLongitude = 0.toDouble()
    var lastLocation = Location("lastLocation")

    lateinit var mFusedLocation : FusedLocationProviderClient
    lateinit var mLocationCallback : LocationCallback

    companion object {
        fun start(context: Context){
            var starter = Intent(context, UpdateLocationService::class.java)
            context.startService(starter)
        }

        fun stop(context: Context){
            context.stopService(Intent(context, UpdateLocationService::class.java))
        }
    }

    override fun onCreate() {
        super.onCreate()

        var component = DaggerServiceComponent.builder()
                .applicationComponent((application as MvpApp).component)
                .build()
        component.inject(this)

        mFusedLocation = LocationServices.getFusedLocationProviderClient(this)
        mLocationRequest = LocationRequest.create()
        startLocationUpdates(mLocationRequest)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mFusedLocation.lastLocation.addOnSuccessListener {
                    if (it != null){
                        onLocationChanged(it)
                    }
                }
            }
        }
        else {
            mFusedLocation.lastLocation.addOnSuccessListener {
                if (it != null) {
                    onLocationChanged(it)
                }
            }
        }

        mLocationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations){
                    if (location != null){
                        onLocationChanged(location)
                    }
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallback, null)
            }
        }
        else {
            mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallback, null)
        }

        AppLogger.i("Update Location Service Started")

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        mFusedLocation.removeLocationUpdates(mLocationCallback)

        AppLogger.i("Update Location Service Stopped")
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onLocationChanged(location: Location) {
        //check mock location for fake GPS
        if (!Gps.isMockLocationOn(location)) {
            currentLatitude = location.latitude
            currentLongitude = location.longitude

            lastLocation.latitude = sessionHelper.getSession(SessionConstants.LATITUDE, 0.toDouble())!!
            lastLocation.longitude = sessionHelper.getSession(SessionConstants.LONGITUDE, 0.toDouble())!!

            var jarak = location.distanceTo(lastLocation)

            if (jarak >= 50){
                changeLocation()
            }
        }
    }

    fun changeLocation() {
        AppLogger.i("Lokasi $currentLatitude & $currentLongitude")

        // buat cek update location tetep jalan meski aplikasi ditutup
        updateLocation(currentLatitude.toString(), currentLongitude.toString())
    }

    fun updateLocation(latitude : String, longitude : String){
        var headers = HashMap<String, String>()
        headers[SessionConstants.TOKEN] = sessionHelper.getSession(SessionConstants.TOKEN, "")

        var params = HashMap<String, String>()
        params["latitude"] = latitude
        params["longitude"] = longitude

        // update location to service
    }

    fun showUpdated(){
        // untuk cek updatedLastLocation
//        Toast.makeText(this, "Lokasi $currentLatitude & $currentLongitude", Toast.LENGTH_SHORT).show()
        sessionHelper.addSession(SessionConstants.LATITUDE, currentLatitude)
        sessionHelper.addSession(SessionConstants.LONGITUDE, currentLongitude)
    }

    fun startLocationUpdates(locationRequest: LocationRequest) {
        // Create the location request to start receiving updates
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = UPDATE_INTERVAL
        locationRequest.fastestInterval = FASTEST_INTERVAL

        // Create LocationSettingsRequest object using location request
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)
        val locationSettingsRequest = builder.build()

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        val settingsClient = LocationServices.getSettingsClient(this)
        settingsClient.checkLocationSettings(locationSettingsRequest)
    }
}