package com.pkgname.appname

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.kaopiz.kprogresshud.KProgressHUD
import com.pkgname.appname.backend.BASE_URL.TOKEN
import com.pkgname.appname.databinding.ActivityMainBinding
import com.pkgname.appname.utils.SharePrefrenceHelper
import com.pkgname.appname.viewmodels.LogoutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: LogoutViewModel by viewModels()
    lateinit var progressbar: KProgressHUD


    private val navController by lazy {
        Navigation.findNavController(this, R.id.fragmentContainerView)
    }
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()
        onCLick()


    }

    private fun onCLick() {

    }

    private fun init() {

        TOKEN = "Bearer " + SharePrefrenceHelper.getInstance(this).getToken().toString()
        // Toast.makeText(this, "Bearer "+ SharePrefrenceHelper.getInstance(this).getToken().toString(), Toast.LENGTH_SHORT).show()
        //  TOKEN = "Bearer "+ "18|l8K7ROVCJ2qCv2sfnlQix9Hn1FAwT8hd9GuBQwp9"



        progressbar = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        progressbar.setCancellable(false)


    }
}