package com.ernestkoko.superpro.screens.products

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ernestkoko.superpro.data.Product
import com.ernestkoko.superpro.data.ProductDatabase
import com.ernestkoko.superpro.data.ProductRepository

import com.ernestkoko.superpro.data.ProductsDao
import kotlinx.coroutines.*

class ProductViewModel(
    application:Application): AndroidViewModel(application) {
    private val repository: ProductRepository
    //private val repository: ProductRepository
    //job allows us cancel the coroutine when the coroutine is no longer needed
    private var viewModelJob = Job()
    // the dispatcher determines the thread the coroutine runs on
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
   // val allProducts = dataSource.getProductsByName()
    val allProducts1: LiveData<List<Product>>

    private var products = MutableLiveData<Product?>()
   // private val products = database.getAllProductsById()
    init {
       val productDao = ProductDatabase.getDataBase(application).productDao()
       repository = ProductRepository(productDao)
       allProducts1 = repository.getAllProducts
    }

//    fun insert(product: Product){
//        viewModelScope.launch(Dispatchers.IO){
//           // repository.insert(product)
//        }
//    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}