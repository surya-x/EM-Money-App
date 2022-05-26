package `in`.emmoney.app.homeActivity.data

import `in`.emmoney.app.homeActivity.domain.RemoteDAO
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object EMMoneyClient {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(5000, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.mfapi.in")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

//    val retrofit = Retrofit.Builder()
//        .baseUrl("https://api.mfapi.in")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()

    val remoteDAO = retrofit.create(RemoteDAO::class.java)
}