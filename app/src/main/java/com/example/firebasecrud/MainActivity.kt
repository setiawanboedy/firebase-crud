package com.example.firebasecrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasecrud.adapter.ProductAdapter
import com.example.firebasecrud.databinding.ActivityMainBinding
import com.example.firebasecrud.model.ProductModel
import com.example.firebasecrud.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp

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

        binding.submit.setOnClickListener {
            createProduct()
        }

        mainViewModel.getListProduct()
        initDataProduct()
        initRecyclerView()

        productAdapter.setOnItemClick { product->
            _product = product
            _product?.update_date = Timestamp.now()
            with(binding){
                name.setText(productItem?.name)
                price.setText(productItem?.price.toString())
                description.setText(productItem?.description)
            }
        }
        productAdapter.setOnDelClick {
            mainViewModel.deleteProduct(it.id!!)
        }
    }

    private fun initDataProduct(){
        mainViewModel.listProduct.observe(this) {items->
            onGetListProducts(items)
        }
        mainViewModel.isCreated.observe(this){
            onCreateProduct(it)
        }
        mainViewModel.isUpdate.observe(this){
            onUpdateProduct(it)
        }
        mainViewModel.isDelete.observe(this){
            onDeleteProduct(it)
        }
    }

    private fun onDeleteProduct(isDelete: Boolean){
        if (isDelete){
            mainViewModel.getListProduct()
            Snackbar.make(binding.root, "Delete success", Snackbar.LENGTH_LONG).show()
            resetForm()
        }else
            Snackbar.make(binding.root, "Delete Failed", Snackbar.LENGTH_LONG).show()
    }

    private fun onUpdateProduct(isUpdate: Boolean){
        if (isUpdate){
            mainViewModel.getListProduct()
            Snackbar.make(binding.root, "Update success", Snackbar.LENGTH_LONG).show()
            resetForm()
        }else
            Snackbar.make(binding.root, "Update Failed", Snackbar.LENGTH_LONG).show()
    }

    private fun onCreateProduct(isCreated: Boolean){
        if (isCreated) {
            mainViewModel.getListProduct()
            Snackbar.make(binding.root, "Created success", Snackbar.LENGTH_LONG).show()
            resetForm()
        }else
            Snackbar.make(binding.root, "Created Failed", Snackbar.LENGTH_LONG).show()
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

    private fun createProduct(){
        if (binding.price.text.toString().isNotEmpty() && binding.name.text.toString().isNotEmpty() && binding.description.text.toString().isNotEmpty()){
            val product = ProductModel(
                id = productItem?.id,
                name = binding.name.text.toString(),
                price = binding.price.text.toString().toDouble(),
                description = binding.description.text.toString(),
                create_date = productItem?.create_date ?: Timestamp.now(),
                update_date = productItem?.update_date
            )
            if (productItem?.id != null)
                mainViewModel.updateProduct(product)
            else
                mainViewModel.createProduct(product)
        }
    }

    private fun resetForm(){
        _product = null
        binding.name.text.clear()
        binding.price.text.clear()
        binding.description.text.clear()

    }

}