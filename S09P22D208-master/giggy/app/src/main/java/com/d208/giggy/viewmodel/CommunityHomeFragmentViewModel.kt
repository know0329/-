package com.d208.giggy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d208.domain.model.DomainPost
import com.d208.domain.usecase.GetPostsUsecase
import com.d208.domain.usecase.PushLikeUsecase
import com.d208.domain.utils.ErrorType
import com.d208.domain.utils.RemoteErrorEmitter
import com.d208.giggy.di.App
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CommunityHomeFragmentViewModel @Inject constructor(
    private val getPostsUsecase: GetPostsUsecase,
    private val pushLikeUsecase: PushLikeUsecase,
) : ViewModel(), RemoteErrorEmitter {

    private val _postList = MutableLiveData<MutableList<DomainPost>>()
    val postList : LiveData<MutableList<DomainPost>> get() = _postList

    private val _filterdPostList = MutableLiveData<MutableList<DomainPost>>()
    val filteredPostList : LiveData<MutableList<DomainPost>> get() = _filterdPostList
    fun getPosts(){
        viewModelScope.launch {
            getPostsUsecase.execute(this@CommunityHomeFragmentViewModel, UUID.fromString(App.sharedPreferences.getString("id"))).let {
                response ->
                if(response != null){
                    _postList.value = response
                }
                else{
                    _postList.value = mutableListOf()
                }
            }
        }
    }
    fun pushLike(id : Long) {
        viewModelScope.launch {
            pushLikeUsecase.execute(this@CommunityHomeFragmentViewModel, id, UUID.fromString(App.sharedPreferences.getString("id")) )
        }
    }
    fun getPostsByPostType(postType : String){
        viewModelScope.launch {
            getPostsUsecase.executeFilter(this@CommunityHomeFragmentViewModel, UUID.fromString(App.sharedPreferences.getString("id")), postType).let{
                response->
                if(response != null){
                    _postList.value = response
                }
                else{
                    _postList.value = mutableListOf()
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