package com.example.fetchmylist.ui.item

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetchmylist.MyApplication
import com.example.fetchmylist.databinding.ActivityItemBinding
import com.example.fetchmylist.ui.adapter.ItemAdapter

class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemBinding
    private lateinit var adapter: ItemAdapter
    private lateinit var viewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializing ViewModel
        val myApplication = application as MyApplication
        viewModel = ItemViewModel(myApplication.appContainer.itemRepo)

        // Initializing RecyclerView
        binding.recyclerList.layoutManager = LinearLayoutManager(this)
        adapter = ItemAdapter()
        binding.recyclerList.adapter = adapter

        // Observing items LiveData from the ViewModel
        viewModel.items.observe(this) { items ->
            adapter.submitList(items)
        }

        // Fetching items when Activity is created
        viewModel.fetchItems()

        // Set onClickListener for buttons
        binding.buttonNameId.setOnClickListener {
            viewModel.sortByNameAndId()
        }

        binding.buttonId.setOnClickListener {
            viewModel.sortById()
        }

        binding.buttonname.setOnClickListener {
            viewModel.sortByName()
        }

        //method to pull-to-refresh list items
        binding.container.setOnRefreshListener {
            if (isNetworkAvailable()) {
                binding.container.isRefreshing =false
                viewModel.fetchItems()
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
                binding.container.isRefreshing = false
            }
        }

        //method to search for item
       binding.searchEditText.addTextChangedListener(object : TextWatcher {
           override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
           override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

           override fun afterTextChanged(s: Editable?) {
               val searchText = s.toString().trim()
               viewModel.filterItems(searchText)
           }
       })
    }

    //method checking for network
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
