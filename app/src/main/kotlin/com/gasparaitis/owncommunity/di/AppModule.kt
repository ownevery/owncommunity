package com.gasparaitis.owncommunity.di

import com.gasparaitis.owncommunity.domain.alerts.usecase.AlertsUseCase
import com.gasparaitis.owncommunity.domain.shared.post.usecase.PostUseCase
import com.gasparaitis.owncommunity.domain.shared.profile.usecase.ProfileUseCase
import com.gasparaitis.owncommunity.domain.shared.story.usecase.StoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providesPostUseCase() = PostUseCase()

    @Provides
    fun providesStoryUseCase() = StoryUseCase()

    @Provides
    fun providesProfileUseCase() = ProfileUseCase()

    @Provides
    fun providesAlertsUseCase() = AlertsUseCase()
}
