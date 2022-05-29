package `in`.emmoney.app.homeActivity.data

import `in`.emmoney.app.homeActivity.domain.AllSchemesDao
import `in`.emmoney.app.homeActivity.domain.models.AllSchemesEntity
import `in`.emmoney.app.homeActivity.domain.models.SchemeDetailed
import android.util.Log
import androidx.sqlite.db.SimpleSQLiteQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SchemeRepo (private val allSchemesDao: AllSchemesDao) {

    private val TAG = "SchemeRepo"

//    val allSchemes: LiveData< List<AllSchemesEntity> > = allSchemesDao.getAllSchemes()

    suspend fun insertToAllSchemes(allSchemesEntity: AllSchemesEntity){
        allSchemesDao.insert(allSchemesEntity)
    }

    suspend fun insertListOfSchemesToAllSchemes(allSchemesEntityList: List<AllSchemesEntity>){
        Log.d(TAG, "insertListOfSchemes called, size:${allSchemesEntityList.size}")
        allSchemesDao.insertListOfSchemes(allSchemesEntityList)
    }

    suspend fun deleteFromAllSchemes(allSchemesEntity: AllSchemesEntity){
        allSchemesDao.delete(allSchemesEntity)
    }

    fun getSchemesCountOfAllSchemes() : Int {
        return allSchemesDao.getSchemesCount()
    }

    fun getAllSchemes(): List<AllSchemesEntity>{
        return allSchemesDao.getAllSchemes()
    }

    fun getAllSchemesLimitRand(): List<AllSchemesEntity>{
        val limit = 7
        return allSchemesDao.getAllSchemesLimitRand(limit)
    }

    fun getAllSchemesOrdered(): List<AllSchemesEntity>{
        return allSchemesDao.getAllSchemesOrdered()
    }

    fun getSelectedFunds(): List<AllSchemesEntity>{
        return allSchemesDao.getSelectedFunds()
    }

    fun searchSchemeName(query: String): List<AllSchemesEntity>{
        val likeQuery = "%${query}%"
        val limit = 50
        return allSchemesDao.searchSchemeName(likeQuery, limit)
    }


    fun searchSchemeNameLike(query: String): List<AllSchemesEntity>{
        val allWords = query.split(" ")
        val limit = 50

        var queryTemp = "SELECT * FROM all_schemes_table WHERE "
        for(item in allWords.indices){
            if(item != allWords.size - 1)
                queryTemp += "schemeName LIKE '%${allWords[item]}%' AND "
            else
                queryTemp += "schemeName LIKE '%${allWords[item]}%' LIMIT $limit"
        }

        Log.d(TAG, queryTemp)

        val queryObject = SimpleSQLiteQuery(queryTemp)

        return allSchemesDao.searchSchemeNameList(queryObject)
    }




    suspend fun getAllSchemesFromNetwork(): List<AllSchemesEntity> {
        return EMMoneyClient.remoteDAO.getAllSchemes()
    }

    suspend fun getOneSchemeFromNetwork(schemeID: Int): Response<SchemeDetailed> {
        return withContext(Dispatchers.IO) {
            val call =  async { EMMoneyClient.remoteDAO.getOneSchemes(schemeID).execute() }
            val response = call.await()
            response
        }
    }

//    fun getOneSchemeFromNetwork(schemeCode: Int): SchemeDetailed? {
//        return EMMoneyClient.remoteDAO.getOneSchemes(schemeCode).execute().body()
//    }

}