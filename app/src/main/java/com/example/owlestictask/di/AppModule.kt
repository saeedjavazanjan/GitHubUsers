package com.example.owlestictask.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.owlestictask.common.Common
import com.example.owlestictask.repository.local.AppDatabase
import com.example.owlestictask.repository.remote.ApiService
import com.example.owlestictask.repository.remote.GetData
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun provideApi(
        client: OkHttpClient
    ): ApiService {
        return Retrofit.Builder()
            .baseUrl(Common.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }



    @Provides
    @Singleton
    fun provideGetData(
        apiService: ApiService
    ): GetData {
        return GetData(apiService)
    }






    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }


    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences {
        //return PreferenceManager.getDefaultSharedPreferences(context)
        return context.getSharedPreferences("SHARED", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPrefEditor(preferences: SharedPreferences): SharedPreferences.Editor {
        return preferences.edit()
    }

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "pipes"

    ).allowMainThreadQueries()
        .build()
}