package com.tttrfge.deliveryapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tttrfge.deliveryapp.databinding.IconForegroundBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.DrivingOptions
import com.yandex.mapkit.directions.driving.DrivingRoute
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.directions.driving.VehicleOptions
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider
import java.io.InputStream


class YandexMapkitActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_yandex_mapkit)
        mapView = findViewById(R.id.mapView);
        var text = assets.open("customizations.json").bufferedReader().readText()
        println(text);
        mapView.map.setMapStyle(text);

        showZone(Point(55.80163679298551, 49.17724461823924), 9700.0)

        val placemark: PlacemarkMapObject = mapView.map.mapObjects.addPlacemark()
        placemark.geometry = Point(55.80163679298551, 49.17724461823924)
        val view = IconForegroundBinding.inflate(layoutInflater);
        placemark.setView(ViewProvider(view.root), IconStyle().apply { scale = 0.2f })

        val placemark2: PlacemarkMapObject = mapView.map.mapObjects.addPlacemark()
        placemark2.geometry = Point(55.78799698131486, 49.117998989816556)
        val view2 = IconForegroundBinding.inflate(layoutInflater);
        placemark2.setView(ViewProvider(view2.root), IconStyle().apply { scale = 0.2f })

        val drivingRouter = DirectionsFactory.getInstance().createDrivingRouter()
        val drivingOptions = DrivingOptions().apply {
            routesCount = 1
        }
        val vehicleOptions = VehicleOptions()
        val points = buildList {
            add(RequestPoint(placemark.geometry, RequestPointType.WAYPOINT, null, null))
            add(RequestPoint(placemark2.geometry, RequestPointType.WAYPOINT, null, null))
        }


        val drivingRouteListener = object : DrivingSession.DrivingRouteListener {
            override fun onDrivingRoutes(drivingRoutes: MutableList<DrivingRoute>) {
                println(drivingRoutes)
                println(drivingRoutes.size)
                drivingRoutes.forEach {
                    mapView.map.mapObjects.addPolyline(it.geometry)
                }
            }

            override fun onDrivingRoutesError(p0: com.yandex.runtime.Error) {
                TODO("Not yet implemented")
            }

        }


        val drivingSession = drivingRouter.requestRoutes(
            points,
            drivingOptions,
            vehicleOptions,
            drivingRouteListener
        )

        setMapStyle()
    }
    private fun showZone(centerPoint: Point, radius: Double) {
        mapView.map.move(
            com.yandex.mapkit.map.CameraPosition(centerPoint, 20.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0.0f),
            null
        )
    }

    fun setMapStyle(){
        val style = "InputStream"
        mapView.map.setMapStyle(style)
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}
