package com.codecoy.labourconnect.fragments.submitFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codecoy.labourconnect.models.requestModels.submit.SubmitRequestModel
import com.codecoy.labourconnect.models.responseModel.jobofDayModel.JobofDayModel
import com.codecoy.labourconnect.models.responseModel.login.LoginModelResponse
import com.codecoy.labourconnect.models.responseModel.profile.ProfileModel
import com.codecoy.labourconnect.utils.Resource
import com.codecoy.labourconnect.utils.SharePrefrenceHelper
import com.codecoy.labourconnect.utils.SharedWebServices
import com.codecoy.labourconnect.utils.SubmitResposeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubmitViewModel @Inject constructor(private val sharedWebServices: SharedWebServices, private val sharedPreferenceHelper: SharePrefrenceHelper) : ViewModel() {

    var _data= MutableLiveData<Resource<SubmitResposeModel>>()
    val data: LiveData<Resource<SubmitResposeModel>>
        get() = _data

    var _spinnerData= MutableLiveData<Resource<JobofDayModel>>()
    val spinnerData: LiveData<Resource<JobofDayModel>>
        get() = _spinnerData

    fun submit(submitRequestModel: SubmitRequestModel) {
        viewModelScope.launch {

            _data.postValue(Resource.loading(data = null))

            sharedWebServices.submit(submitRequestModel).run{
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

    fun jobofDay(id:String) {
        viewModelScope.launch {

            _spinnerData.postValue(Resource.loading(data = null))

            sharedWebServices.jobofDay(id).run{
                onSuccess {

                    if (it.status) {

                        _spinnerData.postValue(Resource.success(data = it))

                    } else {
                        _spinnerData.postValue(Resource.error(data = null, message = it.message ?: "Error Occurred!"))
                    }
                }
                onFailure {
                    _spinnerData.postValue(Resource.error(data = null, message = it.message ?: "Error Occurred!"))
                }
            }
        }
    }

    }
