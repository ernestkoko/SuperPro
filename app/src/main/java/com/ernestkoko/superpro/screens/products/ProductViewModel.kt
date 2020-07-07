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
import com.ernestkoko.superpro.firebase.FirebaseProducts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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
   private val _fireProducts = repository.getFbProducts()
    val fireProducts : LiveData<ArrayList<FirebaseProducts>>
    get() = _fireProducts




    //navigate to product details
    private val _navigateToProductDetails = MutableLiveData<Long>()
    val navigateToProductDetails
    get() = _navigateToProductDetails

    //set the id of the item clicked
    fun onProductItemClicked(id: Long){
        _navigateToProductDetails.value = id
    }
    //called the navigation is done
    fun onProductDetailsNavigated(){
        _navigateToProductDetails.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}