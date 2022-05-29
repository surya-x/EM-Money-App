package `in`.emmoney.app.homeActivity.domain

import `in`.emmoney.app.homeActivity.domain.models.AllSchemesEntity
import `in`.emmoney.app.homeActivity.domain.models.SchemeDetailed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteDAO {
//    @GET("/mf")
//    suspend fun getAllSchemes (): Call<ArrayList<AllSchemesItem>>

    @GET("/mf")
    suspend fun getAllSchemes (): List<AllSchemesEntity>

    @GET("/mf/{scheme_id}")
    fun getOneSchemes (@Path(value = "scheme_id", encoded = true) scheme_id: Int): Call<SchemeDetailed>

}