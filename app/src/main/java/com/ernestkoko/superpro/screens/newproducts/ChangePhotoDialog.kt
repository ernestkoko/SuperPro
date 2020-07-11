package com.ernestkoko.superpro.screens.newproducts

import android.app.Activity
import android.content.Context
import androidx.fragment.app.DialogFragment
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.ernestkoko.superpro.R
import com.ernestkoko.superpro.databinding.DialogChangePhotoBinding
import java.lang.ClassCastException

class ChangePhotoDialog: DialogFragment() {
    private val CAMERA_REQUEST_CODE = 2001
    private val PICK_FILE_REQUEST_CODE = 2002
    private val TAG = "ChangePhotoDialog"
    private lateinit var mBinding: DialogChangePhotoBinding
    private lateinit var mOnPhotoReceived: OnPhotoReceivedListener

    interface OnPhotoReceivedListener{
        fun getImagePath(imagePath: Uri)
        fun getImageBitmap(bitmap: Bitmap)

    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       mBinding = DataBindingUtil.inflate(inflater,R.layout.dialog_change_photo, container, false)

        //Initialize the textview for choosing an image from memory
        mBinding.dialogChoosePhoto.setOnClickListener {
            Log.i(TAG, "Onlcik: Accessing phone memory")
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_FILE_REQUEST_CODE)
        }
        //initialising the textview for snapping pic with camera
        mBinding.dialogOpenCamera.setOnClickListener {
            Log.i(TAG,"onClick: Taking photo")
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
        }


        return mBinding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /*
        Results when selecting new image from phone memory
         */
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val selectedImageUri = data?.data
            Log.i(TAG, "onActivityResult: image: " + selectedImageUri)
            //send the bitmap and fragment to the interface
            mOnPhotoReceived.getImagePath(selectedImageUri!!)
            //dismiss the dialog
            dialog!!.dismiss()

        }else if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Log.d(TAG, "onActivityResult: done taking a photo.")
            val bitmap = data!!.extras?.get("data") as Bitmap
            mOnPhotoReceived.getImageBitmap(bitmap)
            dialog?.dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mOnPhotoReceived =  targetFragment as OnPhotoReceivedListener
        }catch (e: ClassCastException){
            Log.e(TAG, "onAttach: ClassCastException "+ e.cause )
        }

    }
}