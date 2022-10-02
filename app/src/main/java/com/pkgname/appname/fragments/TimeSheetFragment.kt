package com.pkgname.appname.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.pkgname.appname.MainActivity
import com.pkgname.appname.R
import com.pkgname.appname.databinding.FragmentTimeSheetBinding


class TimeSheetFragment : Fragment() {
    private lateinit var binding: FragmentTimeSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimeSheetBinding.inflate(inflater, container, false)
        val view = binding.root

        val activity = activity as MainActivity

        activity.title.text = "TimeSheet"

        onClick()



        return view
    }

    private fun onClick() {
        binding.addBtn.setOnClickListener {
            this.findNavController().navigate(R.id.action_timeSheetFragment_to_currentPayWeekFragment)
        }
        binding.preWeekBtn.setOnClickListener {
            this.findNavController().navigate(R.id.action_timeSheetFragment_to_hilt_PreviousPayWeekFragment)
        }
    }
}