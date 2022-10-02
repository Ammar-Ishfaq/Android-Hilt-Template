package com.pkgname.appname

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.kaopiz.kprogresshud.KProgressHUD
import com.pkgname.appname.backend.BASE_URL.TOKEN
import com.pkgname.appname.databinding.ActivityMainBinding
import com.pkgname.appname.utils.SharePrefrenceHelper
import com.pkgname.appname.utils.Status
import com.pkgname.appname.viewmodels.LogoutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: LogoutViewModel by viewModels()
    lateinit var progressbar: KProgressHUD
    lateinit var title:TextView



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

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.splashFragment || destination.id == R.id.loginFragment) {
                binding.bottomNavigation.visibility = View.GONE
                binding.toolbar.visibility = View.GONE
            } else {
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.toolbar.visibility = View.VISIBLE
            }

            if (destination.id == R.id.homeFragment){
                binding.back.visibility = View.GONE
                binding.menu.visibility = View.VISIBLE
            }else{
                binding.menu.visibility = View.GONE
                binding.back.visibility = View.VISIBLE
            }

            NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
        }


        binding.navView.menu.findItem(R.id.logout).setOnMenuItemClickListener(
            MenuItem.OnMenuItemClickListener {
                binding.drawer.closeDrawer(GravityCompat.START)
                try {
                    SharePrefrenceHelper.getInstance(this).getUserId()?.let {
                        viewModel.logoutUser(
                            it
                        )
                    }
                } catch (e: Exception) {
                    //e.toString();
                }
                true
            })
        binding.navView.menu.findItem(R.id.home).setOnMenuItemClickListener(
            MenuItem.OnMenuItemClickListener {
                binding.drawer.closeDrawer(GravityCompat.START)
                true
            })
    }

    private fun onCLick() {
        binding.menu.setOnClickListener {
            binding.drawer.openDrawer(GravityCompat.START)
        }

        binding.back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun init(){

        TOKEN = "Bearer "+ SharePrefrenceHelper.getInstance(this).getToken().toString()
       // Toast.makeText(this, "Bearer "+ SharePrefrenceHelper.getInstance(this).getToken().toString(), Toast.LENGTH_SHORT).show()
      //  TOKEN = "Bearer "+ "18|l8K7ROVCJ2qCv2sfnlQix9Hn1FAwT8hd9GuBQwp9"


        title = findViewById(R.id.title)

        progressbar = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        progressbar.setCancellable(false)

        viewModel.data.observe(this , {
            it?.let { resource ->
                when (resource.status)
                {
                    Status.SUCCESS -> {

                        progressbar.dismiss()
                        navController.navigate(R.id.action_homeFragment_to_loginFragment)

                    }
                    Status.ERROR -> {
                        progressbar.dismiss()
                        Log.i(ContentValues.TAG, "setupObservers: error:"+it.message)
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressbar.show()
                        Log.i(ContentValues.TAG, "setupObservers: loading")

                    }
                }
            }
        })


    }
}