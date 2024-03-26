package com.d208.data.remote.api

import android.accounts.Account
import com.d208.data.remote.model.AccountAuthData
import com.d208.data.remote.model.AccountAuthResponse
import com.d208.data.remote.model.LoginData
import com.d208.data.remote.model.LoginUser
import com.d208.data.remote.model.TargetAmountChangeRequest
import com.d208.domain.model.DomainUser
import com.d208.domain.model.SignUpUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApi {

    // 로그인
    @POST("app/user/login")
    suspend fun login(
        @Body data : LoginData,
    ) : Response<LoginUser>

    // 닉네임중복체크
    @POST("app/user/nickname")
    suspend fun duplicateNickNameCheck(
        @Body data :DomainUser,
    ) : Response<Boolean>
    //목표 소비액 변경
    @PUT("app/user/targetamount")
    suspend fun updateTargetAmount(
        @Body data : TargetAmountChangeRequest,
    ) : Response<Boolean>
    //회원가입
    @POST("app/user/signup")
    suspend fun signUp(
        @Body data : SignUpUser,

    ) : Response<Boolean>

    //계좌 인증
    @POST("app/bank/auth")
    suspend fun accountAuth(
        @Body data : AccountAuthData,
    ) : Response<AccountAuthResponse>

    @POST("app/user/info")
    suspend fun getUserInfo(
        @Body data : DomainUser,
    ) : Response<LoginUser>


}