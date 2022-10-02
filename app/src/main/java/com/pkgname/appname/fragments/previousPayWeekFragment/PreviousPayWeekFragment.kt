package com.pkgname.appname.fragments.previousPayWeekFragment

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
import androidx.navigation.fragment.findNavController
import com.pkgname.appname.MainActivity
import com.pkgname.appname.R
import com.pkgname.appname.databinding.FragmentPreviousPayWeekBinding
import com.pkgname.appname.models.responseModel.currentWeekModel.CurrentWeekModel
import com.pkgname.appname.utils.SharePrefrenceHelper
import com.pkgname.appname.utils.Status
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PreviousPayWeekFragment : Fragment() {
    private lateinit var binding: FragmentPreviousPayWeekBinding
    private val viewModel: PreviousWeekViewModel by viewModels()
    lateinit var progressbar: KProgressHUD
    lateinit var currentWeekModel:CurrentWeekModel




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPreviousPayWeekBinding.inflate(inflater, container, false)
        val view = binding.root

        init()
        onClick()

        SharePrefrenceHelper.getInstance(requireContext()).getUserId()?.let {
            viewModel.previousWeek(
                it
            )
        }

        return view
    }

    private fun onClick() {



        binding.currentWeekBtn.setOnClickListener {
            this.findNavController().navigate(R.id.action_hilt_PreviousPayWeekFragment_to_currentPayWeekFragment)
        }

        binding.btn1.setOnClickListener {
            val bundle=Bundle()
            bundle.putString("id" ,currentWeekModel.data[0].done_job_id)
            bundle.putString("title" , "Mon "+currentWeekModel.data[0].j_date)
            this.findNavController().navigate(R.id.action_hilt_PreviousPayWeekFragment_to_doneJobFragment , bundle)
        }
        binding.btn2.setOnClickListener {
            val bundle=Bundle()
            bundle.putString("id" ,currentWeekModel.data[1].done_job_id)
            bundle.putString("title" , "Tus "+currentWeekModel.data[1].j_date)
            this.findNavController().navigate(R.id.action_hilt_PreviousPayWeekFragment_to_doneJobFragment , bundle)
        }
        binding.btn3.setOnClickListener {
            val bundle=Bundle()
            bundle.putString("id" ,currentWeekModel.data[2].done_job_id)
            bundle.putString("title" , "Wed "+currentWeekModel.data[2].j_date)
            this.findNavController().navigate(R.id.action_hilt_PreviousPayWeekFragment_to_doneJobFragment , bundle)
        }
        binding.btn4.setOnClickListener {
            val bundle=Bundle()
            bundle.putString("id" ,currentWeekModel.data[3].done_job_id)
            bundle.putString("title" , "Thr "+currentWeekModel.data[3].j_date)
            this.findNavController().navigate(R.id.action_hilt_PreviousPayWeekFragment_to_doneJobFragment , bundle)
        }
        binding.btn5.setOnClickListener {
            val bundle=Bundle()
            bundle.putString("id" ,currentWeekModel.data[4].done_job_id)
            bundle.putString("title" , "Fri "+currentWeekModel.data[4].j_date)
            this.findNavController().navigate(R.id.action_hilt_PreviousPayWeekFragment_to_doneJobFragment , bundle)
        }
        binding.btn6.setOnClickListener {
            val bundle=Bundle()
            bundle.putString("id" ,currentWeekModel.data[5].done_job_id)
            bundle.putString("title" , "Sat "+currentWeekModel.data[5].j_date)
            this.findNavController().navigate(R.id.action_hilt_PreviousPayWeekFragment_to_doneJobFragment , bundle)
        }
        binding.btn7.setOnClickListener {
            val bundle=Bundle()
            bundle.putString("id" ,currentWeekModel.data[6].done_job_id)
            bundle.putString("title" , "Sun "+currentWeekModel.data[6].j_date)
            this.findNavController().navigate(R.id.action_hilt_PreviousPayWeekFragment_to_doneJobFragment , bundle)
        }
    }

    fun init(){
        progressbar = KProgressHUD.create(requireActivity())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        progressbar.setCancellable(false)

        val activity = activity as MainActivity

       activity.title.text = "Previous Pay Week"


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

//        val format: DateFormat = SimpleDateFormat("MM/dd/yyyy")
//        val calendar: Calendar = Calendar.getInstance()
//        calendar.setFirstDayOfWeek(Calendar.MONDAY)
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
//
//        val days = arrayOfNulls<String>(7)
//        for (i in 0..6) {
//            days[i] = format.format(calendar.getTime())
//            calendar.add(Calendar.DAY_OF_MONTH, 1)
//        }




    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    private fun retriveData(data: CurrentWeekModel) {

        currentWeekModel = data


        binding.day1.text = "Mon "+data.data[0].j_date+" - "+data.data[0].working_hours+"Hrs"
        binding.day2.text = "Tus "+data.data[1].j_date+" - "+data.data[1].working_hours+"Hrs"
        binding.day3.text = "Wed "+data.data[2].j_date+" - "+data.data[2].working_hours+"Hrs"
        binding.day4.text = "Thu "+data.data[3].j_date+" - "+data.data[3].working_hours+"Hrs"
        binding.day5.text = "Fri "+data.data[4].j_date+" - "+data.data[4].working_hours+"Hrs"
        binding.day6.text = "Sat "+data.data[5].j_date+" - "+data.data[5].working_hours+"Hrs"
        binding.day7.text = "Sun "+data.data[6].j_date+" - "+data.data[6].working_hours+"Hrs"

        if (data.data.size>1){
            if (data.data[0].submission_status){
                binding.day1.setBackgroundResource(R.color.submit_color)
            }  else{
                binding.btn1.text = "Open"
                binding.day1.setBackgroundResource(R.color.not_submit_color)
            }
        }
        if (data.data.size>2){
            if (data.data[1].submission_status){
                binding.day2.setBackgroundResource(R.color.submit_color)
            }  else{
                binding.btn2.text = "Open"
                binding.day2.setBackgroundResource(R.color.not_submit_color)
            }
        }
        if (data.data.size>3){
            if (data.data[2].submission_status){
                binding.day3.setBackgroundResource(R.color.submit_color)
            }  else{
                binding.btn3.text = "Open"
                binding.day3.setBackgroundResource(R.color.not_submit_color)
            }
        }
        if (data.data.size>4){
            if (data.data[3].submission_status){
                binding.day4.setBackgroundResource(R.color.submit_color)
            }  else{
                binding.btn4.text = "Open"
                binding.day4.setBackgroundResource(R.color.not_submit_color)
            }
        }
        if (data.data.size>5){
            if (data.data[4].submission_status){
                binding.day5.setBackgroundResource(R.color.submit_color)
            }  else{
                binding.btn5.text = "Open"
                binding.day5.setBackgroundResource(R.color.not_submit_color)
            }
        }
        if (data.data.size>6){
            if (data.data[5].submission_status){
                binding.day6.setBackgroundResource(R.color.submit_color)
            }  else{
                binding.btn6.text = "Open"
                binding.day6.setBackgroundResource(R.color.not_submit_color)
            }
        }
        if (data.data.size>7){
            if (data.data[6].submission_status){
                binding.day7.setBackgroundResource(R.color.submit_color)
            }  else{
                binding.btn7.text = "Open"
                binding.day7.setBackgroundResource(R.color.not_submit_color)
            }
        }


//        if (data.data.size==1){
//            binding.day1.setBackgroundResource(R.color.app_color)
//        }
//        if (data.data.size==2){
//            binding.day2.setBackgroundResource(R.color.app_color)
//        }
//        if (data.data.size==3){
//            binding.day3.setBackgroundResource(R.color.app_color)
//        }
//        if (data.data.size==4){
//            binding.day4.setBackgroundResource(R.color.app_color)
//        }
//        if (data.data.size==5){
//            binding.day5.setBackgroundResource(R.color.app_color)
//        }
//        if (data.data.size==6){
//            binding.day6.setBackgroundResource(R.color.app_color)
//        }
//        if (data.data.size==7){
//            binding.day7.setBackgroundResource(R.color.app_color)
//        }


    }

}