package edu.du.week6apis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import edu.du.week6apis.model.DogModel
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
    lateinit var service: DogService
    lateinit var gson: Gson
    lateinit var requestText: TextView
    lateinit var responseText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/jmmolis/Week6-API/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(DogService::class.java)
        gson = GsonBuilder().setPrettyPrinting().create()

        requestText = findViewById(R.id.request_text)
        responseText = findViewById(R.id.response_text)

        // GET
        findViewById<Button>(R.id.get_button).setOnClickListener {
                if (TextUtils.isEmpty(requestText.text)) {
                    makeListCall {
                        service.getDogs()
                    }
                } else {
                    makeCall {
                        service.getDog(requestText.text.toString())
                    }
                }
            }

        // POST
        findViewById<Button>(R.id.post_button).setOnClickListener {
            val jsonObject = JSONObject()
            jsonObject.put("id", "1")
            jsonObject.put("name", "Mia")
            jsonObject.put("breed", "Border Collie")
            jsonObject.put("weight", "49")
            jsonObject.put("shelter", "MaxFund")
            jsonObject.put("adoptable", "true")
            makeCall {
                service.createDog(
                    jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
                )
            }
        }

        // Update
        findViewById<Button>(R.id.put_button).setOnClickListener {
            val jsonObject = JSONObject()
            jsonObject.put("id", "2")
            jsonObject.put("name", "Moe")
            jsonObject.put("breed", "Pug")
            jsonObject.put("weight", "19")
            jsonObject.put("shelter", "MaxFund")
            jsonObject.put("adoptable", "true")
            makeCall {
                service.updateDog(jsonObject.getString("id"), jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull()))
            }
        }

        // Delete
        findViewById<Button>(R.id.delete_button).setOnClickListener {
            makeCall {
                service.deleteDog(requestText.text.toString())
            }
        }
    }


        fun makeCall(action: suspend () -> Response<DogModel>) {
            CoroutineScope(Dispatchers.IO).launch {
                var response: Response<DogModel> = action()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        responseText.text = gson.toJson(response.body())
                    } else {
                        responseText.text = response.code().toString()
                    }
                }
            }
        }

    fun makeListCall(action: suspend () -> Response<List<DogModel>>) {
        CoroutineScope(Dispatchers.IO).launch {
            var response: Response<List<DogModel>> = action()
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

