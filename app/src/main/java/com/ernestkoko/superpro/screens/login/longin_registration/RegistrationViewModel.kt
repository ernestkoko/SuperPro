package com.ernestkoko.superpro.screens.login.longin_registration

import android.app.Application
import android.text.TextUtils
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

/**
 * Registration page viewModel class where all the business logic for the fragment are carried out
 * It contains Live data that the fragment can observe
 */

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {
    enum class AuthenticationState {
        UNAUTHENTICATED,        // Initial state, the user needs to authenticate
        AUTHENTICATED,        // The user has authenticated successfully
        INVALID_AUTHENTICATION  // Authentication failed
    }

    //instance of the firebase auth
    private val mAuth = FirebaseAuth.getInstance()

    //email live data for two way data binding

    val _email = MutableLiveData<String>()
    var email: LiveData<String>
        set(value) {
            _email.value = value.value
        }
        get() = _email

    //password
    val _password = MutableLiveData<String>()
    var password: LiveData<String>
        set(value) {
            _password.value = value.value
        }
        get() = _password

    //password2
    val _password2 = MutableLiveData<String>()
    var password2: LiveData<String>
        set(value) {
            _password2.value = value.value
        }
        get() = _password2


    //Live data of registration state
    val registrationState =
        MutableLiveData<AuthenticationState>()

    //live data to be observed if the email is valid or not
    private val _validEmail = MutableLiveData<Boolean>()
    val validEmail: LiveData<Boolean>
        get() = _validEmail

    //show dialogue
    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean>
        get() = _showDialog

    //check if registration is successful
    private val _isRegSuccessful = MutableLiveData<Boolean>()
    val isRegSuccessful: LiveData<Boolean>
        get() = _isRegSuccessful

    //check if registration task is complete
    private val _isRegComplete = MutableLiveData<Boolean>()
    val isRegComplete: LiveData<Boolean>
        get() = _isRegComplete

    //check if the fields are empty
    private val _isFieldEmpty = MutableLiveData<Boolean>()
    val isFieldEmpty: LiveData<Boolean>
        get() = _isFieldEmpty

    //check if the passwords match
    private val _arePasswordsMatch = MutableLiveData<Boolean>()
    val arePasswordsMatch: MutableLiveData<Boolean>
        get() = _arePasswordsMatch


    //authentication token
    var authToken = ""
        private set

    //init block is called anytime the class is instantiated
    init {
        //initialise the validEmail
//        _validEmail.value = false
//        _showDialog.value = false
//        _isRegSuccessful.value = false
//        _isRegComplete.value = false
//        _isFieldEmpty.value = true


    }

    //fun that collects profile data
    fun registerNewUser() {
        //check if input strings are empty or null
        if (!isEmptyOrNull(_email.value) &&
            !isEmptyOrNull(_password.value) &&
            !isEmptyOrNull(_password2.value)
        ) {
            //set the live data _isFieldEmpty to false
            _isFieldEmpty.value = false
            //check if the email is valid
            if (isValidEmail(_email.value)) {
                //set this to true
                _validEmail.value = true
                //check if the password match
                if (doStringsMatch(_password.value!!, _password2.value!!)) {
                    //if passwords are match show a progress bar on from the fragment
                    _arePasswordsMatch.value = true
                    //set isRegComplete to false so we can show the progress bar
                    _isRegComplete.value = false
                    // register the new user here
                    //get the instance of Firebase Authentication and create new user with email and password
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(_email.value!!, _password.value!!)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {


                                val user = mAuth.currentUser
                                //sign out the user so he can be redirected to login screen
                                mAuth.signOut()
                                //set isRegComplete to true so we can hide the progress bar
                                _isRegComplete.value = true
                                //set the isRegSuccessful to true so we can toast a message to the user
                                _isRegSuccessful.value = true
                                Log.i(
                                    "Registration",
                                    "Complete: AuthState: " + mAuth.currentUser?.uid
                                )

                            } else {
                                //set the regComplete to true
                                _isRegComplete.value = true
                                //set the _isRegSuccessful to false
                                _isRegSuccessful.value = false

                                Log.i("Registration", "Unable to register")

                            }
                            _isRegComplete.value = true


                        }

                } else {
                    //set passwords match to false
                    _arePasswordsMatch.value = false


                }

            } else {
                //the email is not valid
                _validEmail.value = false

            }


        } else {
            //Alert the user to fill all the fields
            _isFieldEmpty.value = true
        }


    }


    /**
     * @param s1 is the first string
     * @param s2 is the second string
     * returns true if the both strings are equal or match
     */
    fun doStringsMatch(s1: String, s2: String): Boolean {
        //returns true if the two passwords are equal

        return s1 == s2

    }

    /**
     * @param email is the email to validate
     * returns true if the email meets required email standard
     */
    fun isValidEmail(email: CharSequence?): Boolean {
        //returns true if email is not empty and it matches standard email format
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    }

    /**
     * @param string is the string to be tested
     * the method returns true if the string null or empty
     *
     */
    private fun isEmptyOrNull(string: String?): Boolean = string.isNullOrEmpty()
}