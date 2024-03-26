package com.d208.giggy.viewmodel

import androidx.lifecycle.ViewModel
import com.d208.domain.model.DomainPostDetail
import com.d208.domain.model.DomainTransaction
import com.d208.domain.model.DomainUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() :  ViewModel(){

    var user = DomainUser()

    var fcmToken = ""

    var accessToken = ""

    var refreshToken = ""

    var selectedTransaction : DomainTransaction? = null
    var seletedPostId : Long = 0

    var postUpdateData : DomainPostDetail? = null



}