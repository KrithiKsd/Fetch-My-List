package com.example.fetchmylist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fetchmylist.data.model.Item
import com.example.fetchmylist.data.repository.ItemRepository
import com.example.fetchmylist.ui.item.ItemViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class ItemViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockRepository: ItemRepository

    private lateinit var viewModel: ItemViewModel


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = ItemViewModel(mockRepository)
    }

    @Test
    fun `test fetchItems success`() = runBlocking {
        // Mock successful response
        val itemList = listOf(
            Item(1, 1, "Item 1"),
            Item(2, 2, "Item 2")
        )
        val mockResponse = Response.success(itemList)

        `when`(mockRepository.getItems()).thenReturn(mockResponse)

        viewModel.fetchItems()

        // Assert that items LiveData is updated with the correct data
        viewModel.items.observeForever {
            assertEquals(itemList, it)
        }
    }

    @Test
    fun `test fetchItems error`() {
        // Mock error response
        val mockResponse = Response.error<List<Item>>(
            404,
            ResponseBody.create("application/json".toMediaTypeOrNull(), "Error body")
        )
        runBlocking {
            `when`(mockRepository.getItems()).thenReturn(mockResponse)

            viewModel.fetchItems()

            // Assert that items LiveData is empty after error response
            viewModel.items.observeForever {
                assertEquals(emptyList<Item>(), it)
            }
        }
    }

}


