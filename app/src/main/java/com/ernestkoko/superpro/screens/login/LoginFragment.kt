package com.ernestkoko.superpro.screens.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ernestkoko.superpro.R
import com.ernestkoko.superpro.ResendVerificationDialog
import com.ernestkoko.superpro.databinding.FragmentLoginBinding


/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

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
        binding.registerButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        //navigate to the password reset fragment when the user clicks on Reset button
        binding.resetPasswordButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_loginFragment_to_loginResetFragment)
        }

        //listen for when the input values are empty
        viewModel.isEmpty.observe(viewLifecycleOwner, Observer { isEmpty ->
            if (isEmpty) {
                //toast a message to the user to fill all the fields
                Toast.makeText(
                    application,
                    getString(R.string.fill_all_fields_meesage),
                    Toast.LENGTH_LONG
                ).show()
            }

        })
        //listen for when the task if successful or fail
        viewModel.isSignInSuccessful.observe(viewLifecycleOwner, Observer { isSignInSuccessful ->
            if (isSignInSuccessful) {
                //log in the user
                this.findNavController().navigate(R.id.action_global_products_fragment)
            } else {
                //toast a message to the user that login failed
                Toast.makeText(
                    application,
                    "Email is not verified\nCheck you Inbox",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        //listen for when the auth is fails
        viewModel.isEmailVerified.observe(viewLifecycleOwner, Observer {
            if (!it) {
                //toast a message to the user that login failed
                Toast.makeText(
                    application, "Email is not verified\nCheck you Inbox",
                    Toast.LENGTH_LONG
                ).show()

            } else{
                //take the user to product fragment if the email is verified
                this.findNavController().navigate(R.id.action_global_products_fragment)
            }
        })
        //listen for when the resend verification email is clicked
        viewModel.resendEmail.observe(viewLifecycleOwner, Observer {
            if (it){
                //navigate to resend verification email dialog fragment
                val dialog = ResendVerificationDialog()
                dialog.show(parentFragmentManager,"dialog_resend_email_verification")

            }
        })

        return binding.root
    }


}
