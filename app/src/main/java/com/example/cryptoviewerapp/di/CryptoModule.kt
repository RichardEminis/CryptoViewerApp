package com.example.cryptoviewerapp.di

import android.content.Context
import androidx.room.Room
import com.example.cryptoviewerapp.model.CryptoCurrencyDao
import com.example.cryptoviewerapp.model.CryptoCurrencyDatabase
import com.example.cryptoviewerapp.model.CryptoDetailsDao
import com.example.cryptoviewerapp.network.ApiService
import com.example.cryptoviewerapp.ulils.CRYPTO_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class CryptoModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CryptoCurrencyDatabase =
        Room.databaseBuilder(
            context,
            CryptoCurrencyDatabase::class.java, "recipes-database"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideCryptocurrenciesDao(appDatabase: CryptoCurrencyDatabase): CryptoCurrencyDao =
        appDatabase.cryptoCurrencyDao()

    @Provides
    fun provideCryptoDetailDao(appDatabase: CryptoCurrencyDatabase): CryptoDetailsDao =
        appDatabase.cryptoCurrencyDetailDao()

    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(logInterceptor).build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder()
            .baseUrl(CRYPTO_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
        return retrofit
    }

    @Provides
    fun provideRecipeApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}