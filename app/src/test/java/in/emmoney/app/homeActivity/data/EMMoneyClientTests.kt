package `in`.emmoney.app.homeActivity.data


import `in`.emmoney.app.homeActivity.domain.models.SchemeDetailed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.junit.Assert.*
import org.junit.jupiter.api.Test
import retrofit2.Response


class EMMoneyClientTests {
    @Test
    suspend fun `List all mutual funds`(){
        val response = EMMoneyClient.remoteDAO.getAllSchemes()
        assertNotNull(response)
    }


    @Test
    fun testOneSchemes(){
        val schemeCode = 130002
        val response = EMMoneyClient.remoteDAO.getOneSchemes(schemeCode).execute()

        response.body().let {
            assertNotNull(it)
        }
    }

//    @Test
//    suspend fun testOneSchemes2(): Unit = withContext(Dispatchers.IO) {
//        val schemeID = 130002
//        val call =  async { EMMoneyClient.remoteDAO.getOneSchemes(schemeID).execute() }
//        val response = call.await()
//        assertNotNull(response.body())
//    }



}