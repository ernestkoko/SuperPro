package com.ernestkoko.superpro.screens.login.longin_registration

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthProvider
import com.google.firebase.auth.FirebaseUser

/**
 * Registration page viewModel class where all the business logic for the fragment are carried out
 * It contains Live data that the fragment can observe
 */

class RegistrationViewModel() : ViewModel() {
    private val TAG = "RegViewModel"

    enum class AuthenticationState {
        UNAUTHENTICATED,        // Initial state, the user needs to authenticate
        AUTHENTICATED,        // The user has authenticated successfully
        INVALID_AUTHENTICATION  // Authentication failed
    }

    //instance of the firebase auth
    private var mAuth = FirebaseAuth.getInstance()

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
    val showProgressBar = MutableLiveData<Boolean>()


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
        showProgressBar.value = false
        //initialise the validEmail
//        _validEmail.value = false
//        _showDialog.value = false
//        _isRegSuccessful.value = false
//        _isRegComplete.value = false
//        _isFieldEmpty.value = true


    }


    //fun that collects profile data
    fun registerNewUser() {
        Log.i(TAG, "Reg new user called")
        //check if input strings are empty or null
        if (!_email.value.isNullOrEmpty() &&
            !_password.value.isNullOrEmpty() &&
            !_password2.value.isNullOrEmpty()
        ) {
            Log.i(TAG, "Fields are  not empty")
            //set the live data _isFieldEmpty to false
            _isFieldEmpty.value = false
            //check if the email is valid
            if (isValidEmail(_email.value!!)) {
                Log.i(TAG, "Email is valid")
                //set this to true
                _validEmail.value = true
                //check if the password match
                if (doStringsMatch(_password.value!!, _password2.value!!)) {
                    Log.i(TAG, "Passwords match")
                    //if passwords are match show a progress bar on from the fragment
                    _arePasswordsMatch.value = true
                    //set isRegComplete to false so we can show the progress bar
                    showProgressBar.value = true
                    mAuth.createUserWithEmailAndPassword(_email.value!!, _password.value!!)
                        .addOnCompleteListener {
                            if (it.isComplete) {
                                //task is complete
                                Log.i(TAG, "CreateEmail: completed")

                                if (it.isSuccessful) {
                                    Log.i(TAG, "CreateEmail: Successful")
                                    //task is successful
                                    sendVerificationEmail(mAuth.currentUser!!)
                                    showProgressBar.value = false
                                    _isRegSuccessful.value = true
                                } else {
                                    Log.i(TAG, "CreateEmail: Not successful")
                                    Log.i(TAG,it.exception?.localizedMessage)
                                    //task is not successful

                                    showProgressBar.value = false
                                    _isRegSuccessful.value = false
                                }
                            } else {
                                //task is not complete
                                Log.i(TAG, "CreateEmail: Not complete")
                                Log.i(TAG, it.exception?.localizedMessage)
                            }
                        }


                     if (mAuth.currentUser != null) sendVerificationEmail(mAuth.currentUser!!)

                    Log.i(TAG, "Reg task finished")
                    showProgressBar.value = false


                } else {
                    Log.i(TAG, "Passwords do not match")
                    //set passwords match to false
                    _arePasswordsMatch.value = false


                }


            } else {
                Log.i(TAG, "Email is invalid")
                //the email is not valid
                _validEmail.value = false
            }

        } else {
            Log.i(TAG, "Fields are empty")
            //Alert the user to fill all the fields
            _isFieldEmpty.value = true
        }


    }

    /**
     * send a verification email to new user
     */
    private fun sendVerificationEmail(user: FirebaseUser) {
        Log.i(TAG, "sendVerificationEmail(): called")
        //get  the user
       // val user = mAuth.currentUser
        //send verification email to the user
        user!!.sendEmailVerification().addOnSuccessListener {
            //check if task was successful
            Log.i(TAG, "Verification email was sent: Success")
            //sign out the user so they can verify their email before logging
            //in on the login page
            mAuth.signOut()

        }.addOnFailureListener {
            Log.i(TAG, "Verification Email sending: Failed")
            //sign out the user
            mAuth.signOut()
        }
        Log.i(TAG, "User signed out")
        //sign out the user so they can verify their email before logging
        //in on the login page
        mAuth.signOut()
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
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email!!).matches()

    }

//    private fun authState(firebaseCallback: FirebaseCallback) {
//        // register the new user here
//        //get the instance of Firebase Authentication and create new user with email and password
//        mAuth.createUserWithEmailAndPassword(_email.value!!, _password.value!!)
//            .addOnCompleteListener {
//                if (it.isComplete) {
//                    Log.i(TAG, "Task is  completed")
//                    //task is complete
//                    if (it.isSuccessful) {
//                        Log.i(TAG, "Task is not successful")
//                        //is successful
//                        sendVerificationEmail()
//                        showProgressBar.value = false
//                        _isRegSuccessful.value = true
//                    } else {
//                        Log.i(TAG, "Task is not successful")
//                        Log.i(TAG, it.exception!!.localizedMessage)
//                        //not successful
//                    }
//
//                } else {
//                    //task not complete
//                    Log.i(TAG, "Task is not completed")
//                    Log.i(TAG, it.exception!!.localizedMessage)
//                }
//                Log.i(TAG, "Reg TaskSuccessful")
    //send a verification email to the user
//                sendVerificationEmail()
//                showProgressBar.value = false
//                _isRegSuccessful.value = true

//            }.addOnFailureListener {
//                Log.i(TAG, "Reg Task unsuccessful")
//                Log.i(TAG, it.localizedMessage)
//                //set the regComplete to true
//                showProgressBar.value = false
//                //set the _isRegSuccessful to false
//                _isRegSuccessful.value = false
//
//                Log.i(TAG, "Unable to register")
//
//            }

}


//private interface FirebaseCallback{
//    fun callback(firebaseAuth: FirebaseAuth)
//
//}