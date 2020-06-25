package com.ernestkoko.superpro.screens.expiry_dates

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.ernestkoko.superpro.R
import com.ernestkoko.superpro.databinding.FragmentExpiryDatesBinding

/**
 * A simple [Fragment] subclass.
 */
class ExpiryDates : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //use data binding class
        val binding: FragmentExpiryDatesBinding =
            DataBindingUtil.inflate(inflater,R.layout.fragment_expiry_dates,container,false)
        //get the navController
        val navController = findNavController()
        //setup the app bar with the graph
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        //set the left pointing arrow
        binding.productToolBar.setupWithNavController(navController,appBarConfiguration)


        return binding.root
    }

}
