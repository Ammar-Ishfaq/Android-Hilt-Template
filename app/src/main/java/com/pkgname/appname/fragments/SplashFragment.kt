package com.pkgname.appname.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pkgname.appname.R
import com.pkgname.appname.databinding.FragmentSplashBinding
import com.pkgname.appname.utils.SharePrefrenceHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root

        lifecycleScope.launch {
            delay(1000)
            if (SharePrefrenceHelper.getInstance(requireContext()).isUserLoggedIn()){
//                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }else{
//                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }

        return view
    }
}