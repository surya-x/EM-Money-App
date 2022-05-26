package `in`.emmoney.app.homeActivity.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith

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