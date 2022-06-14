package com.codecoy.labourconnect.fragments.doneJobFragment

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.codecoy.labourconnect.R
import com.codecoy.labourconnect.databinding.FragmentDoneJobBinding
import com.codecoy.labourconnect.databinding.FragmentPreviousPayWeekBinding
import com.codecoy.labourconnect.fragments.previousPayWeekFragment.PreviousWeekViewModel
import com.codecoy.labourconnect.models.responseModel.donejob.DoneJobModel
import com.codecoy.labourconnect.utils.SharePrefrenceHelper
import com.codecoy.labourconnect.utils.Status
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.AndroidEntryPoint
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.util.Base64
import androidx.navigation.fragment.findNavController
import com.codecoy.labourconnect.MainActivity
import com.codecoy.labourconnect.hilt.AppModule_ContextFactory.context





@AndroidEntryPoint
class DoneJobFragment : Fragment() {
    private lateinit var binding: FragmentDoneJobBinding
    private val viewModel: DoneJobViewModel by viewModels()
    lateinit var progressbar: KProgressHUD
    var jobId="null"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDoneJobBinding.inflate(inflater, container, false)
        val view = binding.root

        init()
        onClick()



        return view
    }

    private fun onClick() {
        binding.homeBtn.setOnClickListener {
            this.findNavController().navigate(R.id.homeFragment)
        }
    }

    fun init(){

        jobId = requireArguments().getString("id").toString()

        val activity = activity as MainActivity

        activity.title.text = requireArguments().getString("title").toString()

        viewModel.doneJob(jobId)



        progressbar = KProgressHUD.create(requireActivity())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        progressbar.setCancellable(false)


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
                        binding.constraintLayout.visibility=View.INVISIBLE
                        binding.date.visibility=View.VISIBLE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressbar.show()
                        Log.i(ContentValues.TAG, "setupObservers: loading")

                    }
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun retriveData(data: DoneJobModel) {
        binding.date.text = data.data.j_date
        binding.tvStartTime.text = data.data.start
        binding.location.text = data.data.j_location
        binding.tvFinishTime.text = data.data.finish
        binding.tvClientSpinner.text = data.data.c_contact
        binding.tvTimeSpinner.text = data.data.`break`
        binding.superviser.text = data.data.supervisor
        binding.tvTotalHours.text = "Total working hours = "+data.data.working_hours
        val imageByteArray: ByteArray = Base64.decode(data.data.signature, Base64.DEFAULT)

        Glide.with(requireContext())
            .load(imageByteArray)
            .into(binding.signature)
    }
}