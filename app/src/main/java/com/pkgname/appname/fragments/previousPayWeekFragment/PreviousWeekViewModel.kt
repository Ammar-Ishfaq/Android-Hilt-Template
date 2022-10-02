package com.pkgname.appname.fragments.previousPayWeekFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pkgname.appname.models.responseModel.currentWeekModel.CurrentWeekModel
import com.pkgname.appname.utils.Resource
import com.pkgname.appname.utils.SharePrefrenceHelper
import com.pkgname.appname.utils.SharedWebServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreviousWeekViewModel @Inject constructor(private val sharedWebServices: SharedWebServices, private val sharedPreferenceHelper: SharePrefrenceHelper) : ViewModel() {

    var _data= MutableLiveData<Resource<CurrentWeekModel>>()
    val data: LiveData<Resource<CurrentWeekModel>>
        get() = _data

    fun previousWeek(id:String) {
        viewModelScope.launch {

            _data.postValue(Resource.loading(data = null))

            sharedWebServices.previousWeek(id).run{
                    onSuccess {

                        if (it.status) {

                            _data.postValue(Resource.success(data = it))

                        } else {
                            _data.postValue(Resource.error(data = null, message = it.message ?: "Error Occurred!"))
                        }
                    }
                    onFailure {
                        _data.postValue(Resource.error(data = null, message = it.message ?: "Error Occurred!"))
                    }
                }
            }
        }

    }