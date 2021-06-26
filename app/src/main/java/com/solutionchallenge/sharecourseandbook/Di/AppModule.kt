package com.solutionchallenge.sharecourseandbook.Di

import android.content.Context
import com.solutionchallenge.sharecourseandbook.Repository.FireStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFirestoreRepository()=FireStoreRepository()


    @Singleton
    @Provides
    fun provideApplicationContext(
        @ApplicationContext context: Context
    )=context

}