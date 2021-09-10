package com.cardnotes.inspirationalquotes.data.repository

import com.cardnotes.inspirationalquotes.data.database.Database

interface Repository {

    fun database() : Database
    suspend fun setupDatabase(repositoryListener: RepositoryListener)
    suspend fun isDatabaseCreated() : Boolean

    interface RepositoryListener {
        suspend fun check()
    }
}