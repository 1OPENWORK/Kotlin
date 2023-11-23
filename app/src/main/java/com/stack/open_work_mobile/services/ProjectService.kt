package com.stack.open_work_mobile.services


import com.stack.open_work_mobile.activities.lay_home.CardProjectHome
import com.stack.open_work_mobile.activities.lay_my_projects.ProjectProgressCard
import com.stack.open_work_mobile.models.CandForm
import com.stack.open_work_mobile.models.ProjectDetailsModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProjectService {

    @GET("/api/projetos/projeto/{projectId}")
    fun getProjectDetails(@Path("projectId") id: Long): Call<ProjectDetailsModel>
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

    @POST("/api/propostas/desenvolvedor/proposta/{userId}")
    fun postCand(@Path("userId")id: Long, @Body form: CandForm): Call<CandForm>
}