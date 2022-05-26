package `in`.emmoney.app.homeActivity.data

import `in`.emmoney.app.homeActivity.domain.AllSchemesDao
import `in`.emmoney.app.homeActivity.domain.AllSchemesEntity
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
//    private lateinit var allSchemesDao: AllSchemesDao
//    private lateinit var db: SchemesRoomDatabase
//
//    @Before
//    fun createDb() {
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        db = Room.inMemoryDatabaseBuilder(
//            context, SchemesRoomDatabase::class.java).build()
//        allSchemesDao = db.getAllSchemesDao()
//    }
//
//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        db.close()
//    }
//
//    @Test
//    @Throws(Exception::class)
//    suspend fun writeUserAndReadInList() {
//        val myScheme= AllSchemesEntity(10039, "HDFC SURYA FUND")
//        allSchemesDao.insert(myScheme)
//        val byName = allSchemesDao.findSchemeNameByCode(10039)
////        assertThat(byName.get(0), equalTo(myScheme))
//        assertEquals("HDFC SURYA FUND", byName)
//    }
}