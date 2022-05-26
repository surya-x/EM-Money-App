package `in`.emmoney.app.homeActivity.data

import org.junit.Test
import org.junit.Assert.*

class EMMoneyClientTests {
    @Test
    suspend fun `List all mutual funds`(){
        val response = EMMoneyClient.remoteDAO.getAllSchemes().execute()
        response.body()!!.let {
            assertNotNull(it)
//            assertNotNull(it.)
//            assertEquals(it.)
//            assert(it.allSchemes?.size!! > 10)
        }
    }


    @Test
    suspend fun testOneSchemes(){
        val response = EMMoneyClient.remoteDAO.getOneSchemes().execute()
        response.body()!!.let {
            assertNotNull(it)
            assertEquals(3972, it.data?.size)
        }
    }
}