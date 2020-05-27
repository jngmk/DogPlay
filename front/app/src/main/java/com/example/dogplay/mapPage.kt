package com.example.dogplay

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.maps.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.map_page.*
import kotlinx.android.synthetic.main.map_page.view.*


class mapPage : Fragment(),OnMapReadyCallback {
    companion object {
        fun newInstance() = mapPage()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.map_page,container,false)
        var mapFragment = childFragmentManager.findFragmentById(R.id.map)
        if (mapFragment == null){
            val fm = getFragmentManager()
            val ft = fm!!.beginTransaction()
            mapFragment = SupportMapFragment.newInstance()
            ft.replace(R.id.map, mapFragment).commit()
            mapFragment.getMapAsync(this)
        }

        return layout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        val sydney = LatLng(37.566, 126.978)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(14f))
    }
}

