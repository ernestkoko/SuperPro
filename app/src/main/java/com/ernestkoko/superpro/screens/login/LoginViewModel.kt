package com.ernestkoko.superpro.screens.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel(application: Application): AndroidViewModel(application){
    private val TAG ="LoginViewModel"
    //get reference to the authentication state of the user
    private lateinit var mAuthStateListener: FirebaseAuth.AuthStateListener
    //live data of the email field
    val mEmail = MutableLiveData<String>()
    //live data of password
    val mPassword = MutableLiveData<String>()
    //live data to indicate the fields are empty or not
    val isEmpty = MutableLiveData<Boolean>()
    //live data of task successful
    val isSignInSuccessful = MutableLiveData<Boolean>()
    //live data of successful authentication
    val isAuthSuccessful = MutableLiveData<Boolean>()


    init {
        //call the authState listener
        authStateListener()
        //add the auth state listener when the view model is instantiated
       // FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener)

    }
    fun signInMethod(){
        if (!isEmptyOrNull(mEmail.value) && !isEmptyOrNull(mPassword.value)) {
            //
            isEmpty.value = false
            //call the authState listener
            authStateListener()
            if (isAuthSuccessful.equals(true)) {
                //call signIn
                signIn()
            } else {
                Log.i(TAG, "Not Signed in cos no auth")
                isAuthSuccessful.value = false
            }
        }else{
            isEmpty.value = true

        }
    }

    //signIn button triggers this
    private fun signIn(){

            //get the firebase instance and authenticate the user
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(mEmail.toString(), mPassword.toString())
                .addOnCompleteListener {task ->
                    if (task.isSuccessful){
                        //set task successful to true
                        isSignInSuccessful.value = true

                    }else{
                        //set task successful to false
                        isSignInSuccessful.value = false
                    }
                }


    }

    //set up the authState listener
    private fun authStateListener(){
        Log.i(TAG, "setupFirebaseAuth: started.");
        mAuthStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            //create object of user
            val user = firebaseAuth.currentUser
            //check if the user is not null
            if (user != null){
                //check if email is verified
                if (user.isEmailVerified){
                    //if not null, there is a valid user
                    Log.i(TAG, "AuthStateChanged: Signed in: " + user.uid)
                    //set isAuthSuccessful to true
                    isAuthSuccessful.value = true

                }else{
                    //set isAuthSuccessful to false
                    isAuthSuccessful.value = false
                }
            } else{
                Log.i(TAG, "AuthStateChanged: Signed out " )
                //set isAuthSuccessful to false
                isAuthSuccessful.value = false
            }
        }
    }

    //set up Auth listener
//    private fun isUserAuthenticated(user: FirebaseUser): Boolean{
//        Log.i("LoginViewModel","setupFirebaseAuth: Started")
//
//
//    }
    /**
     * @param string is the string to be tested
     * the method returns true if the string null or empty
     *
     */
    private fun isEmptyOrNull(string: String?): Boolean = string.isNullOrEmpty()

    override fun onCleared() {
        super.onCleared()
        //remove the authState listener
        if (mAuthStateListener != null){
            //remove the listener if not null. If null and you try to remove it, the app crashes
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener)
        }
    }
}