package com.gasparaitis.owncommunity.di

import com.gasparaitis.owncommunity.domain.alerts.usecase.AlertsUseCase
import com.gasparaitis.owncommunity.domain.home.usecase.HomeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providesHomeUseCase() = HomeUseCase()

    @Provides
    fun providesAlertsUseCase() = AlertsUseCase()
}
