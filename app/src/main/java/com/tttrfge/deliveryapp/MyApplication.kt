package com.tttrfge.deliveryapp

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.auth


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("ed16e156-fce4-42d8-8011-259338dc4d28")
    }
}
