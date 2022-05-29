package `in`.emmoney.app.homeActivity.domain

import `in`.emmoney.app.homeActivity.domain.models.AllSchemesEntity
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery

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

    @Query("SELECT * FROM all_schemes_table ORDER BY RANDOM() LIMIT :limit")
    fun getAllSchemesLimitRand(limit: Int): List<AllSchemesEntity>

    @Query("SELECT * FROM all_schemes_table ORDER BY schemeName ASC")
    fun getAllSchemesOrdered(): List<AllSchemesEntity>

//    @Query("SELECT schemeName FROM all_schemes_table WHERE schemeCode = :i")
//    fun findSchemeNameByCode(i: Int): Any?

    @Query("SELECT * FROM all_schemes_table WHERE schemeCode = '120594' OR schemeCode = '120539' OR schemeCode = '120578' OR schemeCode = '125354' OR schemeCode = '120823' OR schemeCode = '128051'")
    fun getSelectedFunds(): List<AllSchemesEntity>


    @Query("SELECT count(*) FROM all_schemes_table LIMIT 1")
    fun getSchemesCount(): Int

    @Query("SELECT * FROM all_schemes_table WHERE schemeName LIKE :query LIMIT :limit")
    fun searchSchemeName(query: String, limit: Int): List<AllSchemesEntity>

    @RawQuery
    fun searchSchemeNameList(query: SimpleSQLiteQuery): List<AllSchemesEntity>


}