package com.d208.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.d208.data.mapper.MainMapper
import com.d208.data.repository.remote.datasource.MainDataSource
import com.d208.domain.model.DomainDuplicateCheck
import com.d208.domain.model.DomainUser
import com.d208.domain.model.SignUpUser
import com.d208.domain.repository.MainRepository
import com.d208.domain.utils.RemoteErrorEmitter
import java.util.UUID
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainDataSource :MainDataSource

) : MainRepository {

    override suspend fun login(
        remoteErrorEmitter: RemoteErrorEmitter,
        accessToken: String,
        refreshToken: String,
        fcmToken: String
    ): DomainUser? {
        return MainMapper.loginMapper(mainDataSource.login(remoteErrorEmitter = remoteErrorEmitter, accessToken, refreshToken, fcmToken))
    }

    override suspend fun duplicateCheck(
        remoteErrorEmitter: RemoteErrorEmitter,
        user: DomainUser
    ): DomainDuplicateCheck? {
        return MainMapper.duplicateCheckMapper(mainDataSource.duplicateCheck(remoteErrorEmitter, user))
    }

    override suspend fun signUp(remoteErrorEmitter: RemoteErrorEmitter, user: SignUpUser): Boolean? {
        return MainMapper.signUpMapper(mainDataSource.signUp(remoteErrorEmitter, user))
    }

    override suspend fun accountAuth(
        remoteErrorEmitter: RemoteErrorEmitter,
        accountNumber: String,
        fcmToken: String,
        birthday: String
    ): String? {
        return mainDataSource.accountAuth(remoteErrorEmitter, accountNumber, fcmToken, birthday)
            ?.let { MainMapper.accountAuthMapper(it) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getUserData(
        remoteErrorEmitter: RemoteErrorEmitter,
        user: DomainUser
    ): DomainUser? {
        return MainMapper.getUseDataMapper(mainDataSource.getUserData(remoteErrorEmitter, user))
    }

    override suspend fun updateTargetAmount(
        remoteErrorEmitter: RemoteErrorEmitter,
        id: UUID,
        targetAmount: Int
    ): Boolean? {
        return mainDataSource.updateTargetAmount(remoteErrorEmitter, id, targetAmount)
    }


}