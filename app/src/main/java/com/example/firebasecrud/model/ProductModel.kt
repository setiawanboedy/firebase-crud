package com.example.firebasecrudclient.model

import com.google.firebase.Timestamp

data class ProductModel(
    var id: String? = null,
    var name: String? = null,
    var price: Double? = null,
    var description: String? = null,
    var create_date: Timestamp? = null,
    var update_date: Timestamp? = null
){
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
            "price" to price,
            "description" to description,
            "create_date" to create_date,
            "update_date" to update_date,
        )
    }
}
