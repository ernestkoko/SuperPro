package com.ernestkoko.superpro

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ResendVerificationDialogViewModel : ViewModel() {
    private val TAG = "ResendVeriViewModel"
    //get the instance of the firebase authentication
    private val auth = FirebaseAuth.getInstance()


    //email to be entered
    val email = MutableLiveData<String>()

    //password to be entered
    val password = MutableLiveData<String>()

    //live data of empty fields
    val isFieldEmpty = MutableLiveData<Boolean>()

    //live data of whether email was sent
    val wasEmailSent = MutableLiveData<Boolean>()
    //live data of whether to show dialog or not
    val showDialog = MutableLiveData<Boolean>()
    init {
        showDialog.value = true
    }


    //authenticate so email verification can be sent again
    fun authenticateAndResendEmail() {
        Log.i(TAG, "authAndResendEmail: Called")
        //check if the email and password are empty
        if (!email.value.isNullOrEmpty() && !password.value.isNullOrEmpty()){
            Log.i(TAG, "Fields are not empty")
            //set isFieldEmpty to false
            isFieldEmpty.value = false
            //get the authCredential for email
            val credential =
                EmailAuthProvider.getCredential(email.value.toString(), password.value.toString())
            //use the instance of the firebase auth to sign in with the credential
            auth.signInWithCredential(credential).addOnCompleteListener {
                //check if the task is successful
                if (it.isSuccessful){
                    Log.i(TAG,"OnComplete: Re-Authenticate success")
                    //send the verification
                    senVerification()
                    //sign out
                    auth.signOut()
                    //dismiss the dialog from fragment
                    showDialog.value = false
                }else{
                    //task failed
                    Log.i(TAG,"OnComplete: Re-Authenticate failed")
                    //dismiss the dialog
                    showDialog.value = false


                }


            }

        } else{
            Log.i(TAG, "Fields are empty")
            //notify the user to fill all the fields
            isFieldEmpty.value = true
        }


    }

    //method for sending verification
    private fun senVerification() {
        Log.i(TAG, "sendVerificationEmail: called")
        //get the user of the firebase
        val user = auth.currentUser
        //check if user is not null before sending verification
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener { task ->
                //check if the task is successful
                if (task.isSuccessful) {
                    Log.i(TAG, "Verification Email sent successfully")
                    //notify the user the verification was sent
                    wasEmailSent.value = true

                } else {
                    Log.i(TAG, "Verification email was not sent")
                    //notify the user that the verification was not sent
                    wasEmailSent.value = false

                }

            }
        }
    }
}