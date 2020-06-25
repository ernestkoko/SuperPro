package com.ernestkoko.superpro.screens.product_details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.ernestkoko.superpro.R
import com.ernestkoko.superpro.databinding.FragmentProductDetailsBinding
import androidx.navigation.fragment.navArgs as navArgs1

/**
 * A simple [Fragment] subclass.
 */
class ProductDetails : Fragment() {
    //val args: ProductDetailsArgs by navArgs1()

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
        //get product with the aid of the id value from bundle
        val arguments = ProductDetailsArgs.fromBundle(arguments!!)
        //create the viewModel factory
        val viewModelFactory = ProductDetailsViewModelFactory(arguments.productId, application)
        //create the viewModel from the factory
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProductDetailsViewModel::class.java)
        // get the fragment to be aware of the vieModel data binding to the xml file
        binding.productDetailsViewModel= viewModel
        //set the lifecycle owner to this
        binding.setLifecycleOwner(this)

        //observer
//        viewModel.product1.observe(viewLifecycleOwner, Observer {
//
//           // binding.productNameTextView.setText(it.productName)
//            binding.productManuTextView.setText(it.prodManufacturer)
//            binding.productInventoryDate.setText(it.productExpiryDate.toString())
//           // Log.i("ProductDetails", "Name is: " + it.productName)
//        })




        return binding.root
    }



}
