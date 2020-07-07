package com.ernestkoko.superpro.screens.login.longin_registration

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ernestkoko.superpro.R
import com.ernestkoko.superpro.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass.
 */
class RegistrationFragment : Fragment() {
    private val TAG = "RegFragment"
    private lateinit var mBinding: FragmentRegistrationBinding
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mEmail: EditText
    private lateinit var mPassword1: EditText
    private lateinit var mPassword2: EditText
    private var mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)
        mEmail = mBinding.companyEmailEdit
        mPassword1 = mBinding.companyPasswordEdit1
        mPassword2 = mBinding.companyPasswordEdit2
        //set the life cycle owner
        mBinding.setLifecycleOwner(this)
        //get the application context
        // val application = requireNotNull(this.activity).application
        //instantiate the viewModel factory
        val viewModelFactory = RegistrationViewModelFactory()
        //instantiate the viewModel
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(RegistrationViewModel::class.java)
        Log.i("RegFragment", "RegFrag initialised")

        //connect the viewModel to binding class
        mBinding.regViewModel = viewModel
        //bind the progress bar
        mProgressBar = mBinding.progressBar

        //observe if any of the fields is empty
        viewModel.isFieldEmpty.observe(viewLifecycleOwner, Observer { fieldIsEmpty ->
            if (fieldIsEmpty) {
                //alert the user to fill out all the fields
                Toast.makeText(
                    context,
                    getString(R.string.fill_all_fields_meesage),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        //observe if the email is valid
        viewModel.validEmail.observe(viewLifecycleOwner, Observer { isEmailValid ->
            if (!isEmailValid) {
                //if email is not valid, tell the user to enter a valid email
                Toast.makeText(
                    context,
                    getString(R.string.enter_valid_email_message),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        //observe if the passwords match
        viewModel.arePasswordsMatch.observe(viewLifecycleOwner, Observer { passwordsAreMacth ->
            if (!passwordsAreMacth) {
                //alert the user that the passwords are not match
                Toast.makeText(
                    context,
                    getString(R.string.password_not_match_message),
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        //listen for when the reg is complete or not so we can show the progress bar
        viewModel.showProgressBar.observe(viewLifecycleOwner, Observer { showProgressBar ->
            if (showProgressBar) {
                //set the progress bar to be visible
                showProgBar()
            } else {
                //hide the progress bar
                hideProgBar()
            }
        })
        //listen for when the registration was successful or failed
        viewModel.isRegSuccessful.observe(viewLifecycleOwner, Observer { regIsSuccessful ->
            if (regIsSuccessful) {
                //if register is successful toast a message
                Toast.makeText(
                    context,
                    getString(R.string.registration_successful_message),
                    Toast.LENGTH_LONG
                )
                    .show()
                //navigate the user to the login fragment
                this.findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)

            } else {
                //alert the user that the registration failed
                Toast.makeText(
                    context,
                    getString(R.string.registration_failed_meesage),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        //create new user button
        mBinding.registrationButton.setOnClickListener {
            regNewUser()
        }



        return mBinding.root
    }

    private fun showProgBar() {
        //hide all other views apart from progress bar
        mBinding.progressBar.visibility = View.VISIBLE

    }

    private fun hideProgBar() {
        //show all other views and set the progress bar to GONE so it does not take space while
        //invisible
        //check if it is visible first
        if (mBinding.progressBar.visibility == View.VISIBLE) {
            mBinding.progressBar.visibility = View.INVISIBLE
        }


    }

    private fun regNewUser() {

        if (!mEmail.equals("") && !mPassword1.equals("") && !mPassword2.equals("")) {
            Log.i(TAG, "Fields: not Empty")
            if (mPassword1 == mPassword1) {
                Log.i(TAG, "Password: match")
                mBinding.progressBar.visibility = View.VISIBLE

                mAuth.createUserWithEmailAndPassword(mEmail.text.toString(), mPassword1.text.toString())
                    .addOnCompleteListener {
                        if (it.isComplete){
                            Log.i(TAG, "Task is complete")
                            if (it!!.isSuccessful) {
                                Log.i(TAG, "Email Created: Successful")
                                //send verification mail
                                sendEmailVerification()
                                mBinding.progressBar.visibility = View.INVISIBLE
                            } else {
                                Log.i(TAG, "Email Created: Not Successful")
                                Log.i(TAG,"Error: " + it.exception!!.localizedMessage)
                                mBinding.progressBar.visibility = View.INVISIBLE
                            }
                            //
                        }else{
                            Log.i(TAG, "Task not complete")
                        }
                        mBinding.progressBar.visibility = View.INVISIBLE

                    }

            } else {
                Log.i(TAG, "Password: Not match")
                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
            }


        } else {
            Log.i(TAG, "Fields: empty")
            Toast.makeText(context, "Fill all the fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun sendEmailVerification() {
        val user = mAuth.currentUser
        if (user != null){
            Log.i(TAG, "User: Not null")
            //send verification mail
            user.sendEmailVerification()
            //log out the user
            mAuth.signOut()
        }else{
            Log.i(TAG, "User: null")
            //log out the user
            mAuth.signOut()

        }
    }

    /**
     * @param s1 is the first string
     * @param s2 is the second string
     * returns true if the both strings are equal or match
     */
    fun doStringsMatch(s1: String, s2: String): Boolean {
        //returns true if the two passwords are equal

        return s1 == s2

    }
}
