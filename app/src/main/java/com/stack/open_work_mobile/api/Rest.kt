package com.stack.open_work_mobile.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import javax.net.ssl.SSLContext

object Rest {
    fun getInstance(): Retrofit? {
        val myTrust = MyTrustManager()
        val trustAllCertificates = arrayOf(myTrust)

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustAllCertificates, java.security.SecureRandom())

        val okHttpClient = OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, myTrust)
            .build()

        return Retrofit
            .Builder()
            .baseUrl("https://openwork.ddns.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}