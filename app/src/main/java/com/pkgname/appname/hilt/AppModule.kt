package com.pkgname.appname.hilt

import android.content.Context
import com.pkgname.appname.backend.ApiService
import com.pkgname.appname.backend.BASE_URL.BASE_URL
import com.pkgname.appname.utils.SharePrefrenceHelper
import com.pkgname.appname.utils.SharedWebServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun okHttp(): OkHttpClient {
        val tlsSpecs = listOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT)
        return OkHttpClient.Builder()
            .connectionSpecs(tlsSpecs)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInterface(client: OkHttpClient): ApiService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiHelper(api: ApiService): SharedWebServices {
        return SharedWebServices(api)
    }

    @Provides
    @Singleton
    fun provideSharePrefrence(@ApplicationContext appContext: Context): SharePrefrenceHelper {
        return SharePrefrenceHelper.getInstance(appContext)
    }

    @Provides
    @Singleton
    fun context(@ApplicationContext appContext: Context): Context {
        return appContext
    }




}