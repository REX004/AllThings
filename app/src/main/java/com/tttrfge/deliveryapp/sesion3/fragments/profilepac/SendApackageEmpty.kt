package com.tttrfge.deliveryapp.sesion3.fragments.profilepac

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.tttrfge.deliveryapp.R
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.result.PostgrestResult
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
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive

class SendApackageEmpty : Fragment() {

    private lateinit var supabase: SupabaseClient
    private lateinit var catsTextView: TextView

    private fun fetchDataFromCatsTable() {
        lifecycleScope.launch {
            try {
                val response = supabase.from("cats")
                    .select()
                    .executeAsList(Cat::class.java)

                // Обработка успешного ответа
                val catsList = response.body()
                // Ваши действия с полученными данными
                displayCats(catsList)
            } catch (error: Exception) {
                // Обработка ошибки запроса
                println("Error fetching data from 'cats' table: ${error.message}")
            }
        }
    }

    private fun addCatToTable(cat: Cat) {
        lifecycleScope.launch {
            try {
                supabase.from("cats")
                    .insert(listOf(cat))
                    .execute()
                // Ваши действия после успешного добавления
            } catch (error: Exception) {
                // Обработка ошибки запроса на добавление
                println("Error adding cat to 'cats' table: ${error.message}")
            }
        }
    }

    private fun displayCats(catsList: List<Cat>) {
        val catNames = catsList.map { it.name }
        val catNamesString = catNames.joinToString("\n")
        catsTextView.text = catNamesString
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.send_apackage_empty, container, false)
        catsTextView = view.findViewById(R.id.catsTextView)
        return view
    }

    @OptIn(SupabaseInternal::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        // Пример использования реального времени для отслеживания изменений в таблице "cats"
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
                        val cat = Cat.fromJson(it.record)
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

        // Здесь вызывайте функцию для извлечения данных
        fetchDataFromCatsTable()

        // Пример добавления нового кота в таблицу
        val newCat = Cat(name = "Whiskers", lastname = "Catson")
        addCatToTable(newCat)
    }
}

private fun PostgrestResult.execute() {
    TODO("Not yet implemented")
}

private fun Any.body(): List<Cat> {
    return emptyList()
}

private fun <T> PostgrestResult.executeAsList(clazz: Class<T>): List<T> {
    return emptyList()
}

@Serializable
data class Cat(val id: String? = null, val name: String, val lastname: String) {
    companion object {
        fun fromJson(jsonObject: JsonObject): Cat {
            return Cat(
                id = jsonObject["id"]?.jsonPrimitive?.contentOrNull,
                name = jsonObject["name"]?.jsonPrimitive?.contentOrNull ?: "",
                lastname = jsonObject["lastname"]?.jsonPrimitive?.contentOrNull ?: ""
            )
        }
    }
}
