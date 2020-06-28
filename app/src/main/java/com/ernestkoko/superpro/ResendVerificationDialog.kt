package com.ernestkoko.superpro

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ernestkoko.superpro.databinding.ResendVerificationDialogFragmentBinding

class ResendVerificationDialog : DialogFragment() {
    private lateinit var mBinding: ResendVerificationDialogFragmentBinding
    private val TAG = "ResendDialFragment"

    companion object {
        fun newInstance() = ResendVerificationDialog()
    }

    private lateinit var viewModel: ResendVerificationDialogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil
            .inflate(inflater,R.layout.resend_verification_dialog_fragment,container,false)
        mBinding.setLifecycleOwner(this)

       // initialise the viewModel
        viewModel = ViewModelProvider(this).get(ResendVerificationDialogViewModel::class.java)

        //let the binding know about the viewmodel
        mBinding.resendVerificationViewModel = viewModel

        //listen for when the fields are empty
        viewModel.isFieldEmpty.observe(viewLifecycleOwner, Observer {
            if (it){
                //toast a message to the user to fill out fields
                Toast.makeText(context, "Fields are empty", Toast.LENGTH_LONG).show()
            }
        })
        //listens for when the email is sent
        viewModel.wasEmailSent.observe(viewLifecycleOwner, Observer {
            if(it){
                //toast a message to the user that the email has been sent
                Toast.makeText(context,"Email was sent Successfully", Toast.LENGTH_LONG).show()
                //navigate to log in fragment

            }
        })

        //listen for when to show dialog
        viewModel.showDialog.observe(viewLifecycleOwner, Observer {
            if(it){
                //show dialog
                dialog!!.show()
            } else{
                //dismiss the dialog
                dialog!!.dismiss()
            }
        })

       return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =ViewModelProvider(this).get(ResendVerificationDialogViewModel::class.java)

    }

}