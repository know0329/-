package com.d208.giggy.di

import com.d208.domain.repository.BankRepository
import com.d208.domain.repository.MainRepository
import com.d208.domain.repository.PostRepository
import com.d208.domain.repository.RankRepository
import com.d208.domain.usecase.AnalysisUsecase
import com.d208.domain.usecase.BeggerRankUsecase
import com.d208.domain.usecase.DeleteCommentUsecase
import com.d208.domain.usecase.DeletePostUsecase
import com.d208.domain.usecase.DuplicateCheckUsecase
import com.d208.domain.usecase.GameRankUsecase
import com.d208.domain.usecase.GetMonthsUsecase
import com.d208.domain.usecase.GetOnePostUsecase
import com.d208.domain.usecase.GetPostsUsecase
import com.d208.domain.usecase.LoginUsecase
import com.d208.domain.usecase.PostUpdateUsecase
import com.d208.domain.usecase.RegisterCommentUsecase
import com.d208.domain.usecase.RegisterPostUsecase
import com.d208.domain.usecase.TransactionUsecase
import com.d208.domain.usecase.UpdateTargetAmountUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(repository : MainRepository) =  LoginUsecase(repository)

    @Provides
    @Singleton
    fun provideDuplicateUseCase(repository : MainRepository) =  DuplicateCheckUsecase(repository)

    @Provides
    @Singleton
    fun provideTransactionUseCase(repository: BankRepository) = TransactionUsecase(repository)

    @Provides
    @Singleton
    fun provideGetMonthsUseCase(repository: BankRepository) = GetMonthsUsecase(repository)

    @Provides
    @Singleton
    fun provideGetAnalysisUsecase(repository: BankRepository) = AnalysisUsecase(repository)

    @Provides
    @Singleton
    fun provideRegisterPostUsecase(repository : PostRepository) = RegisterPostUsecase(repository)

    @Provides
    @Singleton
    fun provideGetPostsUsecase(repository: PostRepository) = GetPostsUsecase(repository)

    @Provides
    @Singleton
    fun provideGetOnePostUsecase(repository: PostRepository) = GetOnePostUsecase(repository)

    @Provides
    @Singleton
    fun provideUpdatePostUsecase(repository: PostRepository) = PostUpdateUsecase(repository)

    @Provides
    @Singleton
    fun provideRegisterCommentUsecase(repository: PostRepository) = RegisterCommentUsecase(repository)

    @Provides
    @Singleton
    fun provideDeleteCommentUsecase(repository: PostRepository) = DeleteCommentUsecase(repository)

    @Provides
    @Singleton
    fun provideDeletePostUsecase(repository: PostRepository) = DeletePostUsecase(repository)

    @Provides
    @Singleton
    fun provideBeggerRankUsecase(repository : RankRepository) = BeggerRankUsecase(repository)

    @Provides
    @Singleton
    fun provideGameRankUsecase(repository: RankRepository) = GameRankUsecase(repository)

    @Provides
    @Singleton
    fun provideUpdateTargetAmountUsecase(repository: MainRepository) = UpdateTargetAmountUsecase(repository)
}