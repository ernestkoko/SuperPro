package com.ernestkoko.superpro.screens.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.ernestkoko.superpro.R
import com.ernestkoko.superpro.databinding.FragmentSettingsBinding

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val binding: FragmentSettingsBinding=
           DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        // find the navController
        val navController = findNavController()
        //set the ap bar
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        //set the left pointing arrow
        binding.productToolBar.setupWithNavController(navController, appBarConfiguration)



        //bind the view to root
       return binding.root
    }

}
