package com.example.fetchmylist.ui.item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchmylist.data.model.Item
import com.example.fetchmylist.data.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(private val itemRepository: ItemRepository) : ViewModel() {

    private val _items = MutableLiveData<List<Item>>()
    var items: LiveData<List<Item>> = _items

    init {
        fetchItems() // Fetching items when ViewModel is created
    }

    //method to sort the results first by "listId" then by "name" also filtered items where "name" is blank or null
    fun fetchItems() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = itemRepository.getItems()
                if (response.isSuccessful) {
                    val items = response.body()?.filter { !it.name.isNullOrBlank() } ?: emptyList()
                    _items.postValue(items.sortedWith(compareBy({ it.listId }, { it.name })))
                } else {
                    Log.e("ItemViewModel", "Error response: ${response.message()}")
                    _items.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("ItemViewModel", "Exception: ${e.message}", e)
                _items.postValue(emptyList())
            }
        }
    }

    //method to sort the list by listId and then name
    fun sortByNameAndId() {
        _items.value = _items.value?.sortedWith(compareBy({ it.listId }, { it.name }))
    }

    //method to sort the list by id
    fun sortById() {
        _items.value = _items.value?.sortedBy { it.id }
    }

    //method to sort the list by only name
    fun sortByName() {
        _items.value = _items.value?.sortedBy { it.name }
    }

}
