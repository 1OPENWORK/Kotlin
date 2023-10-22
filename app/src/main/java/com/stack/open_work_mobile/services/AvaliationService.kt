package com.stack.open_work_mobile.services

import com.stack.open_work_mobile.models.RatingCompanies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AvaliationService {

    @GET("/api/avaliacoes/desenvolvedor/{id}")
    fun getAvaliationsDev(@Path("id") id: Int): Call<RatingCompanies>

    @GET("/api/avaliacoes/empresa/{id}")
    fun getAvaliationsCompany(@Path("id") id: Int): Call<RatingCompanies>

    @POST("/api/empresa/{id}/{grade}")
    fun registerAvaliationDeveloper(
        @Path("id") id: Int,
        @Path("grade") grade: Int
    ): Call<RatingCompanies>


}