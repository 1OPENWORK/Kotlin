package com.stack.open_work_mobile.api

import android.annotation.SuppressLint
import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager


@SuppressLint("CustomX509TrustManager")
class MyTrustManager : X509TrustManager {
    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {
    }

    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return arrayOf()
    }

}