package com.d208.domain.repository

import com.d208.domain.model.DomainDuplicateCheck
import com.d208.domain.model.DomainUser
import com.d208.domain.model.SignUpUser
import com.d208.domain.utils.RemoteErrorEmitter
import java.util.UUID

interface MainRepository {
    suspend fun login(remoteErrorEmitter: RemoteErrorEmitter, accessToken : String, refreshToken : String, fcmToken : String) : DomainUser?

    suspend fun duplicateCheck(remoteErrorEmitter: RemoteErrorEmitter, user : DomainUser) : DomainDuplicateCheck?

    suspend fun signUp(remoteErrorEmitter: RemoteErrorEmitter, user : SignUpUser) : Boolean?

    suspend fun accountAuth(remoteErrorEmitter: RemoteErrorEmitter, accountNumber: String, fcmToken: String, birthday : String) : String?

    suspend fun getUserData(remoteErrorEmitter: RemoteErrorEmitter, user : DomainUser) : DomainUser?

    suspend fun updateTargetAmount(remoteErrorEmitter: RemoteErrorEmitter, id : UUID, targetAmount : Int) : Boolean?
}