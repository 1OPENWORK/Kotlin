package com.stack.open_work_mobile.services


import com.stack.open_work_mobile.activities.lay_home.CardProjectHome
import com.stack.open_work_mobile.activities.lay_my_projects.ProjectProgressCard
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProjectService {

    @GET("/api/projetos-aceitos/andamento/desenvolvedor/{userId}")
    fun getAllProgress(@Path("userId") id: Long): Call<List<ProjectProgressCard>>

    @GET("/api/projetos-aceitos/completos/desenvolvedor/{userId}")
    fun getAllCompleted(@Path("userId") id: Long): Call<List<ProjectProgressCard>>

    @GET("/api/projetos-aceitos/cancelados/desenvolvedor/{userId}")
    fun getAllCanceled(@Path("userId") id: Long): Call<List<ProjectProgressCard>>

    @GET("/api/projetos/user/{userId}")
    fun getAllProjectsUserTools(@Path("userId") id: Long): Call<List<CardProjectHome>>

    @GET("/api/projetos")
    fun getAllProjects(): Call<List<CardProjectHome>>
}