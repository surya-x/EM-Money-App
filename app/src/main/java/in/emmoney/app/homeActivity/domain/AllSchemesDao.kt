package `in`.emmoney.app.homeActivity.domain

import `in`.emmoney.app.homeActivity.domain.models.AllSchemesEntity
import androidx.room.*

@Dao
interface AllSchemesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(allSchemesEntity: AllSchemesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfSchemes(allSchemesEntityList: List<AllSchemesEntity>)

    @Delete
    suspend fun delete(allSchemesEntity: AllSchemesEntity)

    @Query("SELECT * FROM all_schemes_table")
    fun getAllSchemes(): List<AllSchemesEntity>

    @Query("SELECT * FROM all_schemes_table ORDER BY schemeName ASC")
    fun getAllSchemesOrdered(): List<AllSchemesEntity>

//    @Query("SELECT schemeName FROM all_schemes_table WHERE schemeCode = :i")
//    fun findSchemeNameByCode(i: Int): Any?

    @Query("SELECT count(*) FROM all_schemes_table LIMIT 1")
    fun getSchemesCount(): Int
}