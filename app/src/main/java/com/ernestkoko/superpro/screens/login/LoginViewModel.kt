package com.ernestkoko.superpro.screens.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ernestkoko.superpro.FirebaseUserLiveData
import com.google.firebase.auth.FirebaseAuth


class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "LoginViewModel"

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = FirebaseUserLiveData().observeForever {
        if (it != null) {
            Log.i(TAG, "AuthState: Authenticated")

            AuthenticationState.AUTHENTICATED
        }else{
            Log.i(TAG, "AUthState: Not Authenticated")
            AuthenticationState.UNAUTHENTICATED
        }
    }




}