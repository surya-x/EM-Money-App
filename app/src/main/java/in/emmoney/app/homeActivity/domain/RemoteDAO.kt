package `in`.emmoney.app.homeActivity.domain

import `in`.emmoney.app.homeActivity.domain.models.AllSchemesItem
import `in`.emmoney.app.homeActivity.domain.models.SchemeDetailed
import retrofit2.Call
import retrofit2.http.GET
import java.util.ArrayList

interface RemoteDAO {
    @GET("/mf")
    suspend fun getAllSchemes (): Call<ArrayList<AllSchemesItem>>

    @GET("/mf/100351")
    suspend fun getOneSchemes (): Call<SchemeDetailed>

//    @GET("/mf")
//    fun getAllSchemes (): Call<AllSchemesItem>
}