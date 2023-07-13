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
        var pager = findViewById<ViewPager2>(R.id.view_pager)
        var tl = findViewById<TabLayout>(R.id.tab_layout)
        pager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(tl, pager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()

    }
}