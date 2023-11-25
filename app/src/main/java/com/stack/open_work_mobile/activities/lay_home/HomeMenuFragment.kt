package com.stack.open_work_mobile.activities.lay_home


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.api.Rest
import com.stack.open_work_mobile.services.ProjectService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var adapter: ProjectCardHomeAdapter
private lateinit var recyclerView: RecyclerView
private lateinit var projectCardProjectHomeList: ArrayList<CardProjectHome>


/**
 * A simple [Fragment] subclass.
 * Use the [HomeMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeMenuFragment : Fragment(), ProjectCardHomeAdapter.OnItemClickListener  {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        projectCardProjectHomeList = ArrayList()
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home_menu, container, false)
        recyclerView = view.findViewById(R.id.recycle_view_card_home)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        adapter = ProjectCardHomeAdapter(ArrayList())
        adapter.setOnItemClickListener(this)
        recyclerView.adapter = adapter

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeMenuFragment().apply {
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
        val api = Rest.getInstance()?.create(ProjectService::class.java)
        val list: ArrayList<CardProjectHome> = ArrayList()
        val userId =
            requireContext()
                .getSharedPreferences("IDENTIFY", Context.MODE_PRIVATE)
                .getLong("ID", 0)
        api?.getAllProjectsUserTools(userId)?.enqueue(object : Callback<List<CardProjectHome>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<CardProjectHome>>,
                response: Response<List<CardProjectHome>>
            ) {
                if (isAdded) {
                    if (response.isSuccessful) {
                        val projectList = response.body()

                        projectList?.forEach {
                            list.add(it)
                            adapter.notifyDataSetChanged()
                        }
                        projectCardProjectHomeList.addAll(list)
                        adapter.notifyDataSetChanged()
                    } else {
                        if (isAdded) Toast.makeText(
                            requireContext(),
                            response.message(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<CardProjectHome>>, t: Throwable) {
                if (isAdded) Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()

            }


        })

    }

    override fun onItemClick(position: Int) {
        Log.d("Debug", "Item clicado na posição: $position")
        if (isAdded && activity != null) {
            val projetoSelecionado = projectCardProjectHomeList[position]
            Log.d("Debug", "Projeto selecionado: ${projetoSelecionado.id}")
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("projetoId", projetoSelecionado.id)
            startActivity(intent)
        }
    }
}