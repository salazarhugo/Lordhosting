package com.salazar.lordhosting.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.salazar.lordhosting.Settings
import com.salazar.lordhosting.account.data.repository.AccountRepository
import com.salazar.lordhosting.account.data.repository.AccountRepositoryImpl
import com.salazar.lordhosting.auth.data.api.PterodactylApi
import com.salazar.lordhosting.auth.data.interceptor.CookieInterceptor
import com.salazar.lordhosting.auth.data.interceptor.SetHeaderInterceptor
import com.salazar.lordhosting.auth.data.repository.AuthRepository
import com.salazar.lordhosting.auth.data.repository.AuthRepositoryImpl
import com.salazar.lordhosting.core.data.datastore.settingsDataStore
import com.salazar.lordhosting.server.data.repository.ServerRepository
import com.salazar.lordhosting.server.data.repository.ServerRepositoryImpl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providePterodactylApi(
        setHeaderInterceptor: SetHeaderInterceptor,
        cookieInterceptor: CookieInterceptor,
    ): PterodactylApi {
        val moshi = Moshi.Builder().build()

        val client = OkHttpClient.Builder().build()

        val okHttpClient: OkHttpClient = client.newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(cookieInterceptor)
            .addInterceptor(setHeaderInterceptor)
            .build()

        val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl(PterodactylApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

        return retrofit.create(PterodactylApi::class.java)
    }


    @Provides
    fun provideAccountRepository(
        impl: AccountRepositoryImpl,
    ): AccountRepository {
        return impl
    }

    @Provides
    fun provideServerRepository(
        impl: ServerRepositoryImpl,
    ): ServerRepository {
        return impl
    }

    @Provides
    fun provideAuthRepository(
        impl: AuthRepositoryImpl,
    ): AuthRepository {
        return impl
    }

    @Singleton
    @Provides
    fun provideSettingsDataStore(@ApplicationContext context: Context): DataStore<Settings> {
        return context.settingsDataStore
    }

}