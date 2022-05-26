package `in`.emmoney.app.homeActivity.data

import `in`.emmoney.app.homeActivity.domain.AllSchemesDao
import `in`.emmoney.app.homeActivity.domain.models.AllSchemesEntity
import android.util.Log

class SchemeRepo (private val allSchemesDao: AllSchemesDao) {

    val TAG: String = "SchemeRepo.kt"

//    val allSchemes: LiveData< List<AllSchemesEntity> > = allSchemesDao.getAllSchemes()

    suspend fun insert(allSchemesEntity: AllSchemesEntity){
        allSchemesDao.insert(allSchemesEntity)
    }

    suspend fun insertListOfSchemes(allSchemesEntityList: List<AllSchemesEntity>){
        Log.d(TAG, "insertListOfSchemes called, size:${allSchemesEntityList.size}")
        allSchemesDao.insertListOfSchemes(allSchemesEntityList)
    }

    suspend fun delete(allSchemesEntity: AllSchemesEntity){
        allSchemesDao.delete(allSchemesEntity)
    }

    fun getSchemesCount() : Int {
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

}