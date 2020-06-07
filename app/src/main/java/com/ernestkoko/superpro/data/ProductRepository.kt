package com.ernestkoko.superpro.data

import android.app.Application
import androidx.lifecycle.LiveData

// Declares the DAO as a private property in the constructor. Pass in the DAO
//// instead of the whole database, because you only need access to the DAO
//class ProductRepository (private val productDao: ProductsDao){
//    // Room executes all queries on a separate thread.
//    // Observed LiveData will notify the observer when the data has changed.
//    val getAllProducts: LiveData<List<Product>> = productDao.getAllProductsById()
//
//    //insert
//    suspend fun insert(product: Product){
//        productDao.insert(product)
//    }
//    suspend fun update(product: Product){
//        productDao.update(product)
//    }
////     fun getAProduct(key: Long): Product{
////       //return productDao.getAProduct(key)
////    }
//    fun getProductByName(): LiveData<List<Product>>{
//        return productDao.getProductsByName()
//    }
//    //delete by id
//    fun deleteById(key: Long){
//        productDao.deleteById(key)
//    }
//
//}