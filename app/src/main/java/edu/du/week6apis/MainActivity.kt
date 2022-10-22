package edu.du.week6apis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import edu.du.week6apis.model.MovieModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response;

class MainActivity : AppCompatActivity() {
    lateinit var service: MovieService
    lateinit var gson: Gson
    lateinit var requestText: TextView
    lateinit var responseText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/ottomathuss/Week6RestAPIs/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(MovieService::class.java)
        gson = GsonBuilder().setPrettyPrinting().create()

        requestText = findViewById(R.id.request_text)
        responseText = findViewById(R.id.response_text)

        // GET
        findViewById<Button>(R.id.get_button).setOnClickListener {
                if (TextUtils.isEmpty(requestText.text)) {
                    makeListCall {
                        service.getMovies()
                    }
                } else {
                    makeCall {
                        service.getMovie(requestText.text.toString())
                    }
                }
            }

        // POST
        findViewById<Button>(R.id.post_button).setOnClickListener {
            val jsonObject = JSONObject()
            jsonObject.put("id", "4")
            jsonObject.put("title", "The Avengers")
            jsonObject.put("director", "Joss Whedon")
            jsonObject.put("year", "2012")
            makeCall {
                service.createMovie(
                    jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
                )
            }
        }

        // Update
        findViewById<Button>(R.id.put_button).setOnClickListener {
            val jsonObject = JSONObject()
            jsonObject.put("id", "2")
            jsonObject.put("title", "Some Like it Hot")
            jsonObject.put("director", "Billy Wilder")
            jsonObject.put("year", "1959")
            makeCall {
                service.updateMovie(jsonObject.getString("id"), jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull()))
            }
        }

        // Delete
        findViewById<Button>(R.id.delete_button).setOnClickListener {
            makeCall {
                service.deleteMovie(requestText.text.toString())
            }
        }
    }


        fun makeCall(action: suspend () -> Response<MovieModel>) {
            CoroutineScope(Dispatchers.IO).launch {
                var response: Response<MovieModel> = action()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        responseText.text = gson.toJson(response.body())
                    } else {
                        responseText.text = response.code().toString()
                    }
                }
            }
        }

    fun makeListCall(action: suspend () -> Response<List<MovieModel>>) {
        CoroutineScope(Dispatchers.IO).launch {
            var response: Response<List<MovieModel>> = action()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    responseText.text = gson.toJson(response.body())
                } else {
                    responseText.text = response.code().toString()
                }
            }
        }
    }
}

