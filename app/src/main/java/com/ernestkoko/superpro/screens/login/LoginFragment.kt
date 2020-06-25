package com.ernestkoko.superpro.screens.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ernestkoko.superpro.R
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

        return binding.root
    }

}
