package `in`.emmoney.app.homeActivity.domain

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AllSchemesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(allSchemesEntity: AllSchemesEntity)

    @Delete
    suspend fun delete(allSchemesEntity: AllSchemesEntity)

    @Query("SELECT * FROM all_schemes_table ORDER BY schemeName ASC")
    fun getAllSchemes(): LiveData< List<AllSchemesEntity> >

//    @Query("SELECT schemeName FROM all_schemes_table WHERE schemeCode = :i")
//    fun findSchemeNameByCode(i: Int): Any?

    @Query("SELECT count(*) FROM all_schemes_table LIMIT 1")
    fun isTableEmpty(): Int
}