package com.pkgname.appname.fragments.submitFragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pkgname.appname.R
import com.pkgname.appname.models.requestModels.submit.SubmitRequestModel
import com.pkgname.appname.utils.Status
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.hilt.android.AndroidEntryPoint

import android.app.TimePickerDialog
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.pkgname.appname.models.responseModel.jobofDayModel.JobofDayModel
import com.pkgname.appname.utils.SharePrefrenceHelper
import java.sql.Time
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.graphics.Bitmap

import com.pkgname.appname.MainActivity
import com.pkgname.appname.databinding.FragmentSubmitBinding

import java.io.ByteArrayOutputStream
import java.util.Locale











@AndroidEntryPoint
class SubmitFragment : Fragment() {
    private lateinit var binding: FragmentSubmitBinding
    private val viewModel: SubmitViewModel by viewModels()
    lateinit var progressbar: KProgressHUD
    var timePosition = 0
    var clientPosition = 0
    var spinnerCLientList:ArrayList<String> = ArrayList()
    lateinit var jobofDayModel:JobofDayModel
    var spinnerTimeList:ArrayList<String> = arrayListOf("45 Mins","1 Hour","1 Hour 15 Mins"
        ,"1 Hour 30 Mins","1 Hour 45 Mins","2 Hours")
    var timeList:ArrayList<String> = arrayListOf("00:45","01:00","01:15","01:30","01:45","02:00")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubmitBinding.inflate(inflater, container, false)
        val view = binding.root


        init()

        onClick()


        viewModel.data.observe(viewLifecycleOwner , {
            it?.let { resource ->
                when (resource.status)
                {
                    Status.SUCCESS -> {
                        progressbar.dismiss()

                        this.findNavController().navigate(R.id.action_submitFragment_to_submittedFragment)
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



    private fun onClick() {



        binding.submitBtn.setOnClickListener {
            if (binding.tvStartTime.text.toString() == ""){
                binding.tvStartTime.error = "Required"
            }else if (binding.tvFinishTime.text.toString() == ""){
                binding.tvFinishTime.error = "Required"
            }else{
                val byteArrayOutputStream = ByteArrayOutputStream()
                binding.signaturePad.signatureBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                val byteArray = byteArrayOutputStream.toByteArray()
                val encoded: String = Base64.encodeToString(byteArray, Base64.DEFAULT)
            var submitResposeModel=SubmitRequestModel(jobofDayModel.data[clientPosition].job_id,jobofDayModel.data[clientPosition].user_id,binding.tvStartTime.text.toString(),binding.tvFinishTime.text.toString()
                , timeList[timePosition] , binding.tvClientSpinner.text.toString() , encoded)
            viewModel.submit(submitResposeModel)
        }
        }

        binding.ivStartTime.setOnClickListener {
            val mcurrentTime: Calendar = Calendar.getInstance()
            val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute: Int = mcurrentTime.get(Calendar.MINUTE)
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(requireContext(),
                { timePicker, selectedHour, selectedMinute ->
                    binding.tvStartTime.text = getTime(selectedHour , selectedMinute)
                totalHours()},
                hour,
                minute,
                false
            ) //Yes 24 hour time

            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }

        binding.ivFinishTime.setOnClickListener {
            val mcurrentTime: Calendar = Calendar.getInstance()
            val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute: Int = mcurrentTime.get(Calendar.MINUTE)
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(requireContext(),
                { timePicker, selectedHour, selectedMinute ->
                    binding.tvFinishTime.text = getTime(selectedHour , selectedMinute)
                totalHours()},
                hour,
                minute,
                false
            ) //Yes 24 hour time

            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }

        binding.ivTimeSpinner.setOnClickListener {
            binding.timeSpinner.performClick()
            binding.timeSpinner.visibility = View.VISIBLE
        }

        binding.timeSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {


                timePosition=position
                binding.tvTimeSpinner.text = spinnerTimeList[position]
                binding.timeSpinner.visibility = View.INVISIBLE

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }





        binding.ivClientSpinner.setOnClickListener {
            binding.clientSpinner.performClick()
            binding.ivClientSpinner.visibility = View.VISIBLE
        }

        binding.clientSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {


                clientPosition=position
                binding.tvClientSpinner.text = jobofDayModel.data[position].c_contact
                binding.location.text = jobofDayModel.data[position].j_location
                binding.clientSpinner.visibility = View.INVISIBLE

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    private fun init(){
        val c = Calendar.getInstance().time
        println("Current time => $c")

        val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = df.format(c)

        binding.tvDate.text = formattedDate



        SharePrefrenceHelper.getInstance(requireContext()).getUserId()?.let {
            viewModel.jobofDay(
                it
            )
        }

        val activity = activity as MainActivity

        activity.title.text = "TimeSheet Form"

        progressbar = KProgressHUD.create(requireActivity())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        progressbar.setCancellable(false)



        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item, spinnerTimeList
        )
        binding.timeSpinner.adapter = adapter

        viewModel.spinnerData.observe(viewLifecycleOwner , {
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

    }

    private fun getTime(hr: Int, min: Int): String? {
        val tme = Time(hr, min, 0) //seconds by default set to zero
        val formatter: Format
        formatter = SimpleDateFormat("hh:mm a")
        return formatter.format(tme)
    }

    private fun retriveData(data: JobofDayModel) {
        jobofDayModel = data
        Log.i(ContentValues.TAG, "setupObservers: "+data)
       for (item in data.data){
           spinnerCLientList.add(item.c_contact)
       }

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item, spinnerCLientList
        )
        binding.clientSpinner.adapter = adapter


    }

    fun totalHours(){

        try {
            val simpleDateFormat = SimpleDateFormat("hh:mm a")

            val date1 = simpleDateFormat.parse(binding.tvStartTime.text.toString())
            val date2 = simpleDateFormat.parse(binding.tvFinishTime.text.toString())

            val difference = date2.time - date1.time
            val days = (difference / (1000 * 60 * 60 * 24))
            var hours = ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60))
            val min =
                (difference - 1000 * 60 * 60 * 24 * days - 1000 * 60 * 60 * hours) / (1000 * 60)
            hours = if (hours < 0) -hours else hours






            binding.tvTotalHours.text = "Total working hours = "+hours+":"+min
        }catch (e:Exception){
         //   Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
        }

    }


}