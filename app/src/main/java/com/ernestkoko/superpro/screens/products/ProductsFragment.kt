package com.ernestkoko.superpro.screens.products

import android.app.Application
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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ernestkoko.superpro.R
import com.ernestkoko.superpro.adapter.ProductClickListener
import com.ernestkoko.superpro.adapter.ProductListAdapter
import com.ernestkoko.superpro.databinding.FragmentProductsBinding


/**
 * A simple [Fragment] subclass.
 */
class ProductsFragment : Fragment() {
//   private var _binding: FragmentProductsBinding? = null
//    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
       val binding: FragmentProductsBinding =
           DataBindingUtil.inflate(inflater,R.layout.fragment_products,container, false)


        //get the application context. requiredNotNull throws illegal arg exception if it is null
        val application = requireNotNull(this.activity).application
       // val dataSource = ProductDatabase.getDataBase(application).productDao()
        //create the view model instance
        val viewModelFactory = ProductsViewModelFactory(application)
        //create the view model
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ProductViewModel::class.java)
        binding.productsViewModel = viewModel
        binding.setLifecycleOwner(this)

        binding.recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
       //tell the adapter about the id of the view that was clicked
        val adapter = ProductListAdapter(ProductClickListener {
            productId -> viewModel.onProductItemClicked(productId)
        })
        binding.recyclerView.adapter = adapter
       // val layoutManager = LinearLayoutManager(activity)
        viewModel.navigateToProductDetails.observe(viewLifecycleOwner, Observer {product ->
            product?.let {
                //navigate to Product details screen where the details will be shown and can be
                // edited
                //Navigate by passing the id of the product as args
             this.findNavController().navigate(ProductsFragmentDirections
                 .actionProductsFragmentToProductDetails(product))
                //call the navigated method to set the value of the id to null
                viewModel.onProductDetailsNavigated()
            }

        })




        viewModel.allProducts1.observe(viewLifecycleOwner, Observer {
            it?.let {
                //listAdapter uses submitList()
                //submit the list of Products to the adapter
               adapter.submitList(it)

               // Log.i("ProductsFrag", it.toString())
            }

        })




       binding.addFab.setOnClickListener{view ->
           // navigate to the new product fragment where new product can be added
           view.findNavController().navigate(R.id.action_productsFragment_to_newProductFragment)
       }




        return binding.root

    }


}


