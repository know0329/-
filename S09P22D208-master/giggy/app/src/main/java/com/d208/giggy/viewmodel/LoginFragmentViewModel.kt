package com.d208.giggy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d208.domain.model.DomainUser
import com.d208.domain.usecase.LoginUsecase
import com.d208.domain.utils.ErrorType
import com.d208.domain.utils.RemoteErrorEmitter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
private const val TAG = "LoginFragmentViewModel_giggy"
@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase,


    ) : ViewModel(), RemoteErrorEmitter {


    private val _loginSuccess = MutableLiveData<DomainUser> ()
    val loginSuccess : LiveData<DomainUser> get() = _loginSuccess

    var apiErrorType = ErrorType.UNKNOWN
    var errorMessage = "none"


    fun login(access : String, refresh : String, fcm : String){

        viewModelScope.launch {
            loginUsecase.execute(this@LoginFragmentViewModel, access, refresh, fcm).let {response ->
                Log.d(TAG, "login: $response")
                if(response != null){
                    _loginSuccess.value = response
                    Log.d(TAG, "login: ${_loginSuccess.value}")
                }
                else{
                    Log.d(TAG, "login:  오류 ")
                }
            }
        }

    }

    override fun onError(msg: String) {
        errorMessage = msg
    }

    override fun onError(errorType: ErrorType) {
        apiErrorType = errorType
    }
}