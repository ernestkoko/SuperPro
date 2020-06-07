package com.ernestkoko.superpro.screens.products

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ernestkoko.superpro.R
import com.ernestkoko.superpro.adapter.ProductListAdapter
import com.ernestkoko.superpro.data.Product
import com.ernestkoko.superpro.data.ProductDatabase

//import androidx.databinding.DataBindingUtil
import com.ernestkoko.superpro.databinding.FragmentProductsBinding

/**
 * A simple [Fragment] subclass.
 */
class ProductsFragment : Fragment() {
   private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
       _binding = FragmentProductsBinding.inflate(inflater,container, false)


        //get the application context. requiredNotNull throws illegal arg exception if it is null
        val application = requireNotNull(this.activity).application
        val dataSource = ProductDatabase.getDataBase(application).productDao()
        //create the view model instance
        val viewModelFactory = ProductsViewModelFactory( dataSource,application)
        //create the view model
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ProductViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val adapter = ProductListAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.allProducts.observe(viewLifecycleOwner, Observer {
            it?.let {
                //listAdapter uses submitList()
               adapter.submitList(it)
            }

        })




       binding.addFab.setOnClickListener{view ->
           view.findNavController().navigate(R.id.action_productsFragment_to_newProductFragment)
       }

//        viewModel.allProducts.observe(viewLifecycleOwner, Observer { words ->
//            words?.let {
//                if (adapter != null) {
//                    adapter.setProducts(it)
//                }
//            }
//        })



        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

//this class compares what changes in recycler view and make a smooth update
class ProductsDiffCallback: DiffUtil.ItemCallback<Product>(){
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        //returns true if the two items have same id, false otherwise
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {

        //checks if the contents of an item has changed
        return  oldItem == newItem
    }

}
