package com.ernestkoko.superpro.screens.newproducts

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class NewProductViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewProductViewModel::class.java)){
            return NewProductViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }

}