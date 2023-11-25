package com.stack.open_work_mobile.activities.lay_home


import DetailAdapter
import ToolAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.stack.open_work_mobile.R

import com.stack.open_work_mobile.api.Rest
import com.stack.open_work_mobile.databinding.ActivityDetailBinding
import com.stack.open_work_mobile.models.CandForm
import com.stack.open_work_mobile.models.ProjectDetailsModel
import com.stack.open_work_mobile.models.ToolsModel
import com.stack.open_work_mobile.services.ProjectService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: DetailAdapter
    private lateinit var cand: CandForm
    private lateinit var adapterTool: ToolAdapter
    private var detailProject: ProjectDetailsModel = ProjectDetailsModel()
    private lateinit var list: ArrayList<ToolsModel>
    private var projetoId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list = ArrayList()
        projetoId = intent.getLongExtra("projetoId", 0L)
        Log.e("Debug", "Projeto ID: $projetoId")
        adapterTool = ToolAdapter(this, list)
        adapter = DetailAdapter(this, detailProject)
        binding.gridTools.adapter = adapterTool
        binding.arrowBack.setOnClickListener{
            startActivity(Intent(this, HomeActivity::class.java))
        }
        binding.btnCand.setOnClickListener{
            click()
        }
        dataInitById()
    }

    private fun click(){
        if (projetoId != null) {
            val api = Rest.getInstance()?.create(ProjectService::class.java)
            cand = CandForm(projetoId!!, "BIG")
            val userId = this.getSharedPreferences("IDENTIFY", MODE_PRIVATE).getLong("ID", 0)
            api?.postCand(userId, cand)?.enqueue(object: Callback<CandForm>{
                override fun onResponse(
                    call: Call<CandForm>,
                    response: Response<CandForm>
                ) {

                    if (response.isSuccessful) {
                        Toast.makeText(
                            baseContext,
                            "Aplicado ao projeto",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else{
                        Log.e("Resposta", "Erro na resposta: ${response.code()}")
                    }
                }
                override fun onFailure(call: Call<CandForm>, t: Throwable) {
                    Log.e("Error", "Falha na requisição", t)
                }
            })
        } else {
            Log.e("Debug:", "Projeto selecionado valor de identificação nulo")
        }
    }

    private fun dataInitById() {
        if (projetoId != null) {
            val api = Rest.getInstance()?.create(ProjectService::class.java)
            val listTools: ArrayList<ToolsModel> = ArrayList()

            api?.getProjectDetails(projetoId!!.toLong())?.enqueue(object: Callback<ProjectDetailsModel>{
                override fun onResponse(
                    call: Call<ProjectDetailsModel>,
                    response: Response<ProjectDetailsModel>
                ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                val detail = response.body()
                                val imageCompany: ShapeableImageView = findViewById(R.id.image_company)
                                val titleProject: TextView = findViewById(R.id.view_project_name)
                                val nameCompany: TextView = findViewById(R.id.company_name)
                                val descriptionProject: TextView = findViewById(R.id.view_detail_company)
                                val maxDevelopers: TextView = findViewById(R.id.view_contribu)
                                val timeExpected: TextView = findViewById(R.id.date_end)
                                val value: TextView = findViewById(R.id.value)


                                Glide.with(baseContext)
                                    .load(detail?.imageCompany)
                                    .into(imageCompany)
                                titleProject.text = detail?.title
                                nameCompany.text = detail?.nameCompany
                                descriptionProject.text = detail?.description
                                maxDevelopers.text = detail?.maxDevelopers.toString() + " Integrantes"
                                timeExpected.text = detail?.timeExpected.toString()
                                value.text = detail?.value.toString()
                                detailProject = it
                                listTools.addAll(it.tools)
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
