package com.codecoy.labourconnect.fragments.currentPayWeekFregment

import android.annotation.SuppressLint
import android.content.ContentValues
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.codecoy.labourconnect.MainActivity
import com.codecoy.labourconnect.R
import com.codecoy.labourconnect.databinding.FragmentCurrentPayWeekBinding
import com.codecoy.labourconnect.databinding.FragmentLoginBinding
import com.codecoy.labourconnect.fragments.loginFragment.LoginViewModel
import com.codecoy.labourconnect.models.responseModel.currentWeekModel.CurrentWeekModel
import com.codecoy.labourconnect.utils.SharePrefrenceHelper
import com.codecoy.labourconnect.utils.Status
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
@AndroidEntryPoint
class CurrentPayWeekFragment : Fragment() {
    private lateinit var binding: FragmentCurrentPayWeekBinding
    private val viewModel: CurrentWeekViewModel by viewModels()
    lateinit var progressbar: KProgressHUD
    lateinit var currentWeekModel: CurrentWeekModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentPayWeekBinding.inflate(inflater, container, false)
        val view = binding.root

        init()
        onClick()

        SharePrefrenceHelper.getInstance(requireContext()).getUserId()?.let {
            viewModel.currentWeek(
                it
            )
        }

        return view
    }

    private fun onClick() {
        binding.previousWeekBtn.setOnClickListener {
                this.findNavController().navigate(R.id.action_currentPayWeekFragment_to_hilt_PreviousPayWeekFragment)
        }

        binding.btn1.setOnClickListener {
            if (currentWeekModel.data.size==1 && !currentWeekModel.data[0].submission_status)
            this.findNavController().navigate(R.id.action_currentPayWeekFragment_to_submitFragment)

            if (currentWeekModel.data.size<2 && currentWeekModel.data[0].submission_status){
                val bundle=Bundle()
                bundle.putString("id" ,currentWeekModel.data[0].done_job_id)
                bundle.putString("title" , "Mon "+currentWeekModel.data[0].j_date)
                this.findNavController().navigate(R.id.action_currentPayWeekFragment_to_doneJobFragment , bundle)
            }
        }
        binding.btn2.setOnClickListener {
            if (currentWeekModel.data.size==2 && !currentWeekModel.data[1].submission_status)
                this.findNavController().navigate(R.id.action_currentPayWeekFragment_to_submitFragment)

            if (currentWeekModel.data.size<3 && currentWeekModel.data[1].submission_status){
                val bundle=Bundle()
                bundle.putString("id" ,currentWeekModel.data[1].done_job_id)
                bundle.putString("title" , "Tus "+currentWeekModel.data[1].j_date)
                this.findNavController().navigate(R.id.action_currentPayWeekFragment_to_doneJobFragment , bundle)
            }
        }
        binding.btn3.setOnClickListener {
            if (currentWeekModel.data.size==3 && !currentWeekModel.data[2].submission_status)
                this.findNavController().navigate(R.id.action_currentPayWeekFragment_to_submitFragment)

            if (currentWeekModel.data.size<4 && currentWeekModel.data[2].submission_status){
                val bundle=Bundle()
                bundle.putString("id" ,currentWeekModel.data[2].done_job_id)
                bundle.putString("title" , "Wed "+currentWeekModel.data[2].j_date)
                this.findNavController().navigate(R.id.action_currentPayWeekFragment_to_doneJobFragment , bundle)
            }
        }
        binding.btn4.setOnClickListener {
            if (currentWeekModel.data.size==4 && !currentWeekModel.data[3].submission_status)
                this.findNavController().navigate(R.id.action_currentPayWeekFragment_to_submitFragment)

            if (currentWeekModel.data.size<5 && currentWeekModel.data[3].submission_status){
                val bundle=Bundle()
                bundle.putString("id" ,currentWeekModel.data[3].done_job_id)
                bundle.putString("title" , "Thr "+currentWeekModel.data[3].j_date)
                this.findNavController().navigate(R.id.action_currentPayWeekFragment_to_doneJobFragment , bundle)
            }
        }
        binding.btn5.setOnClickListener {
            if (currentWeekModel.data.size==5 && !currentWeekModel.data[4].submission_status)
                this.findNavController().navigate(R.id.action_currentPayWeekFragment_to_submitFragment)

            if (currentWeekModel.data.size<6 && currentWeekModel.data[4].submission_status){
                val bundle=Bundle()
                bundle.putString("id" ,currentWeekModel.data[4].done_job_id)
                bundle.putString("title" , "Fir "+currentWeekModel.data[4].j_date)
                this.findNavController().navigate(R.id.action_currentPayWeekFragment_to_doneJobFragment , bundle)
            }
        }
        binding.btn6.setOnClickListener {
            if (currentWeekModel.data.size==6 && !currentWeekModel.data[5].submission_status)
                this.findNavController().navigate(R.id.action_currentPayWeekFragment_to_submitFragment)

            if (currentWeekModel.data.size<7 && currentWeekModel.data[5].submission_status){
                val bundle=Bundle()
                bundle.putString("id" ,currentWeekModel.data[5].done_job_id)
                bundle.putString("title" , "Mon "+currentWeekModel.data[5].j_date)
                this.findNavController().navigate(R.id.action_currentPayWeekFragment_to_doneJobFragment , bundle)
            }
        }
        binding.btn7.setOnClickListener {
            if (currentWeekModel.data.size==7 && !currentWeekModel.data[6].submission_status)
                this.findNavController().navigate(R.id.action_currentPayWeekFragment_to_submitFragment)

            if (currentWeekModel.data.size<8 && currentWeekModel.data[6].submission_status){
                val bundle=Bundle()
                bundle.putString("id" ,currentWeekModel.data[6].done_job_id)
                bundle.putString("title" , "Mon "+currentWeekModel.data[6].j_date)
                this.findNavController().navigate(R.id.action_currentPayWeekFragment_to_doneJobFragment , bundle)
            }
        }
    }

    fun init(){
        progressbar = KProgressHUD.create(requireActivity())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        progressbar.setCancellable(false)

        val activity = activity as MainActivity

        activity.title.text = "Current Pay Week"


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

        val format: DateFormat = SimpleDateFormat("MM/dd/yyyy")
        val calendar: Calendar = Calendar.getInstance()
        calendar.setFirstDayOfWeek(Calendar.MONDAY)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        val days = arrayOfNulls<String>(7)
        for (i in 0..6) {
            days[i] = format.format(calendar.getTime())
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }


        binding.day1.text = "Mon "+days[0].toString()+" - 00Hrs"
        binding.day2.text = "Tus "+days[1].toString()+" - 00Hrs"
        binding.day3.text = "Wed "+days[2].toString()+" - 00Hrs"
        binding.day4.text = "Thu "+days[3].toString()+" - 00Hrs"
        binding.day5.text = "Fri "+days[4].toString()+" - 00Hrs"
        binding.day6.text = "Sat "+days[5].toString()+" - 00Hrs"
        binding.day7.text = "Sun "+days[6].toString()+" - 00Hrs"

    }

    @SuppressLint("ResourceAsColor")
    private fun retriveData(data: CurrentWeekModel) {
        currentWeekModel = data



        if (data.data.size>1){
            if (data.data[0].submission_status){
                binding.day1.text = "Mon "+data.data[0].j_date+" - "+data.data[0].working_hours+"Hrs"
                binding.day1.setBackgroundResource(R.color.submit_color)
            }  else{
                binding.day1.setBackgroundResource(R.color.not_submit_color)
            }
            binding.btn1.text = "Open"

        }
        if (data.data.size>2){
            if (data.data[1].submission_status){
                binding.day2.text = "Tus "+data.data[1].j_date+" - "+data.data[1].working_hours+"Hrs"
                binding.day2.setBackgroundResource(R.color.submit_color)
            }  else{
                binding.day2.setBackgroundResource(R.color.not_submit_color)
            }
            binding.btn2.text = "Open"

        }
        if (data.data.size>3){
            if (data.data[2].submission_status){
                binding.day3.text = "Wed "+data.data[2].j_date+" - "+data.data[2].working_hours+"Hrs"
                binding.day3.setBackgroundResource(R.color.submit_color)
            }  else{
                binding.day3.setBackgroundResource(R.color.not_submit_color)
            }
            binding.btn3.text = "Open"

        }
        if (data.data.size>4){
            if (data.data[3].submission_status){
                binding.day4.text = "Thr "+data.data[3].j_date+" - "+data.data[3].working_hours+"Hrs"
                binding.day4.setBackgroundResource(R.color.submit_color)
            }  else{
                binding.day4.setBackgroundResource(R.color.not_submit_color)
            }
            binding.btn4.text = "Open"


        }
        if (data.data.size>5){
            if (data.data[4].submission_status){
                binding.day5.text = "Fri "+data.data[4].j_date+" - "+data.data[4].working_hours+"Hrs"
                binding.day5.setBackgroundResource(R.color.submit_color)
            }  else{
                binding.day5.setBackgroundResource(R.color.not_submit_color)
            }
            binding.btn5.text = "Open"

        }
        if (data.data.size>6){
            if (data.data[5].submission_status){
                binding.day6.text = "Sat "+data.data[5].j_date+" - "+data.data[5].working_hours+"Hrs"
                binding.day6.setBackgroundResource(R.color.submit_color)
            }  else{
                binding.day6.setBackgroundResource(R.color.not_submit_color)
            }
            binding.btn6.text = "Open"

        }
        if (data.data.size>7){
            if (data.data[6].submission_status){
                binding.day7.text = "Sun "+data.data[6].j_date+" - "+data.data[6].working_hours+"Hrs"
                binding.day7.setBackgroundResource(R.color.submit_color)
            }  else{
                binding.day7.setBackgroundResource(R.color.not_submit_color)
            }
            binding.btn7.text = "Open"

        }


        if (data.data.size==1 && data.data[0].submission_status){
            binding.day1.text = "Mon "+data.data[0].j_date+" - "+data.data[0].working_hours+"Hrs"
             binding.day1.setBackgroundResource(R.color.submit_color)
            binding.btn7.text = "Open"
            }
        if (data.data.size==2 && data.data[1].submission_status){
            binding.day2.text = "Tus "+data.data[1].j_date+" - "+data.data[1].working_hours+"Hrs"
            binding.day2.setBackgroundResource(R.color.submit_color)
            binding.btn2.text = "Open"
        }
        if (data.data.size==3 && data.data[2].submission_status){
            binding.day3.text = "Wed "+data.data[2].j_date+" - "+data.data[2].working_hours+"Hrs"
            binding.day3.setBackgroundResource(R.color.submit_color)
            binding.btn3.text = "Open"
        }
        if (data.data.size==4 && data.data[3].submission_status){
            binding.day4.text = "Thr "+data.data[3].j_date+" - "+data.data[3].working_hours+"Hrs"
            binding.day4.setBackgroundResource(R.color.submit_color)
            binding.btn4.text = "Open"
        }
        if (data.data.size==5 && data.data[4].submission_status){
            binding.day5.text = "Fri "+data.data[4].j_date+" - "+data.data[4].working_hours+"Hrs"
            binding.day5.setBackgroundResource(R.color.submit_color)
            binding.btn5.text = "Open"
        }
        if (data.data.size==6 && data.data[5].submission_status){
            binding.day6.text = "Sat "+data.data[5].j_date+" - "+data.data[5].working_hours+"Hrs"
            binding.day6.setBackgroundResource(R.color.submit_color)
            binding.btn6.text = "Open"
        }
        if (data.data.size==7 && data.data[6].submission_status){
            binding.day7.text = "Sub "+data.data[6].j_date+" - "+data.data[6].working_hours+"Hrs"
            binding.day7.setBackgroundResource(R.color.submit_color)
            binding.btn7.text = "Open"
        }





    }

}