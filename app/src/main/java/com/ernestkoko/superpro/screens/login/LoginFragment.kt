package com.ernestkoko.superpro.screens.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ernestkoko.superpro.R
import com.ernestkoko.superpro.databinding.FragmentLoginBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth


/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {
    companion object {
        const val TAG = "LoginFrag"
        const val SIGN_IN_REQUEST_CODE = 1000
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        //get the application context
        val application = requireNotNull(this.activity).application
        //instantiate the viewModel factory
        val viewModelFactory = LoginViewModelFactory(application)
        //instantiate the viewModel
        val viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
        //
        binding.setLifecycleOwner(this)
        binding.logInViewModel = viewModel

        //navigate to registration fragment when the Register button is click
        binding.signInButton.setOnClickListener { view ->
            launchSignInFlow()
        }





        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // User successfully signed in
                val auth = FirebaseAuth.getInstance()
                Toast.makeText(context,"${auth.currentUser?.displayName}," +
                        " You have successfully been signed in!", Toast.LENGTH_LONG ).show()
                Log.i(TAG, "Successfully signed in user ${auth.currentUser?.displayName}!")
                findNavController().navigate(R.id.action_global_products_fragment)

            } else {
               // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                Log.i(TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }
    }

    private fun launchSignInFlow() {
        // Give users the option to sign in / register with their email or Google account.
        // If users choose to register with their email,
        // they will need to create a password as well.
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()

            // This is where you can provide more ways for users to register and
            // sign in.
        )

        // Create and launch sign-in intent.
        // We listen to the response of this activity with the
        // SIGN_IN_REQUEST_CODE
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            LoginFragment.SIGN_IN_REQUEST_CODE
        )
    }

}
