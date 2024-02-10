plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // TODO Плагин для сериализации supabase
    kotlin("plugin.serialization") version "1.9.22"
}

android {
    namespace = "com.tttrfge.deliveryapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tttrfge.deliveryapp"
        minSdk = 30
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.annotation:annotation:1.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.0-beta01")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.0-beta01")
    implementation("com.android.volley:volley-cronet:1.2.1")
    // Dynamic Feature Module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.3.0-beta01")

    // Testing Navigation
    implementation ("androidx.room:room-runtime:2.3.0")
    annotationProcessor ("androidx.room:room-compiler:2.3.0")
    implementation ("androidx.webkit:webkit:1.4.0")
    implementation("androidx.core:core-ktx:1.9.0")

    implementation ("com.google.android.gms:play-services-maps:17.0.1")


    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.3.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    // todo необходимые библиотеки
    implementation(platform("io.github.jan-tennert.supabase:bom:2.1.0"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:gotrue-kt")
    implementation("io.github.jan-tennert.supabase:storage-kt")
    implementation("io.github.jan-tennert.supabase:realtime-kt")
    implementation("io.ktor:ktor-client-okhttp:2.3.7")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation ("com.yandex.android:maps.mobile:4.4.0-full")
    implementation("io.ktor:ktor-client-logging:2.3.8")

        //OSM Droid
    implementation ("org.osmdroid:osmdroid-android:6.1.18")

    implementation("androidx.biometric:biometric:1.2.0-alpha03")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.5.21")


}