package com.pkgname.appname.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pkgname.appname.databinding.FragmentSubmittedBinding


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