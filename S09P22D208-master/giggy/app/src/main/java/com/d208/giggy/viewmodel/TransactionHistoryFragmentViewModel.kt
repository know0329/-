package com.d208.giggy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d208.domain.model.DomainTransaction
import com.d208.domain.usecase.GetRecentDataUsecase
import com.d208.domain.usecase.TransactionUsecase
import com.d208.domain.utils.ErrorType
import com.d208.domain.utils.RemoteErrorEmitter
import com.d208.giggy.di.App
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TransactionHistoryFragmentViewModel @Inject constructor(
    private val transactionUsecase: TransactionUsecase,
    private val getRecentDataUsecase: GetRecentDataUsecase,
) :  ViewModel(), RemoteErrorEmitter {


    private val _transactionList = MutableLiveData<MutableList<DomainTransaction>>()
    val transactionList : LiveData<MutableList<DomainTransaction>> get() =_transactionList

    private val _updateSuccess = MutableLiveData<Boolean>()
    val updateSuccess : LiveData<Boolean> get() = _updateSuccess

    fun getRecentData() {
        viewModelScope.launch {
            getRecentDataUsecase.execute(
                this@TransactionHistoryFragmentViewModel,
                UUID.fromString(App.sharedPreferences.getString("id")!!)
            ).let { response ->
                if (response != null) {
                    _updateSuccess.value = response
                }
            }
        }
    }

    fun getTransactionData(id : UUID, startDate : String, endDate : String){
        viewModelScope.launch {
            transactionUsecase.execute(this@TransactionHistoryFragmentViewModel, id, startDate, endDate).let{
                response ->
                if(response != null && !response.isEmpty()){
                    _transactionList.value = response.sortedByDescending { it.transactionDate } as MutableList<DomainTransaction>
                }
                else{
                    _transactionList.value = mutableListOf()
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
                _transactionList.value = mutableListOf()
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