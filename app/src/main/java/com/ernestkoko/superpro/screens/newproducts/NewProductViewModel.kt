package com.ernestkoko.superpro.screens.newproducts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ernestkoko.superpro.data.Product
import com.ernestkoko.superpro.data.ProductDatabase
import com.ernestkoko.superpro.data.ProductRepository
import com.ernestkoko.superpro.data.ProductsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewProductViewModel(
     application: Application): AndroidViewModel(application){
    private val dataSource: ProductsDao
    private val repository: ProductRepository


    //create a job
    private var viewModeJob= Job()

    //create a scope to launch coroutine on the ui thread
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModeJob)
    init {
        val productDao = ProductDatabase.getDataBase(application).productDao()
        repository = ProductRepository(productDao)
        dataSource = ProductDatabase.getDataBase(getApplication()).productDao()
    }

    fun insert(product: Product)= viewModelScope.launch(Dispatchers.IO){
            repository.insert(product)
    }

}