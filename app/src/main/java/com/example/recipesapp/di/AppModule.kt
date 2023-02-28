package com.example.recipesapp.di

import android.content.Context
import androidx.room.Room
import com.example.recipesapp.db.RecipesAppDataBase
import com.example.recipesapp.db.RecipesDao
import com.example.recipesapp.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesBaseUrl(): String = "https://api.spoonacular.com/"

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): RecipesAppDataBase {
        return Room.databaseBuilder(
            context,
            RecipesAppDataBase::class.java, "appDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRecipesDao(db: RecipesAppDataBase): RecipesDao {
        return db.getRecipesDao()
    }

}