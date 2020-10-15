package com.fortmart.customer.di

import android.app.Application
import androidx.room.Room
import com.fortmart.customer.R
import com.fortmart.customer.data.dao.UserDao
import com.fortmart.customer.data.db.MainDatabase
import com.fortmart.customer.data.network.AuthInterceptor
import com.fortmart.customer.data.network.BaseApiService
import com.fortmart.customer.data.network.LoginApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideLoginApiInterface(retrofit: Retrofit): LoginApiService {
        return retrofit.create(LoginApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBaseApiInterface(retrofit: Retrofit): BaseApiService {
        return retrofit.create(BaseApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, app: Application): Retrofit {
        return Retrofit.Builder()
            .baseUrl(app.getString(R.string.base_url))
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, userDao: UserDao): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(AuthInterceptor(userDao))
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    fun provideMainDatabase(app: Application): MainDatabase {
        return Room.databaseBuilder(app, MainDatabase::class.java, "taxi_da_database")
            .build()
    }

    @Provides
    fun provideUserDao(mainDatabase: MainDatabase): UserDao {
        return mainDatabase.userDao()
    }
}