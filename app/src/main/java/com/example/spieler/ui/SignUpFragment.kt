package com.example.spieler.ui

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.spieler.R
import com.example.spieler.databinding.CustomProgressDialogBinding
import com.example.spieler.databinding.FragmentSignUpBinding
import com.example.spieler.model.SignUpRequestBody
import com.example.spieler.repository.Repository
import com.example.spieler.util.Constants
import com.example.spieler.viewmodel.SignUpViewModel
import com.example.spieler.viewmodelfactory.SignUpViewModelFactory


class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel
    private lateinit var viewModelFactory: SignUpViewModelFactory
    private lateinit var sessionSharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var playerNameDialog: AlertDialog.Builder
    private lateinit var dialog: AlertDialog

    private var currUri: Uri? = null
    private var firstName = ""
    private var emailText = ""
    private var passwordText = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up,container, false)

        sessionSharedPreferences = context?.getSharedPreferences(Constants.USER_SESSION, Context.MODE_PRIVATE)!!


        binding.loginNavigationTxt.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.navToLogin())
        }

        //To fetch the profile pic from gallery
        val profilePicResponse = registerForActivityResult(ActivityResultContracts.GetContent()){
            if(it != null){
                currUri = it
                Glide.with(this)
                    .load(it)
                    .circleCrop()
                    .into(binding.userProfilePic)
            }
            else{
                Toast.makeText(requireContext(), "Error in fetching image", Toast.LENGTH_SHORT).show()
            }
        }

        //Initialisation
        val repository = Repository()

        viewModelFactory = SignUpViewModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(),
            viewModelFactory)[SignUpViewModel::class.java]

        //Sign up the user
        binding.signupBtn.setOnClickListener {
            firstName = binding.signupFnameETv.text.toString().trim()
            emailText = binding.signupEmailEtv.text.toString().trim()
            passwordText = binding.signupPasswordEtv.text.toString().trim()
            val rePasswordText = binding.signupRePasswordEtv.text.toString().trim()
            if((firstName.isEmpty()
                        || emailText.isEmpty() || passwordText.isEmpty())){
                Toast.makeText(context,
                    "Can't leave any field empty", Toast.LENGTH_SHORT).show()
            }
            else if(passwordText != rePasswordText){
                Toast.makeText(context,
                    "Passwords don't match", Toast.LENGTH_SHORT).show()
                binding.signupPasswordEtv.text = null
                binding.signupRePasswordEtv.text = null
            }
            else if(!emailText.contains("@")){
                Toast.makeText(context,
                    "Not a valid email!", Toast.LENGTH_SHORT).show()
            }
            else{
                createDialog("Announcing your arrival...")
                if(currUri != null){
                    viewModel.uploadImageToFirebase(currUri, emailText, firstName, emailText, passwordText)
                    Log.d("Profile pic Operation", currUri.toString())

                }
                else{
                    val user = SignUpRequestBody(firstName, emailText, passwordText, Constants.DEFAULT_PIC)
                    viewModel.registerUser(user)
                    Log.d("Profile pic Operation", "defaultDP")
                }
            }
        }

        viewModel.currentUser.observe(viewLifecycleOwner){ response ->
            if(response.isSuccessful && response != null){
                val action = SignUpFragmentDirections.navToLogin()
                action.email = emailText
                Log.d("User Creation Operation", "Success")
                findNavController().navigate(action)
                dialog.dismiss()
            }
            else{
                dialog.dismiss()
                Log.d("User Creation Operation", "FAIL")
                Toast.makeText(context, "There was some error", Toast.LENGTH_SHORT).show()
            }
        }

        //Navigate the user to gallery
        binding.addProfilePic.setOnClickListener {
            profilePicResponse.launch("image/*")
        }

        return binding.root
    }

    private fun createDialog(message: String){
        playerNameDialog = AlertDialog.Builder(requireContext())
        val newBinding: CustomProgressDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.custom_progress_dialog, null, false)
        playerNameDialog.setView(newBinding.root)
        dialog = playerNameDialog.create()
        newBinding.message.text = message
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

}