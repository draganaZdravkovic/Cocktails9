package com.example.cocktails9.di

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.example.cocktails9.data.local.FavoritesDao
import com.example.cocktails9.data.local.FavoritesDatabase
import com.example.cocktails9.data.remote.ApiInterface
import com.example.cocktails9.data.repository.CocktailsRepository
import com.example.cocktails9.data.repository.FavoritesRepository
import com.example.cocktails9.data.repository.FilterRepository
import com.example.cocktails9.ui.fragment.cocktails.CocktailsFragment
import com.example.cocktails9.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        ).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideCocktailsRepository(apiInterface: ApiInterface, favoritesDao: FavoritesDao): CocktailsRepository =
        CocktailsRepository(apiInterface, favoritesDao)

    @Provides
    @Singleton
    fun provideFavoritesRepository(favoritesDao: FavoritesDao): FavoritesRepository =
        FavoritesRepository(favoritesDao)

    @Provides
    @Singleton
    fun provideFilterRepository(apiInterface: ApiInterface): FilterRepository =
        FilterRepository(apiInterface)


    @Provides
    @Singleton
    fun provideResources(@ApplicationContext context: Context): Resources =
        context.resources

    @Provides
    @Singleton
    fun provideFavoritesDao(database: FavoritesDatabase): FavoritesDao = database.favoritesDao()

    @Provides
    @Singleton
    fun provideFavoritesDatabase(@ApplicationContext context: Context): FavoritesDatabase =
        Room.databaseBuilder(context, FavoritesDatabase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
}