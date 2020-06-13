package com.ernestkoko.superpro.screens.product_details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ernestkoko.superpro.data.ProductDatabase
import com.ernestkoko.superpro.data.ProductRepository

class ProductDetailsViewModel(application: Application): AndroidViewModel(application){

    // instantiate the repository
    private val repository: ProductRepository
    init {
        // create access to the Dao through the database instance
        val productDao = ProductDatabase.getDataBase(application).productDao()
        //initialise the repository
        repository = ProductRepository(productDao)
    }
}