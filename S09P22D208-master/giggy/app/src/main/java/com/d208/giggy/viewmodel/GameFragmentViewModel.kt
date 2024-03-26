package com.d208.giggy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d208.domain.model.DomainGameRank
import com.d208.domain.model.DomainMyGame
import com.d208.domain.usecase.GameRankUsecase
import com.d208.domain.utils.ErrorType
import com.d208.domain.utils.RemoteErrorEmitter
import com.d208.giggy.di.App
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class GameFragmentViewModel @Inject constructor(
    private val gameRankUsecase: GameRankUsecase
) : ViewModel(), RemoteErrorEmitter {

    private val _gameRankList = MutableLiveData<List<DomainGameRank>>()
    val gameRankList : LiveData<List<DomainGameRank>> get() = _gameRankList

    private val _myGameRank = MutableLiveData<DomainMyGame>()
    val myGameRank : LiveData<DomainMyGame> get() = _myGameRank
    fun getGameRank() {
        viewModelScope.launch {
            gameRankUsecase.execute(this@GameFragmentViewModel).let {
                if(!it.isNullOrEmpty()){
                    _gameRankList.value = it
                }
                else{
                    _gameRankList.value = mutableListOf()
                }
            }
        }
    }

    fun getMyGameRank() {
        viewModelScope.launch {
            gameRankUsecase.executeMyGameRank(this@GameFragmentViewModel, UUID.fromString(App.sharedPreferences.getString("id"))).let {
                if(it != null){
                    _myGameRank.value = it
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