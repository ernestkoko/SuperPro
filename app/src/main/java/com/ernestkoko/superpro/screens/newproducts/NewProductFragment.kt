package com.ernestkoko.superpro.screens.newproducts

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
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
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ernestkoko.superpro.R
import com.ernestkoko.superpro.data.Product
import com.ernestkoko.superpro.databinding.FragmentNewProductBinding
import com.squareup.picasso.Picasso
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

/**
 * A simple [Fragment] subclass.
 */
class NewProductFragment : Fragment() {
    //    private var _binding: FragmentNewProductBinding? = null
//    private val binding get() = _binding!!
    private lateinit var binding: FragmentNewProductBinding

    //instantiate the view model
    private lateinit var viewModel: NewProductViewModel

    //photo path
    private lateinit var currentPhotoPath: String
    private val SAVE_IMAGE_REQUEST_CODE: Int = 100
    private val PICK_IMAGE_REQUEST_CODE: Int = 101
    private val CAMERA_PERMISSION_REQUEST_CODE: Int = 102


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_new_product, container, false)

        //find the navController and set up the tool bar
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.productToolBar
            .setupWithNavController(navController, appBarConfiguration)

        //create the application
        val application = requireNotNull(this.activity).application
        //create the viewModel factory
        val viewModelFactory = NewProductViewModelFactory(application)
        //create the viewModel from the factory
        viewModel = ViewModelProvider(this, viewModelFactory)
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
            prepToTakePhoto()
            //pick image from phone
//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
//            Log.i("Intent: ", "Image picked")

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
                        val month1 = (month + 1).toString() + "/"

                        viewModel.setDateToEditText(format + day + month1 + year.toString())
                    }

                }, year, month, day
            )
            dpd.show()

        }






        return binding.root
    }

    // called after startActivityFor result is called to get the result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //check for the request code
        if (resultCode == Activity.RESULT_OK) {
            //check if the result is ok
            if (requestCode == PICK_IMAGE_REQUEST_CODE) {
                //get the uri of the image
                var imageUrl: Uri? = data?.data
                // val imageBitMap = data!!.extras!!.get("data") as Bitmap
                //display the side indicator to show where the image was got from
                Picasso.get().setIndicatorsEnabled(true)
                //load the image selected into the image view
                Picasso.get().load(imageUrl).fit().centerCrop().into(binding.newProductImage)
                Toast.makeText(context, "Image Saved!", Toast.LENGTH_LONG).show()
            } else if (requestCode == SAVE_IMAGE_REQUEST_CODE) {
                //get the image uri
                var imageUrl = data!!.data
                //display the image on the image view
                Picasso.get().load(imageUrl).fit().into(binding.newProductImage)
                //tell the user it has been saved
                Toast.makeText(context, "Image Saved", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, "Image not gotten!", Toast.LENGTH_LONG).show()
        }
    }

    //create a file with a unique time stamp
    private fun createImageFile(): File {
        //generates a unique filename with date
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        //get access to the directory where we can write pictures
        val storageDir: File? =
            requireContext()!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        //create a file with name, file extension and storage location
        return File.createTempFile("SuperPro${timeStamp}", ".jpg", storageDir).apply {
            //this returns the path of the saved file
            currentPhotoPath = absolutePath
        }

    }

    private fun takePhotoWithCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
                takePictureIntent -> takePictureIntent.resolveActivity(requireContext()!!.packageManager)
            if (takePictureIntent == null) {
                Toast.makeText(context, "Unable to save Photo", Toast.LENGTH_LONG).show()

            } else {
                //means the intent returns photo
                val photoFile = createImageFile()
                photoFile?.also {
                    val photoURI = FileProvider.getUriForFile(
                        requireContext()!!,
                        "com.ernestkoko.superpro.fileprovider", photoFile
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile)
                    startActivityForResult(takePictureIntent, SAVE_IMAGE_REQUEST_CODE)
                }
            }
        }
    }

    //pick an image from gallery
    private fun pickImageFromGallery() {
        //pick image from phone
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
//        intent.also {
//            //check if there is place to save the photo
//                pickImageIntent ->
//            pickImageIntent.resolveActivity(requireContext()!!.packageManager)
//            if (pickImageIntent == null) {
//                //if no place to save the photo
//                Toast.makeText(context, "Unable to save the photo", Toast.LENGTH_LONG).show()
//            } else {
//                val imageFile = createImageFile()
//                imageFile?.also {
//                    val imageURI = FileProvider.getUriForFile(
//                        requireContext()!!,
//                        "com.ernestkoko.superpro.fileprovider", imageFile
//                    )
//                    //save the image to the image file
//                    pickImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFile)
//                    startActivityForResult(pickImageIntent, PICK_IMAGE_REQUEST_CODE)
//                }
//            }
//        }
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)

    }

    //fun prepare to take photo
    private fun prepToTakePhoto(){
        //check if permission is granted
        if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA )
            == PackageManager.PERMISSION_GRANTED){
            //if granted, take photo
            takePhotoWithCamera()
        } else{
            //else ask for permission
            val permissionRequest = arrayOf(android.Manifest.permission.CAMERA)
            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //check for the request code
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE){
            //CHECK IF PERMISSION GRANTED
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.isNotEmpty()){
                //permission granted, take photo
                takePhotoWithCamera()
            }else{
                //make a toast to the user that it can not take pic without permission
                Toast.makeText(requireContext(),
                    "Unable to take photo without permission", Toast.LENGTH_LONG).show()
            }
        }
    }
}
