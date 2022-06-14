package com.codecoy.labourconnect.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codecoy.labourconnect.MainActivity
import com.codecoy.labourconnect.R
import com.codecoy.labourconnect.databinding.FragmentSubmitBinding
import com.codecoy.labourconnect.databinding.FragmentSubmittedBinding


class SubmittedFragment : Fragment() {
    private lateinit var binding: FragmentSubmittedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubmittedBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.continueBtn.setOnClickListener {
            activity?.onBackPressed()
        }

        return view
    }
}