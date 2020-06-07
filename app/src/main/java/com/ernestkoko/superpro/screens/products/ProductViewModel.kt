package com.ernestkoko.superpro.screens.products

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ernestkoko.superpro.data.Product
import com.ernestkoko.superpro.data.ProductDatabase

import com.ernestkoko.superpro.data.ProductsDao
import kotlinx.coroutines.*

class ProductViewModel(
    val dataSource: ProductsDao, application:Application): AndroidViewModel(application) {
    //private val repository: ProductRepository
    //job allows us cancel the coroutine when the coroutine is no longer needed
    private var viewModelJob = Job()
    // the dispatcher determines the thread the coroutine runs on
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val allProducts = dataSource.getProductsByName()

    private var products = MutableLiveData<Product?>()
   // private val products = database.getAllProductsById()
    init {
        val productDao = ProductDatabase.getDataBase(application).productDao()
//        repository = ProductRepository(productDao)
//        allProducts = repository.getProductByName()
        initializeProduct()
    }
    fun insert(product: Product){
        viewModelScope.launch(Dispatchers.IO){
           // repository.insert(product)
        }
    }

    private fun initializeProduct(){
        uiScope.launch {
            products.value = getProductFromDatabase()
        }
    }
    private suspend fun getProductFromDatabase(): Product?{
        return withContext(Dispatchers.IO){
            var product = dataSource.getAProduct()
            if (product != null) product else null
        }
    }

    //get the repository
//    private val repository: ProductRepository
    // Using LiveData and caching what getAllProducts returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
//    // - Repository is completely separated from the UI through the ViewModel.
//    val allProducts: LiveData<List<Product>>
//    init {
//        val productDao = ProductDatabase.getDataBase(application).productDao()
//        repository = ProductRepository(productDao)
//        allProducts = repository.getAllProducts
//    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
//    fun insert(product: Product) = viewModelScope.launch(Dispatchers.IO){
//        repository.insert(product)
//    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}