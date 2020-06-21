package com.ernestkoko.superpro.screens.newproducts

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ernestkoko.superpro.R
import com.ernestkoko.superpro.data.Product
import com.ernestkoko.superpro.databinding.FragmentNewProductBinding
import com.squareup.picasso.Picasso
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class NewProductFragment : Fragment() {
    //    private var _binding: FragmentNewProductBinding? = null
//    private val binding get() = _binding!!
    private lateinit var binding: FragmentNewProductBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
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
        binding.productQuantityEdit.addTextChangedListener(object : TextWatcher {
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
        binding.productManufacturerEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.setProdManufacturer(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        //set image
        val image = binding.newProductImage
        image.setOnClickListener {
            //pick image from phone
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 1)
            Log.i("Intent: ", "Image picked")

            // Picasso.get().load(R.drawable.ic_checked).into(binding.newProductImage)
        }
//
//        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//        Picasso.get().load(R.drawable.ic_product_image).into(image)
//        image.setImageResource(R.drawable.ic_add)
//        Log.d("Picasso", "Loaded image")

        //date
        val date = Date()
        //date picker dialogue
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        binding.buttonPickDate.setOnClickListener {

            val dpd = DatePickerDialog(
                this.requireContext(),
                object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(
                        view: DatePicker?, year: Int, month: Int, dayOfMonth: Int
                    ) {
                        // c.time = date
                        viewModel.setProdExpiryDate(
                            dayOfMonth,
                            month, year
                        )
                        val format = ("DD/MM/YYYY: ")
                        val day = (dayOfMonth).toString() + "/"
                        val month1 = (month + 1).toString() +"/"


                        viewModel.setDateToEditText(format + day + month1 + year.toString() )


                    }

                }, year, month, day
            )
            dpd.show()

        }






        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

       var imageUrl  = data?.data

        if (requestCode == 1) {
            Toast.makeText(context, "Image gotten!",Toast.LENGTH_LONG).show()
            //get the image with picasso and display it on the image view
            Picasso.get().setIndicatorsEnabled(true)
            Picasso.get().load(imageUrl).fit().centerCrop().into(binding.newProductImage)
        } else{
            Toast.makeText(context, "Image not gotten!",Toast.LENGTH_LONG).show()
        }
    }
}
