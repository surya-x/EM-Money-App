package `in`.emmoney.app.homeActivity.data


import org.junit.Assert.*
import org.junit.jupiter.api.Test


class EMMoneyClientTests {
    @Test
    suspend fun `List all mutual funds`(){
        val response = EMMoneyClient.remoteDAO.getAllSchemes()
        assertNotNull(response)
    }


    @Test
    fun testOneSchemes(){
//        val schemeCode = 130002
        val response = EMMoneyClient.remoteDAO.getOneSchemes().execute()

        response.body().let {
            assertNotNull(it)
        }
    }

}