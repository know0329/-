package com.d208.data.repository.remote.datasource

import com.d208.data.remote.model.AccountAuthResponse
import com.d208.data.remote.model.LoginUser
import com.d208.domain.model.DomainUser
import com.d208.domain.model.SignUpUser
import com.d208.domain.utils.RemoteErrorEmitter
import java.util.UUID

interface MainDataSource {
    suspend fun login(
        remoteErrorEmitter: RemoteErrorEmitter,
        accessToken: String,
        refreshToken : String,
        fcmToken : String,
        ): LoginUser?

    suspend fun duplicateCheck(
        remoteErrorEmitter: RemoteErrorEmitter,
        user : DomainUser,
    ) : Boolean?

    suspend fun signUp(
        remoteErrorEmitter: RemoteErrorEmitter,
        user : SignUpUser,
    ) : Boolean?

    suspend fun accountAuth(
        remoteErrorEmitter: RemoteErrorEmitter,
        accountNumber : String,
        fcmToken : String,
        birthday : String,
    ) : AccountAuthResponse?

    suspend fun getUserData(
        remoteErrorEmitter: RemoteErrorEmitter,
        user : DomainUser
    ) : LoginUser?

    suspend fun updateTargetAmount(
        remoteErrorEmitter: RemoteErrorEmitter,
        id : UUID,
        targetAmount : Int,
    ) : Boolean?
}