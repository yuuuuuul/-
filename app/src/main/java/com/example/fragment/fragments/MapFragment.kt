package com.example.fragment.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.example.fragment.adapters.ViewPagerAdapter
import com.example.fragment.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.text.toFloatOrNull as toFloatOrNull1

@Suppress("UNREACHABLE_CODE")
class MapFragment : Fragment() {

    var tabTitle = arrayOf("Phone", "Map")
    lateinit var t: String
    var a: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        t = arguments?.getString("t").toString()
        Log.d("Mylog", "map" + t)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        var view: View? = null
        view = inflater.inflate(R.layout.fragment_map, container, false)


        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webViewSetup()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun webViewSetup(){
        var wb_webview = view?.findViewById<WebView>(R.id.wb_webView)
        var text_current_lat_lon: TextView? = view?.findViewById(R.id.text_current_lat_lon)
        Log.d("Mylog", t?.split(" ").toString())
        val c_la: Float? = t?.split(" ")?.get(0)?.toFloatOrNull1()
        val c_lo: Float? = t?.split(" ")?.get(1)?.toFloatOrNull1()
        Log.d("Mylog", "${c_la} ${c_lo}")
        wb_webview?.webViewClient = WebViewClient()
        wb_webview?.apply {
            loadUrl("https://yandex.ru/maps/?rtext=${c_la}, ${c_lo}~55.76009,37.648801&rtt=mt")

            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
        }
    }





}