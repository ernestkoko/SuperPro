package com.ernestkoko.superpro.screens.login.login_reset

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ernestkoko.superpro.R
import com.ernestkoko.superpro.databinding.FragmentLoginResetBinding


/**
 * A simple [Fragment] subclass.
 */
class LoginResetFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentLoginResetBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login_reset, container, false)
        //get the application context
        val application = requireNotNull(this.activity).application
        //instantiate the viewModel factory
        val viewModelFactory = LoginResetViewModelFactory(application)
        //instantiate the viewModel
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(LoginResetViewModel::class.java)

        return binding.root
    }

}
