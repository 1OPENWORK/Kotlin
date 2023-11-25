package com.stack.open_work_mobile.services

import com.stack.open_work_mobile.models.ApiResponse
import com.stack.open_work_mobile.models.UpdateResponse
import com.stack.open_work_mobile.models.authModel.UpdateProfileModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProfileService {

    @GET("/api/usuarios/perfil/{userId}")
    fun getProfileInfo(@Path("userId") id: Long): Call<ApiResponse>

    @PUT("/api/usuarios/{userId}")
    fun putProfileInfo(@Path("userId") id: Long, @Body profile: UpdateProfileModel): Call<UpdateResponse>

    @DELETE("/api/usuarios/deletar/{userId}")
    fun deleteProfile(@Path("userId") id: Long): Call<String>
}