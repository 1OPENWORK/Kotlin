package com.stack.open_work_mobile.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.adapters.ListAdapterRatingCompany
import com.stack.open_work_mobile.models.RatingCompanies

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RatingCompanyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RatingCompanyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var rating: ArrayList<RatingCompanies>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_rating_company, container, false)

        val tvId: IntArray = intArrayOf(1, 2, 3, 4, 5)

        val tvEmpresa: Array<String> =
            arrayOf("OpenWork", "OpenWork 2", "OpenWork 3", "OpenWork 4", "OpenWork 5")

        val tvData: Array<String> =
            arrayOf("10/09/2023", "11/09/2023", "12/09/2023", "13/09/2023", "14/09/2023")

        val tvEstrela: Array<String> = arrayOf("3", "4", "5", "2", "3")

        // Resto do seu código para inicializar as listas

        rating = ArrayList()

        for (i in tvId.indices) {
            val ratings =
                RatingCompanies(
                    tvId[i],
                    tvEmpresa[i],
                    tvData[i],
                    tvEstrela[i],
                    "Criar plataforma freelancer do zero, backend, frontend, bd conectado na nuget, e aplicação mobile."
                )
            rating.add(ratings)
        }

        val lvListaRatingCompanies: ListView? = view.findViewById(R.id.lvListRatingCompanies)
        lvListaRatingCompanies?.adapter = ListAdapterRatingCompany(requireActivity(), rating)

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
}