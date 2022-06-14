package com.codecoy.labourconnect.fragments.currentPayWeekFregment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codecoy.labourconnect.models.responseModel.currentWeekModel.CurrentWeekModel
import com.codecoy.labourconnect.models.responseModel.login.LoginModelResponse
import com.codecoy.labourconnect.models.responseModel.profile.ProfileModel
import com.codecoy.labourconnect.utils.Resource
import com.codecoy.labourconnect.utils.SharePrefrenceHelper
import com.codecoy.labourconnect.utils.SharedWebServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeekViewModel @Inject constructor(private val sharedWebServices: SharedWebServices, private val sharedPreferenceHelper: SharePrefrenceHelper) : ViewModel() {

    var _data= MutableLiveData<Resource<CurrentWeekModel>>()
    val data: LiveData<Resource<CurrentWeekModel>>
        get() = _data

    fun currentWeek(id:String) {
        viewModelScope.launch {

            _data.postValue(Resource.loading(data = null))

            sharedWebServices.currentWeek(id).run{
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
