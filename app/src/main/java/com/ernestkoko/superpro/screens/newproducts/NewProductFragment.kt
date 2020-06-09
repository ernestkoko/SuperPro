package com.ernestkoko.superpro.screens.newproducts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ernestkoko.superpro.R
import com.ernestkoko.superpro.data.Product
import com.ernestkoko.superpro.databinding.FragmentNewProductBinding

/**
 * A simple [Fragment] subclass.
 */
class NewProductFragment : Fragment() {
//    private var _binding: FragmentNewProductBinding? = null
//    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentNewProductBinding = DataBindingUtil.
        inflate(layoutInflater,R.layout.fragment_new_product,container, false)
        //create the application
        val application = requireNotNull(this.activity).application
        //create the viewModel factory
        val viewModelFactory = NewProductViewModelFactory(application)
        //create the viewModel from the factory
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NewProductViewModel::class.java)

        binding.addButton.setOnClickListener{
         //  val product1 = Product(3, "Efe","ER")
//            product.prodManufacturer
           val name = binding.productNameEdit.text.toString()
           var manufacturer = binding.productManufacturerEdit.text.toString()


           // viewModel.insert(Product(1,"Mango", "Farm"))
            viewModel.insert(Product(0, name, manufacturer))
            Log.i("NewProduct", "New Product with name ${name} inserted")
        }




        return binding.root
    }

}
