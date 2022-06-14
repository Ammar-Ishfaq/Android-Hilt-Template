package com.codecoy.labourconnect.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.codecoy.labourconnect.MainActivity
import com.codecoy.labourconnect.R
import com.codecoy.labourconnect.databinding.FragmentHomeBinding
import com.codecoy.labourconnect.databinding.FragmentLoginBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val activity = activity as MainActivity

        activity.title.text = "DashBoard"

        onClick()


        return view
    }

    private fun onClick() {
        binding.timeSheet.setOnClickListener {
            this.findNavController().navigate(R.id.action_homeFragment_to_timeSheetFragment)
        }
    }

}