package com.salazar.lordhosting.auth.data.interceptor

import com.salazar.lordhosting.core.data.datastore.DataStoreRepositoryImpl
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.lang.String.format
import javax.inject.Inject


class SetHeaderInterceptor @Inject constructor(
    private val dataStoreRepository: DataStoreRepositoryImpl,
): Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        return try {
            val cookies = runBlocking {
                dataStoreRepository.getCookie()
            }
            val apiKey = runBlocking {
                dataStoreRepository.getApiKey()
            }
            val xsrfToken = runBlocking {
                dataStoreRepository.getXSRFToken()
            }

            val modifiedRequest: Request = request.newBuilder()
                .addHeader(X_AUTHORIZATION, format("Bearer %s", apiKey))
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
//                .apply {
//                    cookies.forEach {  cookie ->
//                        addHeader(COOKIE, cookie)
//                    }
//                }
                .addHeader(X_XSRF_TOKEN, xsrfToken)
                .build()
            chain.proceed(modifiedRequest)
        } catch (e: Exception) {
            throw IOException(e.message)
        }
    }

    companion object {
        private const val X_AUTHORIZATION = "Authorization"
        private const val COOKIE = "cookie"
        private const val X_XSRF_TOKEN = "x-xsrf-token"
    }
}