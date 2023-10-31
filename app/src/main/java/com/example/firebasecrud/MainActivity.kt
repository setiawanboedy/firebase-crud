package com.example.firebasecrudclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasecrud.adapter.ProductAdapter
import com.example.firebasecrudclient.model.ProductModel
import com.example.firebasecrudclient.viewmodel.MainViewModel
import com.example.firebasecrudclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    private var _product: ProductModel? = null
    private val productItem get() = _product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.refresh.setOnClickListener {
            mainViewModel.getListProduct()
        }

        mainViewModel.getListProduct()
        initDataProduct()
        initRecyclerView()

    }

    private fun initDataProduct(){
        mainViewModel.listProduct.observe(this) {items->
            onGetListProducts(items)
        }

    }

    private fun onGetListProducts(items: List<ProductModel>){
        productAdapter.submitList(items)
    }

    private fun initRecyclerView(){
        productAdapter = ProductAdapter()
        binding.rvList.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }
    }


}