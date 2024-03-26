package com.d208.giggy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d208.domain.model.DomainUser
import com.d208.domain.model.SignUpUser
import com.d208.domain.usecase.AccountCheckUsecase
import com.d208.domain.usecase.SignUpUseCase
import com.d208.domain.utils.ErrorType
import com.d208.domain.utils.RemoteErrorEmitter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SignUpNextFragmentViewM giggy"
@HiltViewModel
class SignUpNextFragmentViewModel @Inject constructor(
    private val accountUsecase : AccountCheckUsecase,
    private val signUpUseCase : SignUpUseCase,
) : ViewModel(), RemoteErrorEmitter {



    var password : String? = null
    private val _accountAuthSuccess = MutableLiveData<Boolean>()
    val accountAuthSuccess : LiveData<Boolean> get() = _accountAuthSuccess

    private val _signUpSuccess = MutableLiveData<Boolean>()
    val signUpSuccess : LiveData<Boolean> get() = _signUpSuccess
    fun accountAuth(accountNumber : String, fcmToken : String, birthday : String){
        viewModelScope.launch {
            accountUsecase.execute(this@SignUpNextFragmentViewModel, accountNumber, fcmToken, birthday).let {
                response ->
                if(response == "서버 오류"){
                    _accountAuthSuccess.value = false
                    Log.d(TAG, "accountAuth: 통신 오류")
                }
                else if(response == ""){
                    _accountAuthSuccess.value = false
                }
                else{
                    Log.d(TAG, "accountAuth: $response")
                    _accountAuthSuccess.value = true
                    password = response
                }
            }


        }
    }

    fun signUp(user : DomainUser, accessToken : String, refreshToken : String, accountNumber : String) {
        var signUpUser = SignUpUser(nickname = user.nickname!!, accessToken =  accessToken, refreshToken = refreshToken, accountNumber = accountNumber, targetAmount = user.targetAmount, fcmToken = user.fcmToken!!)
        viewModelScope.launch {
            signUpUseCase.execute(this@SignUpNextFragmentViewModel, signUpUser).let {
                response ->
                if(response == true){
                    _signUpSuccess.value = true
                }
                else{
                    _signUpSuccess.value = false
                }


            }
        }
    }

    var apiErrorType = ErrorType.UNKNOWN
    var errorMessage = "none"

    private val _exceptionHandler = MutableLiveData<Int>()
    val exceptionHandler : LiveData<Int> get() = _exceptionHandler
    override fun onError(msg: String) {
        errorMessage = msg
    }

    override fun onError(errorType: ErrorType) {
        apiErrorType = errorType

        when (errorType) {
            ErrorType.NETWORK -> {
                // 네트워크 에러 처리
                _exceptionHandler.value = 0
            }
            ErrorType.SESSION_EXPIRED -> {
                // 세션 만료 에러 처리
                _exceptionHandler.value = 401
            }
            // 다른 에러 유형에 대한 처리 추가
            else -> {
                _exceptionHandler.value = 4
            }
        }
    }


}