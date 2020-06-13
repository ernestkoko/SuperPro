package com.ernestkoko.superpro.screens.product_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ernestkoko.superpro.R
import com.ernestkoko.superpro.databinding.FragmentProductDetailsBinding

/**
 * A simple [Fragment] subclass.
 */
class ProductDetails : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       //use binding class to inflate view
        val binding: FragmentProductDetailsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_product_details, container, false
        )

        //get the application
        val application = requireNotNull(this.activity).application
        //create the viewModel factory
        val viewModelFactory = ProductDetailsViewModelFactory(application)
        //create the viewModel from the factory
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProductDetailsViewModel::class.java)
        // get the fragment to be aware of the vieModel data binding to the xml file
        binding.productDetailsViewModel= viewModel
        //set the lifecycle owner to this
        binding.setLifecycleOwner(this)

        return binding.root
    }

}
