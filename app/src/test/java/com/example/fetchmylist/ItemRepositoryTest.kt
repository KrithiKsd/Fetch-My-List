package com.example.fetchmylist

import com.example.fetchmylist.data.model.Item
import com.example.fetchmylist.data.network.ItemAPIService
import com.example.fetchmylist.data.repository.ItemRepository
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class ItemRepositoryTest {
    @Mock
    private lateinit var mockApiService: ItemAPIService

    private lateinit var repository: ItemRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = ItemRepository(mockApiService)
    }

    @Test
    fun `test getItems() success`() {
        // Mock successful response
        val itemList = listOf(Item(1, 1, "Item 1"))
        val mockResponse = Response.success(itemList)
        runBlocking {
            `when`(mockApiService.getItems()).thenReturn(mockResponse)
            val response = repository.getItems()
            assertNotNull(response)
            assertTrue(response.isSuccessful)
            assertNotNull(response.body())
            assertTrue(response.body()?.isNotEmpty() ?: false)
        }
    }

    @Test
    fun `test getItems() failure`() {
        // Mock failure response with non-null body
        val mockResponse = Response.error<List<Item>>(404, ResponseBody.create("application/json".toMediaTypeOrNull(), "Error body"))
        runBlocking {
            `when`(mockApiService.getItems()).thenReturn(mockResponse)
            val response = repository.getItems()
            assertNotNull(response)
            assertTrue(!response.isSuccessful)
            assertNotNull(response.errorBody()?.string())
        }
    }
}






