package com.codecoy.labourconnect.fragments.loginFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codecoy.labourconnect.backend.BASE_URL
import com.codecoy.labourconnect.models.requestModels.login.LoginModel
import com.codecoy.labourconnect.models.responseModel.login.LoginModelResponse
import com.codecoy.labourconnect.utils.Resource
import com.codecoy.labourconnect.utils.SharePrefrenceHelper
import com.codecoy.labourconnect.utils.SharedWebServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val sharedWebServices: SharedWebServices,private val sharedPreferenceHelper: SharePrefrenceHelper) : ViewModel() {

    var _data= MutableLiveData<Resource<LoginModelResponse>>()
    val data: LiveData<Resource<LoginModelResponse>>
        get() = _data

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _data.postValue(Resource.loading(data = null))

            sharedWebServices.loginUser(email, password).run {

                onSuccess {
                  //  _showHideProgressDialog.value = false.wrapWithEvent()
                    if (it.status) {
                        sharedPreferenceHelper.savePhoneNumber(it.data.u_phone)
                        sharedPreferenceHelper.saveUserId(it.data.id.toString())
                        sharedPreferenceHelper.saveLoginEmail(it.data.u_email)
                        sharedPreferenceHelper.saveLoginPassword(it.data.u_pass)
                        sharedPreferenceHelper.saveUserName(it.data.u_name)
                        sharedPreferenceHelper.saveToken(it.data.token)
                        sharedPreferenceHelper.saveUserLogIn()
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