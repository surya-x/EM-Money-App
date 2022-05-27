package `in`.emmoney.app.homeActivity.data

import `in`.emmoney.app.homeActivity.domain.AllSchemesDao
import `in`.emmoney.app.homeActivity.domain.models.AllSchemesEntity
import `in`.emmoney.app.homeActivity.domain.models.SchemeDetailed
import android.util.Log

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

    fun getAllSchemesOrdered(): List<AllSchemesEntity>{
        return allSchemesDao.getAllSchemesOrdered()
    }

    suspend fun getAllSchemesFromNetwork(): List<AllSchemesEntity> {
        return EMMoneyClient.remoteDAO.getAllSchemes()
    }

//    suspend fun getDetailedSchemeFromNetwork(schemeCode: Int): SchemeDetailed {
//        return EMMoneyClient.remoteDAO.getOneSchemes(schemeCode)
//    }

}