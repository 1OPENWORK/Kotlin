package com.stack.open_work_mobile.activities.lay_my_projects

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
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


private lateinit var adapter: ProjectProgressCardAdapter
private lateinit var recycleView: RecyclerView
private lateinit var projectCardCompleted: ArrayList<ProjectProgressCard>

private val api by lazy {
    Rest.getInstance()?.create(ProjectService::class.java)
}


/**
 * A simple [Fragment] subclass.
 * Use the [CompletedMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CompletedMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        projectCardCompleted = ArrayList()
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_completed_menu, container, false)
        recycleView = view.findViewById(R.id.recycle_view_completed)
        recycleView.layoutManager = LinearLayoutManager(requireContext())
        recycleView.setHasFixedSize(true)
        adapter = ProjectProgressCardAdapter(projectCardCompleted)
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
         * @return A new instance of fragment CompletedMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CompletedMenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    private fun loadData() {
        val list: ArrayList<ProjectProgressCard> = ArrayList()
        val userId =
            requireContext()
                .getSharedPreferences("IDENTIFY", MODE_PRIVATE)
                .getLong("ID", 0)

        api?.getAllCompleted(userId)?.enqueue(object : Callback<List<ProjectProgressCard>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<ProjectProgressCard>>,
                response: Response<List<ProjectProgressCard>>
            ) {
                if (isAdded) {
                    if (response.isSuccessful) {
                        val projectList = response.body()
                        projectList?.forEach { current ->
                            current.progress = 100.0
                            list.add(current)
                            adapter.notifyDataSetChanged()
                        }
                        projectCardCompleted.addAll(list)
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

            override fun onFailure(call: Call<List<ProjectProgressCard>>, t: Throwable) {
                if (isAdded) Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}