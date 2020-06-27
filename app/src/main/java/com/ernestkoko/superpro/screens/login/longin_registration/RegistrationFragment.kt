package com.ernestkoko.superpro.screens.login.longin_registration

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ernestkoko.superpro.R
import com.ernestkoko.superpro.databinding.FragmentRegistrationBinding

/**
 * A simple [Fragment] subclass.
 */
class RegistrationFragment : Fragment() {
    private lateinit var mBinding: FragmentRegistrationBinding
    private lateinit var mProgressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)
        //set the life cycle owner
        mBinding.setLifecycleOwner(this)
        //get the application context
        val application = requireNotNull(this.activity).application
        //instantiate the viewModel factory
        val viewModelFactory = RegistrationViewModelFactory(application)
        //instantiate the viewModel
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(RegistrationViewModel::class.java)

        //connect the viewModel to binding class
        mBinding.regViewModel = viewModel

        //observe if any of the fields is empty
        viewModel.isFieldEmpty.observe(viewLifecycleOwner, Observer { fieldIsEmpty ->
            if (fieldIsEmpty) {
                //alert the user to fill out all the fields
                Toast.makeText(application, getString(R.string.fill_all_fields_meesage), Toast.LENGTH_LONG).show()
            }
        })
        //observe if the email is valid
        viewModel.validEmail.observe(viewLifecycleOwner, Observer { isEmailValid ->
            if (!isEmailValid) {
                //if email is not valid, tell the user to enter a valid email
                Toast.makeText(application, getString(R.string.enter_valid_email_message), Toast.LENGTH_LONG).show()
            }
        })
        //observe if the passwords match
        viewModel.arePasswordsMatch.observe(viewLifecycleOwner, Observer { passwordsAreMacth ->
            if (!passwordsAreMacth) {
                //alert the user that the passwords are not match
                Toast.makeText(
                    application,
                    getString(R.string.password_not_match_message),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
//        val inflater1 = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        mBinding.
//
//        //the progress bar
//        val builder = AlertDialog.Builder(requireContext())


        //listen for when the reg is complete or not so we can show the progress bar
        viewModel.isRegComplete.observe(viewLifecycleOwner, Observer { isRegComplete ->
            if (!isRegComplete) {
                //initialise the progress bar
                mProgressBar = mBinding.progressBar

                //set the progress bar to be visible
                hideOtherViews()


            } else {
                //hide the progress bar
                showOtherViews()
            }
        })
        //listen for when the registration was successful or failed
        viewModel.isRegSuccessful.observe(viewLifecycleOwner, Observer { regIsSuccessful ->
            if (regIsSuccessful) {
                //if register is successful toast a message
                Toast.makeText(application, getString(R.string.registration_successful_message), Toast.LENGTH_LONG)
                    .show()
                //navigate the user to the login fragment
                this.findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)

            } else {
                //alert the user that the registration failed
                Toast.makeText(application, getString(R.string.registration_failed_meesage), Toast.LENGTH_LONG).show()
            }
        })



        return mBinding.root
    }

    private fun hideOtherViews(){
        //hide all other views apart from progress bar
        mBinding.progressBar.visibility = View.VISIBLE
        mBinding.companyNameEdit.visibility = View.INVISIBLE
        mBinding.companyEmailEdit.visibility = View.INVISIBLE
        mBinding.companyPhoneNumberEdit.visibility = View.INVISIBLE
        mBinding.companyAddressEdit.visibility = View.INVISIBLE
        mBinding.companyPasswordEdit1.visibility = View.INVISIBLE
        mBinding.companyPasswordEdit2.visibility = View.INVISIBLE
        mBinding.registrationButton.visibility = View.INVISIBLE
    }
    private fun showOtherViews(){
        //show all other views and set the progress bar to GONE so it does not take space while
        //invisible
        mBinding.progressBar.visibility = View.GONE
        mBinding.companyNameEdit.visibility = View.VISIBLE
        mBinding.companyEmailEdit.visibility = View.VISIBLE
        mBinding.companyPhoneNumberEdit.visibility = View.VISIBLE
        mBinding.companyAddressEdit.visibility = View.VISIBLE
        mBinding.companyPasswordEdit1.visibility = View.VISIBLE
        mBinding.companyPasswordEdit2.visibility = View.VISIBLE
        mBinding.registrationButton.visibility = View.VISIBLE

    }

}
