package `in`.emmoney.app.homeActivity.data

import `in`.emmoney.app.homeActivity.domain.AllSchemesDao
import `in`.emmoney.app.homeActivity.domain.AllSchemesEntity
import `in`.emmoney.app.homeActivity.domain.RemoteDAO
import androidx.lifecycle.LiveData

class SchemeRepo (private val allSchemesDao: AllSchemesDao, val remoteDAO: RemoteDAO) {

    val allSchemes: LiveData< List<AllSchemesEntity> > = allSchemesDao.getAllSchemes()

    suspend fun insertToDB(allSchemesEntity: AllSchemesEntity){
        allSchemesDao.insert(allSchemesEntity)
    }

    suspend fun deleteFromDB(allSchemesEntity: AllSchemesEntity){
        allSchemesDao.delete(allSchemesEntity)
    }

    fun isTableEmptyDB() : Int {
        return allSchemesDao.isTableEmpty()
    }

//    fun getAllSchemes(): LiveData< List<AllSchemesEntity> >
}