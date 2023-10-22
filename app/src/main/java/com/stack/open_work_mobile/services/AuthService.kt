package com.stack.open_work_mobile.services

import com.stack.open_work_mobile.models.authModel.AuthForm
import com.stack.open_work_mobile.models.authModel.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/usuarios/login")
    fun authentication(@Body form: AuthForm): Call<AuthResponse>

}