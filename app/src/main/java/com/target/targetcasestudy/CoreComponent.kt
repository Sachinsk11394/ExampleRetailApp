package com.target.targetcasestudy

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.target.targetcasestudy.data.DealsActivityRepository
import com.target.targetcasestudy.data.DealsViewModelFactory
import com.target.targetcasestudy.data.DealsWebService
import com.target.targetcasestudy.ui.DealListFragment
import com.target.targetcasestudy.utils.BaseUrl
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Component(modules = [CoreModule::class])
internal interface CoreComponent {
    fun injectMainActivity(activity: MainActivity)
}

@Module
class CoreModule(val context: Context) {

    @Provides
    fun getMainActivityViewModelFactory(repository: DealsActivityRepository): DealsViewModelFactory {
        return DealsViewModelFactory(repository)
    }

    @Provides
    fun getMainActivityRepository(webService: DealsWebService): DealsActivityRepository {
        return DealsActivityRepository(webService)
    }

    @Provides
    fun getMainActivityWebService(retrofit: Retrofit): DealsWebService = retrofit.create(DealsWebService::class.java)

    @Provides
    fun getMoshi(): Moshi {
        val moshi = Moshi.Builder()
        moshi.add(KotlinJsonAdapterFactory())
        return moshi.build()
    }

    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient, moshi: Moshi, baseUrl: BaseUrl): Retrofit {
        return  Retrofit.Builder()
            .baseUrl(baseUrl.value)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    fun getOkHttp(): OkHttpClient {
        val cacheInterceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                request = if (hasNetwork())
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 30).build()
                else
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                return chain.proceed(request)
            }
        }
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(cacheInterceptor)
        val isDebugBuild = false // get this value from build config
        if(isDebugBuild) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }
        return builder.build()
    }

    @Provides
    fun getBaseUrl(): BaseUrl {
        return BaseUrl("https://api.target.com/mobile_case_study_deals/v1/")
    }

    fun hasNetwork(): Boolean {
        var isConnected: Boolean? = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected ?: false
    }
}