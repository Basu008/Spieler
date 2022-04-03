package com.example.spieler.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.spieler.R
import com.example.spieler.databinding.CustomProgressDialogBinding
import com.example.spieler.databinding.FragmentLoginBinding
import com.example.spieler.model.LoginRequestBody
import com.example.spieler.repository.Repository
import com.example.spieler.util.Constants
import com.example.spieler.viewmodel.LoginViewModel
import com.example.spieler.viewmodelfactory.LoginViewModelFactory

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory
    private lateinit var sessionSharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var playerNameDialog: AlertDialog.Builder
    private lateinit var dialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_login, container, false)

        //Get the email from sign up fragments

        val emailText by navArgs<LoginFragmentArgs>()
        if(emailText.email != "default"){
            binding.loginEmailTv.setText(emailText.email)
            binding.navText.visibility = View.GONE
        }

        //Initialisations
        val repository = Repository()
        sessionSharedPreferences = context?.getSharedPreferences(Constants.USER_SESSION, Context.MODE_PRIVATE)!!
        editor = sessionSharedPreferences.edit()
        viewModelFactory = LoginViewModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[LoginViewModel::class.java]

        binding.signupNavBtn.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.navToSignup())
        }

        //Login the user
        binding.loginBtn.setOnClickListener {
            val innerEmailText = binding.loginEmailTv.text.toString().trim()
            val passwordText = binding.loginPasswordTv.text.toString().trim()
            if(passwordText.isNotEmpty() && innerEmailText.isNotEmpty()){
                val user = LoginRequestBody(innerEmailText, passwordText)
                createDialog("Logging you in....")
                viewModel.loginUser(user)
            }
            else{
                Toast.makeText(requireContext(),
                    "Can't leave any field empty", Toast.LENGTH_SHORT).show()
            }
        }

        //Getting the response from the server
        viewModel.currentUser.observe(viewLifecycleOwner){ response ->
            if (response.isSuccessful){
                Log.d("Login Status", "Successful")
                val id = response?.body()?.content?.user_id ?: "None"
                if(id == "None"){
                    dialog.dismiss()
                    Toast.makeText(context, "Can't login, Try again later", Toast.LENGTH_SHORT).show()
                }
                else{
                    viewModel.getCurrentUserDetails(id)
                    viewModel.userDetails.observe(viewLifecycleOwner){user ->
                        if (user != null){
                            editor.apply {
                                putBoolean(Constants.USER_SESSION_ACTIVE, true)
                                putString(Constants.USER_ID, id)
                                putString(Constants.USER_EMAIL, user.email)
                                putString(Constants.USER_PASSWORD, binding.loginPasswordTv.text.toString().trim())
                                apply()
                            }
                            Intent(requireContext(), HomeActivity::class.java).also {
                                it.putExtra(Constants.USER_DATA, user)
                                startActivity(it)
                                requireActivity().finish()
                                dialog.dismiss()
                            }
                        }
                    }

                }
            }
            else{
                dialog.dismiss()
                Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
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