package com.szlangini.example.myapplication.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.View
import com.szlangini.example.myapplication.R
import android.Manifest
import android.content.pm.PackageManager
import android.location.*
import android.support.v4.content.ContextCompat
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_ticket.*
import java.util.*


class AddTicketActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ticket)
    }

    fun findLocationBtnClicked(view: View){
        Log.d("xyz", "Button clicked")
        getLocation()
    }

    fun addTicketBtnClicked(view: View){}

    fun transformCoordinatesToAddress(latitude : Double, longitude : Double) : String {
        val geocoder = Geocoder(this, Locale.GERMAN)
        val address = geocoder.getFromLocation(latitude, longitude, 1).get(0)
        return address.getAddressLine(0)
    }

    fun getLocation() {

        // Checking for Permissions to be granted from user
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_ACCESS_FINE_LOCATION)
            return
        }

        // Listener Object, that defines what happens, if a location is received or restricted
        var locationListener = object : LocationListener {
            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                Log.d("xyz", p0)
            }

            override fun onProviderEnabled(p0: String?) {
                Log.d("xyz", p0)
            }

            override fun onProviderDisabled(p0: String?) {
                Log.d("xyz", p0)
            }

            override fun onLocationChanged(location: Location?) {
                var latitude = location!!.latitude
                var longitude = location!!.longitude
                val address = transformCoordinatesToAddress(latitude, longitude)
                locationEditText.setText(address)
            }
        }

        // Object to handle locations.
        var locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        // For Low Battery consumption
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_COARSE
        criteria.powerRequirement = Criteria.POWER_LOW
        criteria.isAltitudeRequired = false
        criteria.isBearingRequired = false
        criteria.isSpeedRequired = false
        criteria.isCostAllowed = true
        criteria.horizontalAccuracy = Criteria.ACCURACY_HIGH
        criteria.verticalAccuracy = Criteria.ACCURACY_HIGH

        // Only request location once - for adress purpose.
        locationManager!!.requestSingleUpdate(criteria, locationListener, null)
        //locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)



    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_ACCESS_FINE_LOCATION) {
            when (grantResults[0]) {
                PackageManager.PERMISSION_GRANTED -> getLocation()
                PackageManager.PERMISSION_DENIED -> Toast.makeText(this, "Bitte erlauben Sie der App ihre Location einzusehen.", Toast.LENGTH_LONG).show()//Tell to user the need of grant permission
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 100
    }
}
