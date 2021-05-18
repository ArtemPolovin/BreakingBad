package com.example.data.di

import com.example.data.mappers.BreakingBadMapper
import com.example.data.network.BreakingBadApi
import com.example.data.repositoryimpl.BreakingBadRepositoryImpl
import com.example.domain.repositories.BreakingBadRepository
import com.example.domain.usecases.GetBreakingBadCharactersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBreakingBadRepository(
        api: BreakingBadApi,
        mapper: BreakingBadMapper
    ): BreakingBadRepository = BreakingBadRepositoryImpl(api, mapper)

    @Provides
    @Singleton
    fun provideBreakingBadApi() = BreakingBadApi()

    @Provides
    fun provideBreakingBadMapper() = BreakingBadMapper()

    @Provides
    fun provideGetBreakingBadCharactersUseCase(breakingBadRepository: BreakingBadRepository) =
        GetBreakingBadCharactersUseCase(breakingBadRepository)

}