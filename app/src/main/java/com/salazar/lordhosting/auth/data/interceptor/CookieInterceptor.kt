package com.salazar.lordhosting.auth.data.interceptor

import com.salazar.lordhosting.core.data.datastore.DataStoreRepositoryImpl
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject


class CookieInterceptor @Inject constructor(
    private val dataStoreRepository: DataStoreRepositoryImpl,
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        return try {
            val response = chain.proceed(request)
            val cookie = response.headers(SET_COOKIE)
            cookie.firstOrNull()?.let { it ->
                val token = it.substringAfter("XSRF-TOKEN=").substringBefore(";").replace("%3D", "=")

                runBlocking {
                    cookie.getOrNull(1)?.let { cookieSession ->
                        dataStoreRepository.updateCookie(listOf(it, cookieSession))
                    }
                    dataStoreRepository.updateXSRFToken(token = token)
                }
            }
            response
        } catch (e: Exception) {
            throw IOException(e.message)
        }
    }

    companion object {
        private const val SET_COOKIE = "Set-Cookie"
    }
}