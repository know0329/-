package com.d208.giggy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d208.domain.model.DomainBeggerRank
import com.d208.domain.model.DomainMyBegger
import com.d208.domain.usecase.BeggerRankUsecase
import com.d208.domain.utils.ErrorType
import com.d208.domain.utils.RemoteErrorEmitter
import com.d208.giggy.di.App
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RankFragmentViewModel @Inject constructor(
    private val rankUsecase: BeggerRankUsecase
) : ViewModel(), RemoteErrorEmitter {

    private val _beggerRankList = MutableLiveData<List<DomainBeggerRank>>()
    val beggerRankList : LiveData<List<DomainBeggerRank>> get() = _beggerRankList
    fun getBeggerRank() {
        viewModelScope.launch {
            rankUsecase.execute(this@RankFragmentViewModel).let {
                if(!it.isNullOrEmpty()){
                    _beggerRankList.value = it
                }
                else{
                    _beggerRankList.value = mutableListOf()
                }
            }
        }
    }

    private val _myBeggerRank =  MutableLiveData<DomainMyBegger>()

    val myBeggerRank : LiveData<DomainMyBegger> get() = _myBeggerRank

    fun getMyBeggerRank(){
        viewModelScope.launch {
            rankUsecase.executeMyBeggerRank(this@RankFragmentViewModel, UUID.fromString(App.sharedPreferences.getString("id"))).let {
                if(it != null){
                    _myBeggerRank.value = it
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