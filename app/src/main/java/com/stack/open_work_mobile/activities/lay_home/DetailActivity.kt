package com.stack.open_work_mobile.activities.lay_home


import DetailAdapter
import ToolAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.stack.open_work_mobile.api.Rest
import com.stack.open_work_mobile.databinding.ActivityDetailBinding
import com.stack.open_work_mobile.models.ProjectDetailsModel
import com.stack.open_work_mobile.models.ToolsModel
import com.stack.open_work_mobile.services.ProjectService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: DetailAdapter
    private lateinit var adapterTool: ToolAdapter
    private lateinit var detailProject: ProjectDetailsModel
    private lateinit var list: ArrayList<ToolsModel>
    private var projetoId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list = ArrayList()
        detailProject = ProjectDetailsModel()
        projetoId = intent.getLongExtra("projetoId", 0L)
        Log.e("Debug", "Projeto ID: $projetoId")
        adapterTool = ToolAdapter(this, list)
        adapter = DetailAdapter(this, detailProject)
        binding.gridTools.adapter = adapterTool
        dataInitById()
    }

    private fun dataInitById() {
        if (projetoId != null) {
            val api = Rest.getInstance()?.create(ProjectService::class.java)
            val listTools: ArrayList<ToolsModel> = ArrayList()
            if (!::detailProject.isInitialized) {
                detailProject = ProjectDetailsModel()
            }

            api?.getProjectDetails(projetoId!!.toLong())?.enqueue(object: Callback<ProjectDetailsModel>{
                override fun onResponse(
                    call: Call<ProjectDetailsModel>,
                    response: Response<ProjectDetailsModel>
                ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                detailProject = it
                                listTools.addAll(it.tools)
                                adapter.updateData(it)
                            }
                            Log.e("Resposta", "Projeto Details: ${response.body()}")
                            list.addAll(listTools)
                            adapterTool.notifyDataSetChanged()
                            adapter.notifyDataSetChanged()
                        } else{
                            Log.e("Resposta", "Erro na resposta: ${response.code()}")
                        }
                }
                override fun onFailure(call: Call<ProjectDetailsModel>, t: Throwable) {
                    Log.e("Error", "Falha na requisição", t)
                }
            })
        } else {
            Log.e("Debug:", "Projeto selecionado valor de identificação nulo")
        }
    }
}
