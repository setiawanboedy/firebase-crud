package com.example.firebasecrudclient.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasecrudclient.model.ProductModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainViewModel: ViewModel() {

    private var db = Firebase.firestore
    private val products = "products"

    val listProduct: MutableLiveData<List<ProductModel>> by lazy {
        MutableLiveData<List<ProductModel>>()
    }

    fun getListProduct(){
        val docRef = db.collection(products)
        docRef.get().addOnSuccessListener { items ->
            val products = ArrayList<ProductModel>()

            for (item in items.documents){
                val product = ProductModel(
                id = item.id,
                name = item.get("name") as String?,
                price = item.get("price") as Double?,
                description = item.get("description") as String?,
                create_date = item.get("create_date") as Timestamp?,
                update_date = item.get("update_date") as Timestamp?,
                )
                products.add(product)
            }

            listProduct.value = products
        }.addOnFailureListener {
            Log.e("failure", "Gagal")
        }
    }
}