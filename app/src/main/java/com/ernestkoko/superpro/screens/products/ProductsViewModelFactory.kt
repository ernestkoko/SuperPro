package com.ernestkoko.superpro.screens.products

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ernestkoko.superpro.data.ProductsDao
import java.lang.IllegalArgumentException

class ProductsViewModelFactory (
    private val dataSource: ProductsDao,
    private val application: Application): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)){
            return ProductViewModel(
                dataSource, application
            ) as T
        }
        throw  IllegalArgumentException("Unknown viewModel class")
    }

}