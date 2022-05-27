package `in`.emmoney.app.homeActivity.domain

import `in`.emmoney.app.homeActivity.domain.models.AllSchemesEntity
import `in`.emmoney.app.homeActivity.domain.models.SchemeDetailed
import retrofit2.Call
import retrofit2.http.GET

interface RemoteDAO {
//    @GET("/mf")
//    suspend fun getAllSchemes (): Call<ArrayList<AllSchemesItem>>

    @GET("/mf")
    suspend fun getAllSchemes (): List<AllSchemesEntity>

    @GET("/mf/100351")
    fun getOneSchemes (): Call<SchemeDetailed>

//    @GET("/mf/100351")
//    fun getOneSchemes (): SchemeDetailed

//    @GET("/mf/100351")
//    fun getOneSchemes (schemeCode: Int): SchemeDetailed

}