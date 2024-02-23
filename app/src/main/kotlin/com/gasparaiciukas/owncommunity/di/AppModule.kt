package com.gasparaiciukas.owncommunity.di

import com.gasparaiciukas.owncommunity.domain.alerts.usecase.AlertListUseCase
import com.gasparaiciukas.owncommunity.domain.shared.post.usecase.PostUseCase
import com.gasparaiciukas.owncommunity.domain.shared.profile.usecase.ProfileUseCase
import com.gasparaiciukas.owncommunity.domain.shared.story.usecase.StoryUseCase
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
    fun providesAlertsUseCase() = AlertListUseCase()
}
