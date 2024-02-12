package com.tttrfge.deliveryapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.widget.Button
import io.github.jan.supabase.gotrue.providers.builtin.OTP
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.OtpType
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


class MainActivity : AppCompatActivity() {
    private lateinit var selectedImage: ImageView
    private lateinit var supabase: SupabaseClient

    //todo  намерение для выбора изображения из галереи
    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                val file = getFile(this, imgUri!!);
                println(file)
                uploadFileToSupabase(supabase, file!!)
                selectedImage.setImageURI(imgUri)
            }
        }

    // todo Загрузка файла в storage
    fun uploadFileToSupabase(supabase: SupabaseClient, file: File) {
        val bucket = supabase.storage.from("images")
        val byteArray = file.readBytes();
        lifecycleScope.launch {
            bucket.upload("myIcon.png", byteArray, upsert = false)
        }

    }

    // todo функции для получения file из Uri
    @Throws(IOException::class)
    fun getFile(context: Context, uri: Uri): File? {
        val destinationFilename =
            File(context.filesDir.path + File.separatorChar + queryName(context, uri))
        try {
            context.contentResolver.openInputStream(uri).use { ins ->
                createFileFromStream(
                    ins!!,
                    destinationFilename
                )
            }
        } catch (ex: Exception) {
            Log.e("Save File", ex.message!!)
            ex.printStackTrace()
        }
        return destinationFilename
    }

    fun createFileFromStream(ins: InputStream, destination: File?) {
        try {
            FileOutputStream(destination).use { os ->
                val buffer = ByteArray(4096)
                var length: Int
                while (ins.read(buffer).also { length = it } > 0) {
                    os.write(buffer, 0, length)
                }
                os.flush()
            }
        } catch (ex: Exception) {
            Log.e("Save File", ex.message!!)
            ex.printStackTrace()
        }
    }

    private fun queryName(context: Context, uri: Uri): String {
        val returnCursor = context.contentResolver.query(uri, null, null, null, null)!!
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        returnCursor.close()
        return name
    }

    @OptIn(SupabaseExperimental::class, SupabaseInternal::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectedImage = findViewById(R.id.image)
        // todo открытие галереи
        findViewById<Button>(R.id.button).setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            changeImage.launch(pickImg)
        }
        // todo инициализация supabase
        val supabaseUrl = "https://taiveobdxmijvagwftuv.supabase.co"
        val supabaseKey =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRhaXZlb2JkeG1panZhZ3dmdHV2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDY2OTEzOTMsImV4cCI6MjAyMjI2NzM5M30.CtF3QuD7OMEX1VgbBq4pqXbOMkrUt2jIxz6yOQ-6yt0"

        // todo создание supabase клиента
        supabase = createSupabaseClient(supabaseUrl = supabaseUrl, supabaseKey = supabaseKey) {
            install(Auth)
            install(Realtime)
            install(Storage)
            install(Postgrest)
            httpConfig {
                Logging { this.level = LogLevel.BODY }
            }
        }

        // todo realtime
        val channel = supabase.channel("channelId") {}

        val changeFlow = channel.postgresChangeFlow<PostgresAction>(schema = "public") {
            table = "cats"
        }
        lifecycleScope.launch {
            changeFlow.collect {

                when (it) {
                    is PostgresAction.Delete -> println("Deleted: ${it.oldRecord}")
                    is PostgresAction.Insert -> {
                        println("Inserted: ${it.record}")
                        val cat = Cat.fromJson(it.record);
                        println(cat)
                    }

                    is PostgresAction.Select -> println("Selected: ${it.record}")
                    is PostgresAction.Update -> println("Updated: ${it.oldRecord} with ${it.record}")
                }
            }
        }

        lifecycleScope.launch {
            channel.subscribe()
        }

        //todo авторизация/регистрация
        lifecycleScope.launch {
//            val user = supabase.auth.signUpWith(Email) {
//                email = "puknitov@gmail.com"
//                password = "example-password"
//            }
            val user = supabase.auth.signInWith(Email) {
                email = "puknitov@gmail.com"
                password = "example-password"
            }
            supabase.auth.signInWith(OTP)
//
            supabase.auth.resendEmail(OtpType.Email.EMAIL_CHANGE, "puknitov@gmail.com")
//
//            supabase.auth.resetPasswordForEmail("puknitov@gmail.com")
//            supabase.auth.reauthenticate()
//            supabase.auth.signInWith(OTP) {
//                email = "puknitov@gmail.com"
//            }
//            supabase.auth.signInWith(Google, redirectUrl = "https://taiveobdxmijvagwftuv.supabase.co/auth/v1/callback");
        }

        //todo вставка в бд
        lifecycleScope.launch {
            val cat = Cat(name = "Hello", lastname = "World")
            supabase.from("cats").insert(cat);
        }

    }
}


@Serializable
data class Cat(val id: String? = null, val name: String, val lastname: String) {
    companion object {
        fun fromJson(jsonObject: JsonObject): Cat {
            return Cat(
                id = jsonObject["id"].toString(),
                name = jsonObject["name"].toString(),
                lastname = jsonObject["lastname"].toString()
            )


        }
    }

}