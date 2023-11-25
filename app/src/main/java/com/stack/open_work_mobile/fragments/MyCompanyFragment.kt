package com.stack.open_work_mobile.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stack.open_work_mobile.Interface.OnAvaliarClickListener
import com.stack.open_work_mobile.MyFirebaseMessagingService
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.adapters.ListAdapterRatingCompany
import com.stack.open_work_mobile.api.Rest
import com.stack.open_work_mobile.models.RatingCompanies
import com.stack.open_work_mobile.services.AvaliationService
import com.stack.open_work_mobile.utils.NotificationUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyCompanyFragment : Fragment(), OnAvaliarClickListener {

    private lateinit var rating: ArrayList<RatingCompanies>
    private lateinit var recycleView: RecyclerView
    private lateinit var adapter: ListAdapterRatingCompany
    private lateinit var fragmentContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rating = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_rating, container, false)

        recycleView = view.findViewById(R.id.lvListMyRating)

        recycleView.layoutManager = LinearLayoutManager(requireContext())

        recycleView.setHasFixedSize(true)

        fragmentContext = requireContext()
        adapter = ListAdapterRatingCompany(rating, this)

        recycleView.adapter = adapter

        return view
    }


    companion object {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInit()
    }

    override fun onAvaliarClick(id: Long, nome: String, grade: Int) {
        val api = Rest.getInstance()?.create(AvaliationService::class.java)
        val userId =
            requireContext()
                .getSharedPreferences("IDENTIFY", Context.MODE_PRIVATE)
                .getLong("ID", 0)


        val call = api?.registerAvaliationDeveloper(
            id,
            grade
        )


        call?.enqueue(object : Callback<RatingCompanies> {
            override fun onResponse(
                call: Call<RatingCompanies>,
                response: Response<RatingCompanies>
            ) {
                if (response.isSuccessful) {
                    Log.e("API Success", "Sucesso!")
                    dataInit()
                    val titulo = "Avaliação concluída!"
                    val message = "Sua Avaliação ${grade} para ${nome}"
                    MyFirebaseMessagingService.sendNotificationAvaliation(
                        fragmentContext,
                        titulo,
                        message
                    )
                    NotificationUtils.salvarNotificacao(fragmentContext,titulo,  message, R.drawable.sininho)
                } else {
                    Log.e("API Error", "Falha ao enviar a avaliação ${grade}")
                }
            }

            override fun onFailure(call: Call<RatingCompanies>, t: Throwable) {
                Log.e("API Error", "Erro na chamada")
            }
        })
    }


    private fun dataInit() {
        val api = Rest.getInstance()?.create(AvaliationService::class.java)
        val list: ArrayList<RatingCompanies> = ArrayList()
        val userId =
            requireContext()
                .getSharedPreferences("IDENTIFY", Context.MODE_PRIVATE)
                .getLong("ID", 0)
        api?.getAvaliationsDev(userId)?.enqueue(object : Callback<RatingCompanies> {
            override fun onResponse(
                call: Call<RatingCompanies>,
                response: Response<RatingCompanies>
            ) {
                if (response.isSuccessful) {
                    val avaliationListsDto = response.body()
                    Log.e("API Success", "Sucesso! $avaliationListsDto")
                    if (avaliationListsDto != null) {
                        val evaluates = avaliationListsDto.evaluates
                        for (evaluate in evaluates) {
                            rating.add(avaliationListsDto)
                            adapter.notifyDataSetChanged()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), response.message(), Toast.LENGTH_LONG).show()
                    print("Message: ${response.message()}\n" + "Error Body: ${response.errorBody()}\n" + "Header: ${response.headers()}")
                }
            }

            override fun onFailure(call: Call<RatingCompanies>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
            }
        })
    }


}