package com.stack.open_work_mobile.services

import com.stack.open_work_mobile.models.ProfileModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileService {

    @GET("/api/usuarios/perfil/{userId}")
    fun getProfileInfo(@Path("userId") id: Long): Call<ProfileModel>
}