package com.d208.giggy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d208.domain.model.DomainTransaction
import com.d208.domain.usecase.UpdateCategoryUsecase
import com.d208.domain.utils.ErrorType
import com.d208.domain.utils.RemoteErrorEmitter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailFragmentViewModel @Inject constructor(
    private val updateCategoryUsecase: UpdateCategoryUsecase
) : ViewModel(), RemoteErrorEmitter{

    private val _updateSuccess = MutableLiveData<Boolean>()
    val updateSuccess : LiveData<Boolean> get() = _updateSuccess
    fun updateCategory(data : DomainTransaction){
        viewModelScope.launch {
            updateCategoryUsecase.execute(this@TransactionDetailFragmentViewModel, data).let {
                if(it != null){
                    _updateSuccess.value = it
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