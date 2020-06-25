package com.ernestkoko.superpro.screens.login.login_reset

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class LoginResetViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginResetViewModel::class.java)) {
            return LoginResetViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}