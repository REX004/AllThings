package com.tttrfge.deliveryapp.sesion3.fragments.profilepac


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tttrfge.deliveryapp.CatListAdapter
import com.tttrfge.deliveryapp.R
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.storage.Storage
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import io.github.jan.supabase.postgrest.PostgrestDsl
import io.github.jan.supabase.postgrest.query.Columns


class SendApackageEmpty : AppCompatActivity() {

    private lateinit var supabase: SupabaseClient
    private lateinit var name_edit: EditText
    private lateinit var last_name_edit: EditText
    private lateinit var catListAdapter: CatListAdapter


    @OptIn(SupabaseInternal::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.send_apackage_empty)

        name_edit = findViewById(R.id.name)
        last_name_edit = findViewById(R.id.last_name)

        val entered_name = name_edit.text.toString()
        var entered_last_name = last_name_edit.text.toString()
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

            val user = supabase.auth.signInWith(Email) {
                email = "puknitov@gmail.com"
                password = "example-password"
            }

        }


        val button = findViewById<Button>(R.id.clicker)

        button.setOnClickListener {

            val entered_name = name_edit.text.toString()
            val entered_last_name = last_name_edit.text.toString()

            //todo вставка в бд
            lifecycleScope.launch {
                val cat =
                    com.tttrfge.deliveryapp.Cat(name = entered_name, lastname = entered_last_name)
                supabase.from("cats").insert(cat);
                fetchDataFromDatabase()

            }

        }




    }

    private suspend fun fetchDataFromDatabase() {
        try {
            val columns = Columns.raw("""
            id,
            name,
            lastname,
            phoneNumber
        """.trimIndent())

            val catList = supabase.from("cats")
                .select(columns = columns)
                .decodeAs<Cat>()

            val textViewName = findViewById<TextView>(R.id.catsTextView)
            val textViewLastName = findViewById<TextView>(R.id.textView2)

            if (catList.isNotEmpty()) {
                val cat = catList[0]

                textViewName.text = cat.name
                textViewLastName.text = cat.lastname
            }
        } catch (e: Exception) {

            Log.e("FetchData", "Error fetching data: ${e.message}")
        }
    }



}


    @Serializable
    data class Cat(val id: String? = null, val name: String, val lastname: String) :
        MutableList<com.tttrfge.deliveryapp.Cat> {
        companion object {
            fun fromJson(jsonObject: JsonObject): Cat {
                return Cat(
                    id = jsonObject["id"].toString(),
                    name = jsonObject["name"].toString(),
                    lastname = jsonObject["lastname"].toString()
                )


            }
        }

        override val size: Int
            get() = TODO("Not yet implemented")

        override fun clear() {
            TODO("Not yet implemented")
        }

        override fun addAll(elements: Collection<com.tttrfge.deliveryapp.Cat>): Boolean {
            TODO("Not yet implemented")
        }

        override fun addAll(
            index: Int,
            elements: Collection<com.tttrfge.deliveryapp.Cat>
        ): Boolean {
            TODO("Not yet implemented")
        }

        override fun add(index: Int, element: com.tttrfge.deliveryapp.Cat) {
            TODO("Not yet implemented")
        }

        override fun add(element: com.tttrfge.deliveryapp.Cat): Boolean {
            TODO("Not yet implemented")
        }

        override fun get(index: Int): com.tttrfge.deliveryapp.Cat {
            TODO("Not yet implemented")
        }

        override fun isEmpty(): Boolean {
            TODO("Not yet implemented")
        }

        override fun iterator(): MutableIterator<com.tttrfge.deliveryapp.Cat> {
            TODO("Not yet implemented")
        }

        override fun listIterator(): MutableListIterator<com.tttrfge.deliveryapp.Cat> {
            TODO("Not yet implemented")
        }

        override fun listIterator(index: Int): MutableListIterator<com.tttrfge.deliveryapp.Cat> {
            TODO("Not yet implemented")
        }

        override fun removeAt(index: Int): com.tttrfge.deliveryapp.Cat {
            TODO("Not yet implemented")
        }

        override fun subList(
            fromIndex: Int,
            toIndex: Int
        ): MutableList<com.tttrfge.deliveryapp.Cat> {
            TODO("Not yet implemented")
        }

        override fun set(
            index: Int,
            element: com.tttrfge.deliveryapp.Cat
        ): com.tttrfge.deliveryapp.Cat {
            TODO("Not yet implemented")
        }

        override fun retainAll(elements: Collection<com.tttrfge.deliveryapp.Cat>): Boolean {
            TODO("Not yet implemented")
        }

        override fun removeAll(elements: Collection<com.tttrfge.deliveryapp.Cat>): Boolean {
            TODO("Not yet implemented")
        }

        override fun remove(element: com.tttrfge.deliveryapp.Cat): Boolean {
            TODO("Not yet implemented")
        }

        override fun lastIndexOf(element: com.tttrfge.deliveryapp.Cat): Int {
            TODO("Not yet implemented")
        }

        override fun indexOf(element: com.tttrfge.deliveryapp.Cat): Int {
            TODO("Not yet implemented")
        }

        override fun containsAll(elements: Collection<com.tttrfge.deliveryapp.Cat>): Boolean {
            TODO("Not yet implemented")
        }

        override fun contains(element: com.tttrfge.deliveryapp.Cat): Boolean {
            TODO("Not yet implemented")
        }
    }