package com.stack.open_work_mobile.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.RatingBar
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.adapters.ListAdapterMyRating
import com.stack.open_work_mobile.adapters.ListAdapterRatingCompany
import com.stack.open_work_mobile.models.MyRating

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyCompanyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyCompanyFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var rating: ArrayList<MyRating>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_rating, container, false)

        val tvId: IntArray = intArrayOf(1, 2, 3, 4, 5)

        val tvEmpresa: Array<String> =
            arrayOf("OpenWork", "OpenWork 2", "OpenWork 3", "OpenWork 4", "OpenWork 5")

        val tvData: Array<String> =
            arrayOf("10/09/2023", "11/09/2023", "12/09/2023", "13/09/2023", "14/09/2023")

        val tvEstrela: Array<String> = arrayOf("3", "4", "5", "2", "3")

        val rbRatingBar: RatingBar
        val btnAvaliar: Button


        // Resto do seu código para inicializar as listas

        rating = ArrayList()

        for (i in tvId.indices) {
            val ratings = MyRating(
                tvId[i],
                tvEmpresa[i],
                tvData[i],
                tvEstrela[i],
                "Criar plataforma freelancer do zero, backend, frontend, bd conectado na nuget, e aplicação mobile."
            )

            // Configurar o RatingBar
            ratings.rating = RatingBar(context)
            ratings.rating?.numStars = 5
            ratings.rating?.rating = tvEstrela[i].toFloat()

            // Configurar o Button
            ratings.button = Button(context)
            ratings.button?.text = "Avaliar"

            // Configurar ouvintes de eventos para o RatingBar e o Button, se necessário
            ratings.rating?.setOnRatingBarChangeListener { _, rating, _ ->
                // Lidar com a mudança de classificação, se necessário
            }

            ratings.button?.setOnClickListener {
                // Lidar com o clique no botão, se necessário
            }

            rating.add(ratings)
        }


        val lvListMyRating: ListView? = view.findViewById(R.id.lvListMyRating)
        lvListMyRating?.adapter = ListAdapterMyRating(requireActivity(), rating)

        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyCompanyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}