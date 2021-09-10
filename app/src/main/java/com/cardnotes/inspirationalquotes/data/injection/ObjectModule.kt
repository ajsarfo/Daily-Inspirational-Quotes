package com.cardnotes.inspirationalquotes.data.injection

import com.cardnotes.inspirationalquotes.data.repository.Repository
import com.cardnotes.inspirationalquotes.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ObjectModule {

    @Singleton
    @Binds
    abstract fun repository(repository: RepositoryImpl): Repository
}