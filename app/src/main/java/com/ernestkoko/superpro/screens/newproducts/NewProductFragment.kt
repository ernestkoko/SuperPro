package com.ernestkoko.superpro.screens.newproducts

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.ernestkoko.superpro.R
import com.ernestkoko.superpro.databinding.FragmentNewProductBinding
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class NewProductFragment : Fragment(), ChangePhotoDialog.OnPhotoReceivedListener {
    //    private var _binding: FragmentNewProductBinding? = null
//    private val binding get() = _binding!!
    private val TAG = "NewProdFrag"
    private lateinit var binding: FragmentNewProductBinding
    private var mStoragePermissions: Boolean = false
    private val PERM_REQUEST_CODE = 200
    private var mSelectedImageBitMap: Bitmap? = null
    private var mSelectedImageUri: Uri? = null

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
        binding.newProductImage.setOnClickListener {
            if (mStoragePermissions) {
                val dialog = ChangePhotoDialog()
                dialog.setTargetFragment(this, 300)
                dialog.show(parentFragmentManager, getString(R.string.change_photo_dialog))
            } else {
                verifyStoragePermission()
            }
        }

        binding.buttonPickDate.setOnClickListener {
            //date
            val date = Date()
            //date picker dialogue
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

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

                        val day = (dayOfMonth).toString() + "/"
                        val month1 = (month + 1).toString() + "/"

                        viewModel.setDateToEditText(day + month1 + year.toString())
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
            //toast a message to the user that image is not gotten
            Toast.makeText(context, "Image not gotten!", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * method for verifying if the user has granted the required permissions
     */
    private fun verifyStoragePermission() {
        Log.i("NewProdFrag", "verifyStoragePermission called")
        //arrays of Strings of permission required
        val permissions = arrayOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
        )
        // check if the Phone's SDK is equal to or greater than version M
        //because from M and greater requires user to grant permission when it is needed and not
        //the app is being installed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //check if permissions have been granted
            if (requireContext().checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_GRANTED
                && requireContext().checkSelfPermission(permissions[1]) == PackageManager.PERMISSION_GRANTED
                && requireContext().checkSelfPermission(permissions[2]) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.i(TAG, "Permissions granted: Setting mStoragePermissions to true")
                //if granted
                mStoragePermissions = true

            } else {
                Log.i("NewProdFrag", "VerifyPermissions: Asking user for permission")
                //if not granted, request for permissions
                ActivityCompat.requestPermissions(requireActivity(), permissions, PERM_REQUEST_CODE)

            }
        }

    }

    /**
     * method for getting the Uri of the image from the callback class
     * @param imagePath is the given image path,Uri
     */
    override fun getImagePath(imagePath: Uri) {
        //check if the uri is no empty
        if (imagePath.toString() != "") {
            //use Picasso to display the image on the imageView
            Picasso.get().load(imagePath).fit().centerCrop().into(binding.newProductImage)
            //if true, set the bitmap to null
            mSelectedImageBitMap = null
            //set the uri to the given uri
            mSelectedImageUri = imagePath
            Log.i(TAG, "getImagePath: got the image Uri $mSelectedImageUri")


        }
    }

    /**
     * used for getting bitmap from the callback class
     * @param bitmap is the bitmap gotten from the callback class
     */
    override fun getImageBitmap(bitmap: Bitmap) {

        //check if bitmap is not null
        if (bitmap != null) {
            //convert the bitMap to uri so Picasso can display it
            val uri = convertBitmapToUri(bitmap)
            //display the image with Picasso on the imageView
            Picasso.get().load(uri).fit().centerCrop().into(binding.newProductImage)

            //if not null set the uri to null
            mSelectedImageUri = null
            //set the bitmap to the given bitmap
            mSelectedImageBitMap = bitmap
            Log.i(TAG, "getImageBitmap: got the image bitmap $mSelectedImageBitMap")

        }
    }

    /**
     * converts bitmap to uri and return the uri
     * @param bitmap is the input bitmap
     */
    private fun convertBitmapToUri(bitmap: Bitmap): Uri {
        val file = File(requireContext().cacheDir, "ImageFile")
        file.delete() // delete in case it exists
        file.createNewFile()
        val outputStream = file.outputStream()
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        outputStream.write(byteArray)
        outputStream.flush()
        outputStream.close()
        byteArrayOutputStream.close()
        val uri = file.toURI().toString()
        //return the uri
        return Uri.parse(uri)


    }
}
