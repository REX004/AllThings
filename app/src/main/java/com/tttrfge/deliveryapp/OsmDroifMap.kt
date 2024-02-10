package com.tttrfge.deliveryapp

import android.R
import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView

class OsmDroifMap : Activity() {
    var map: MapView? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//
//        val ctx = applicationContext
//        Configuration.get().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
//
//        setContentView(R.layout.osm)
//        map = findViewById<View>(R.id.map) as MapView
//        map!!.setTileSource(TileSourceFactory.MAPNIK)
    }

    public override fun onResume() {
        super.onResume()

        map!!.onResume()
    }

    public override fun onPause() {
        super.onPause()
        map!!.onPause()
    }
}