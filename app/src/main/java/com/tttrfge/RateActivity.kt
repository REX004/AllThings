package com.tttrfge

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.SENSOR_DELAY_NORMAL
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tttrfge.deliveryapp.R

class RateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate)
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        var lastZ = 0;
        var lastX = 0;
        var lastY = 0;
        var rating = 0;
        sensorManager.registerListener(object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
//                println("Event: $event")
//                println("X: ${event?.values?.get(0)}")
//                println("Y: ${event?.values?.get(1)}")
//                if (lastZ != event?.values?.get(1)?.toInt()) {
//                    lastZ = event?.values?.get(1)?.toInt() ?: 0;
//                    println("Y: $lastY")
//                }
                if (lastX != event?.values?.get(0)?.toInt()) {
                    lastX = event?.values?.get(0)?.toInt() ?: 0;
                    println("X: $lastX")
                    if (lastX in -3..-2) {
                        if (rating < 5) {
                            rating += 1
                            printRating(rating)
                        }
                    }
                    if (lastX in 2..3) {
                        if (rating > 0) {
                            rating -= 1
                            printRating(rating)
                        }
                    }
                }
//                if (lastY != event?.values?.get(1)?.toInt()) {
//                    lastY = event?.values?.get(1)?.toInt() ?: 0;
//                    println("Z: $lastZ")
//                }

            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                println("Accuracy: $accuracy")
            }

        }, sensor, SENSOR_DELAY_NORMAL)
    }
}

fun printRating(rating: Int) {
    val rating = Array(rating) { '*' }
    val ratingStr = String(rating.toCharArray());
    Log.d("rating", "printRating: ${ratingStr}")
    println(rating)
}