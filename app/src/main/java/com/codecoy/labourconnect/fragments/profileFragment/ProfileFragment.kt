package com.codecoy.labourconnect.fragments.profileFragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.codecoy.labourconnect.MainActivity
import com.codecoy.labourconnect.R
import com.codecoy.labourconnect.databinding.FragmentProfileBinding
import com.codecoy.labourconnect.fragments.loginFragment.LoginViewModel
import com.codecoy.labourconnect.models.responseModel.login.LoginModelResponse
import com.codecoy.labourconnect.models.responseModel.profile.ProfileModel
import com.codecoy.labourconnect.utils.SharePrefrenceHelper
import com.codecoy.labourconnect.utils.Status
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    lateinit var progressbar: KProgressHUD

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        init()

        SharePrefrenceHelper.getInstance(requireContext()).getUserId()?.let {
            viewModel.profileUser(
                it
            )
        }

        onClick()
        viewModel.data.observe(viewLifecycleOwner , {
            it?.let { resource ->
                when (resource.status)
                {
                    Status.SUCCESS -> {

                        progressbar.dismiss()
                        resource.data?.let {
                                data -> retriveData(data)
                        }
                    }
                    Status.ERROR -> {
                        progressbar.dismiss()
                        Log.i(ContentValues.TAG, "setupObservers: error:"+it.message)
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressbar.show()
                        Log.i(ContentValues.TAG, "setupObservers: loading")

                    }
                }
            }
        })


        return view
    }

    private fun retriveData(data: ProfileModel) {

        Log.i(ContentValues.TAG, "setupObservers: "+data)
        binding.tvName.text = "Name : "+data.data.u_name
        binding.tvUsername.text =  "Username : "+data.data.u_uname
        binding.tvPhone.text =  "Phone : "+data.data.u_phone
        binding.tvDob.text =  "DOB : "+data.data.u_dob
    }
    private fun onClick() {

    }


    private fun init(){

        val activity = activity as MainActivity

        activity.title.text = "Profile"

        progressbar = KProgressHUD.create(requireActivity())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        progressbar.setCancellable(false)


    }
}