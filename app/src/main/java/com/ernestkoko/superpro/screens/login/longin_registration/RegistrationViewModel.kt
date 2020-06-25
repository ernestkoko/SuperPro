package com.ernestkoko.superpro.screens.login.longin_registration

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class RegistrationViewModel(application: Application): AndroidViewModel(application) {
    enum class RegistrationState {
        COLLECT_PROFILE_DATA,
        REGISTRATION_COMPLETED
    }
    //Live data of registration state
    val registrationState = MutableLiveData<RegistrationState>(RegistrationState.COLLECT_PROFILE_DATA)
    //authentication token
    var authToken = ""
    private set

    //fun that collects profile data
    fun collectProfileData(compName: String?, email: String?, phoneNum: Long?,
                 password1: String?,password2: String?, address: String?){

    }
}