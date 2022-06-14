package com.codecoy.labourconnect.fragments.loginFragment

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.codecoy.labourconnect.R
import com.codecoy.labourconnect.backend.BASE_URL
import com.codecoy.labourconnect.databinding.FragmentLoginBinding
import com.codecoy.labourconnect.databinding.FragmentSplashBinding
import com.codecoy.labourconnect.models.responseModel.login.LoginModelResponse
import com.codecoy.labourconnect.utils.SharePrefrenceHelper
import com.codecoy.labourconnect.utils.Status
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private val viewModel:LoginViewModel by viewModels()
    lateinit var progressbar: KProgressHUD

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root


        init()

        onClick()


        viewModel.data.observe(viewLifecycleOwner , {
            it?.let { resource ->
                when (resource.status)
                {
                    Status.SUCCESS -> {
                        progressbar.dismiss()
                        BASE_URL.TOKEN = "Bearer "+ SharePrefrenceHelper.getInstance(requireContext()).getToken().toString()
                        this.findNavController().navigate(R.id.action_loginFragment_to_homeFragment  )
                    }
                    Status.ERROR -> {
                        progressbar.dismiss()
                        Log.i(TAG, "setupObservers: error:"+it.message)
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        if (it.message=="PIN is Incorrent."){
                            binding.error.visibility = View.VISIBLE
                        }
                    }
                    Status.LOADING -> {
                        progressbar.show()
                        Log.i(TAG, "setupObservers: loading")

                    }
                }
            }
        })


        return view
    }



    private fun onClick() {
        binding.loginBtn.setOnClickListener {
            if (binding.usernameEdt.text.toString() == ""){
                binding.usernameEdt.error = "Required"
            }else{
                viewModel.loginUser(binding.usernameEdt.text.toString().trim()
                    ,binding.password.text.toString())
            }



        }
    }

    private fun init(){

        progressbar = KProgressHUD.create(requireActivity())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        progressbar.setCancellable(false)


    }


}