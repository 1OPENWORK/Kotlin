package com.stack.open_work_mobile.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.stack.open_work_mobile.Interface.OnAvaliarClickListener
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.adapters.ListAdapterMyRating
import com.stack.open_work_mobile.adapters.ListAdapterRatingCompany
import com.stack.open_work_mobile.api.Rest
import com.stack.open_work_mobile.models.RatingCompanies
import com.stack.open_work_mobile.services.AvaliationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RatingCompanyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RatingCompanyFragment : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var adapter: ListAdapterMyRating
    private lateinit var rating: ArrayList<RatingCompanies>
    private lateinit var recycleView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rating = ArrayList()
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rating_company, container, false)

        recycleView = view.findViewById(R.id.lvListRatingCompanies)

        recycleView.layoutManager = LinearLayoutManager(requireContext())

        recycleView.setHasFixedSize(true)

        adapter = ListAdapterMyRating(rating)

        recycleView.adapter = adapter

        return view

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FirstFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RatingCompanyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInit()
    }

    private fun dataInit() {
        val api = Rest.getInstance()?.create(AvaliationService::class.java)

        val userId =
            requireContext()
                .getSharedPreferences("IDENTIFY", Context.MODE_PRIVATE)
                .getLong("ID", 0)// Substitua pelo ID do desenvolvedor desejado

        api?.getAvaliationsDev(userId)?.enqueue(object : Callback<RatingCompanies> {
            override fun onResponse(
                call: Call<RatingCompanies>,
                response: Response<RatingCompanies>
            ) {
                if (response.isSuccessful) {
                    val avaliationListsDto = response.body()

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