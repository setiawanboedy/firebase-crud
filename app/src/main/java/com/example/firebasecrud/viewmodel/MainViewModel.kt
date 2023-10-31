package com.example.firebasecrud.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasecrud.model.ProductModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainViewModel: ViewModel() {

    private var db = Firebase.firestore
    private val products = "products"

    val isCreated: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val isUpdate: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val isDelete: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val listProduct: MutableLiveData<List<ProductModel>> by lazy {
        MutableLiveData<List<ProductModel>>()
    }

    fun createProduct(productModel: ProductModel){
        val docRef = db.collection(products)

        docRef.add(productModel.toMap()).addOnSuccessListener {
            Log.d("success", "Berhasil")
            docRef.document(it.id).update(mapOf("id" to it.id))
            isCreated.value = true
        }.addOnFailureListener {
            Log.e("failure", "Gagal")
            isCreated.value = false
        }
    }

    fun updateProduct(productModel: ProductModel){
        val docRef = db.collection(products)
        docRef.document(productModel.id!!).update(productModel.toMap()).addOnSuccessListener {
            Log.d("success", "Berhasil")
            isUpdate.value = true
        }.addOnFailureListener {
            Log.e("failure", "Gagal")
            isUpdate.value = false
        }
    }

    fun deleteProduct(id: String){
        val docRef = db.collection(products)
        docRef.document(id).delete().addOnSuccessListener {
            Log.d("success", "Berhasil")
            isDelete.value = true
        }.addOnFailureListener {
            Log.e("failure", "Gagal")
            isDelete.value = false
        }
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