package com.example.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.example.fragment.adapters.ViewPagerAdapter
import com.example.fragment.fragments.MapFragment
import com.example.fragment.fragments.PhoneFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    var tabTitle = arrayOf("Phone", "Map")
    val manager = supportFragmentManager
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var text_current_lat_lon: TextView
    lateinit var locationRequest: com.google.android.gms.location.LocationRequest
    var current_lat = 0.0
    var current_lon = 0.0
    var PERMISSION_ID = 52

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text_current_lat_lon = findViewById(R.id.text_current_lat_lon)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        //var intent = Intent("ru.yandex.yandexnavi.action.BUILD_ROUTE_ON_MAP")
        //intent.setPackage("ru.yandex.yandexnavi")
        getLastLocation()
        //intent.putExtra("lat_to", 55.758192)
        //intent.putExtra("lon_to", 37.642817)



        var pager = findViewById<ViewPager2>(R.id.view_pager)
        var tl = findViewById<TabLayout>(R.id.tab_layout)
        pager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(tl, pager){
            tab, position -> tab.text = tabTitle[position]
        }.attach()

    }
    private fun getLastLocation(){
        if (checkPermission()){
            if (isLocationEnabled()){
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        getNewLocation()
                    } else {
                        current_lat = location.latitude
                        current_lon = location.longitude
                        Log.d("Mylog", current_lon.toString() + "lon")
                        text_current_lat_lon.text = current_lat.toString() + ' ' + current_lon.toString()
                        val t = text_current_lat_lon.text.toString()
                        val fragment = MapFragment()
                        Log.d("Mylog", t + "new")
                        val transaction = manager.beginTransaction()
                        fragment.arguments = bundleOf("t" to t)
                        transaction.replace(R.id.cont, fragment)
                        transaction.addToBackStack(null)
                        transaction.commit()
                        Log.d("Mylog", "new" + text_current_lat_lon.text.toString())

                    }
                }
            }else{
                Toast.makeText(this, "пожалуйста, включите геолокацию", Toast.LENGTH_SHORT).show()
                getLastLocation()
            }

        }else{
            requestPermission()
        }
    }

    private fun getNewLocation(){
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
    }
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location? = locationResult.lastLocation
            current_lat = mLastLocation!!.latitude
            current_lon = mLastLocation!!.longitude
            text_current_lat_lon.text = current_lat.toString() + ' ' + current_lon.toString()
            val fragment = MapFragment()
            val t = text_current_lat_lon.text.toString()
            Log.d("Mylog", t + "new")
            val transaction = manager.beginTransaction()
            fragment.arguments = bundleOf("t" to t)
            transaction.replace(R.id.cont, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
            Log.d("Mylog", "new" + text_current_lat_lon.text.toString())
        }
    }

    private fun checkPermission(): Boolean{
        if(ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID)
    }

    private fun isLocationEnabled(): Boolean{

        var locationManager : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID){
            if (grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("MyLog", "permittion granted")
            }

        }
    }
}