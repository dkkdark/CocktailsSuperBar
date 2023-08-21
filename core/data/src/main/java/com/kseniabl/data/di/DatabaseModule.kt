package com.kseniabl.data.di

import android.content.Context
import androidx.room.Room
import com.kseniabl.data.local.dao.CocktailDao
import com.kseniabl.data.local.database.CocktailDatabase
import com.kseniabl.data.local.database.CocktailDatabase.Companion.DATABASE_NAME
import com.kseniabl.data.repository.CocktailDatabaseRepositoryImpl
import com.kseniabl.domain.repository.CocktailDatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CocktailDatabase =
        Room.databaseBuilder(
            context,
            CocktailDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideDao(database: CocktailDatabase): CocktailDao = database.cocktailsDao()

    @Provides
    @Singleton
    fun provideDatabaseRepository(dao: CocktailDao): CocktailDatabaseRepository =
        CocktailDatabaseRepositoryImpl(dao)
}