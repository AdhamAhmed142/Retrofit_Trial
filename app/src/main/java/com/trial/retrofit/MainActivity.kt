package com.trial.retrofit

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val baseURL = "https://jsonplaceholder.typicode.com/comments"
    private val tAG: String = "CHECK_RESPONSE"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        getAllComments()
    }

    private fun getAllComments(){
        val api = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)

        api.getComments().enqueue(object : Callback<List<Comments>>{

            override fun onResponse(
                call: Call<List<Comments>>,
                response: Response<List<Comments>>
            ){
                if (response.isSuccessful){
                    response.body()?.let {
                        for (comment in it){
                            Log.i(tAG, "onResponse: ${comment.body}")
                        }
                    }
                }
            }

            override fun onFailure(
                call: Call<List<Comments>?>, t: Throwable) {
                Log.i(tAG, "onFailure: ${t.message}")
            }
        })

    }

}