package com.ernestkoko.superpro.screens.product_details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ProductDetailsViewModelFactory(private val productId: Long ,
                                     private val application: Application):
    ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)){
            return ProductDetailsViewModel(productId,application) as T
        }
        throw IllegalArgumentException("Unknown view Model class")
    }

}