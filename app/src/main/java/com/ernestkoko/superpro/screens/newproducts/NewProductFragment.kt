package com.ernestkoko.superpro.screens.newproducts

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
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
        val binding: FragmentNewProductBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_new_product, container, false)
        //create the application
        val application = requireNotNull(this.activity).application
        //create the viewModel factory
        val viewModelFactory = NewProductViewModelFactory(application)
        //create the viewModel from the factory
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NewProductViewModel::class.java)
        //tell the fragment about the binding to viewModel on xml
        binding.newProductVieModel = viewModel
        binding.setLifecycleOwner(this)


        // set the prodName
        binding.productNameEdit.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //sets the name field
                viewModel.setName(s?.toString() ?: "")
                Log.i("NewProdonTextchanged", "The name is: " + s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })
        //set the quantity
        binding.productQuantityEdit.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                //set the value of the field
                viewModel.setProdQuantity(s.toString().toLong())

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        //set the manufacturer
        binding.productManufacturerEdit.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                viewModel.setProdManufacturer(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        //set expiry date
        binding.productExpiryDate.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                //set the product expiry date
               viewModel.setProdExpiryDate(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("Not yet implemented")
            }
        })






        return binding.root
    }

}
