package com.ernestkoko.superpro.screens.product_details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ernestkoko.superpro.data.Product
import com.ernestkoko.superpro.data.ProductDatabase
import com.ernestkoko.superpro.data.ProductRepository

class ProductDetailsViewModel( private val productId: Long = 0L,
                               application: Application): AndroidViewModel(application){

    //product with the id
    private val product = MediatorLiveData<Product>()




    // instantiate the repository
    private val repository: ProductRepository
    //
    val product1: LiveData<Product>
    get() = product

    init {
        // create access to the Dao through the database instance
        val productDao = ProductDatabase.getDataBase(application).productDao()
        //initialise the repository
        repository = ProductRepository(productDao)
        //get the product with the  id passed
     product.addSource(repository.getProductById(productId),product::setValue)
       // product1 = repository.getProductById(productId)
    }
    // the live data that the xml file will observe
    val prodName = product1
}