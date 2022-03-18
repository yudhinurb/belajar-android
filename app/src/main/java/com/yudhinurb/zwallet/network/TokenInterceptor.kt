package com.yudhinurb.zwallet.network

import android.content.Context
import com.yudhinurb.zwallet.utils.KEY_USER_TOKEN
import com.yudhinurb.zwallet.utils.PREFS_NAME
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val tokenProvider: () -> String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val token = tokenProvider.invoke()
        if (token.isNotEmpty()){
            request.header("Authorization", "Bearer $token")
        }

        return chain.proceed(request.build())
    }
}