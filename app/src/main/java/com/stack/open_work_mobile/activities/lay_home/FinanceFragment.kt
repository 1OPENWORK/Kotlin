package com.stack.open_work_mobile.activities.lay_home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.activities.lay_my_projects.ProjectProgressCard
import com.stack.open_work_mobile.api.Rest
import com.stack.open_work_mobile.databinding.FragmentFinanceBinding
import com.stack.open_work_mobile.services.ProjectService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat


class FinanceFragment : Fragment() {
    private var financeService: ProjectService? = null
    private lateinit var binding: FragmentFinanceBinding
    private lateinit var linearLayoutParent: LinearLayout
    private val userID by lazy {
        requireContext()
            .getSharedPreferences("IDENTIFY", Context.MODE_PRIVATE)
            .getLong("ID", 0)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFinanceBinding.inflate(inflater, container, false)
        val view = binding.root

        linearLayoutParent = view.findViewById(R.id.cardPai)

        val retrofit = Rest.getInstance()
        if (retrofit != null) {
            financeService = retrofit.create(ProjectService::class.java)
        } else {
            "Erro no retrofit ou ec2"
        }

        binding.recebidoIcone.setOnClickListener {
            binding.recebidoIcone.setBackgroundResource(R.drawable.green_button)
            binding.receberIcone.setBackgroundResource(R.drawable.bg_black_button)
            binding.perdidoIcone.setBackgroundResource(R.drawable.bg_black_button)
            linearLayoutParent?.removeAllViews()
            binding.subtituloCards.text = "Recebido"
            buscarProjetosRecebidos()
        }

        binding.receberIcone.setOnClickListener {
            binding.receberIcone.setBackgroundResource(R.drawable.green_button)
            binding.recebidoIcone.setBackgroundResource(R.drawable.bg_black_button)
            binding.perdidoIcone.setBackgroundResource(R.drawable.bg_black_button)
            binding.subtituloCards.text = "A Receber"
            linearLayoutParent.removeAllViews()
            buscarProjetosReceber()

        }

        binding.perdidoIcone.setOnClickListener {
            binding.perdidoIcone.setBackgroundResource(R.drawable.green_button)
            binding.recebidoIcone.setBackgroundResource(R.drawable.bg_black_button)
            binding.receberIcone.setBackgroundResource(R.drawable.bg_black_button)
            linearLayoutParent?.removeAllViews()
            binding.subtituloCards.text = "Perdido"
            buscarProjetosCancelados()

        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buscarProjetosRecebidos()
    }


    private fun buscarProjetosReceber() {
        if (financeService == null) {
            exibirSnackbar("Problema na service, NULA")
            return
        }
        Log.d("Id:", "$userID")

        val call = financeService!!.getAllProgress(userID)

        call.enqueue(object : Callback<List<ProjectProgressCard>> {
            override fun onResponse(
                call: Call<List<ProjectProgressCard>>,
                response: Response<List<ProjectProgressCard>>
            ) {
                if (response.isSuccessful) {
                    val financeModelList = response.body()

                    linearLayoutParent?.removeAllViews()
                    if (!financeModelList.isNullOrEmpty()) {
                        for (financeModel in financeModelList) {
                            // Criar um novo LinearLayout
                            val newLinearLayout = LinearLayout(requireContext())
                            val layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                100.dpToPx(requireContext())
                            )
                            layoutParams.topMargin = 16.dpToPx(requireContext())
                            newLinearLayout.layoutParams = layoutParams
                            newLinearLayout.orientation = LinearLayout.HORIZONTAL
                            newLinearLayout.background = ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.card_carteira
                            )

                            newLinearLayout.gravity = Gravity.CENTER_VERTICAL

                            val logoEmpresa = ImageView(requireContext())
                            logoEmpresa.layoutParams = LinearLayout.LayoutParams(
                                60.dpToPx(requireContext()),
                                48.dpToPx(requireContext()),
                            )
                            logoEmpresa.setImageResource(R.drawable.logo_small)
                            newLinearLayout.addView(logoEmpresa)


                            val linearSubCard = LinearLayout(requireContext())
                            linearSubCard.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            linearSubCard.gravity = Gravity.CENTER_HORIZONTAL
                            linearSubCard.orientation = LinearLayout.VERTICAL
                            linearSubCard.setPadding(
                                12.dpToPx(requireContext()),
                                0, // Padding superior
                                12.dpToPx(requireContext()),
                                0 // Padding inferior
                            )

                            // Adicione o nome do projeto (TextView) ao novo LinearLayout de informações do projeto
                            val nomeJob = TextView(requireContext())
                            nomeJob.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            nomeJob.text =
                                financeModel.titleProject // Configure o texto com base nos dados do modelo
                            nomeJob.gravity = Gravity.CENTER
                            nomeJob.setTextColor(Color.WHITE)
                            nomeJob.textSize = 12f
                            nomeJob.setTypeface(
                                ResourcesCompat.getFont(
                                    requireContext(),
                                    R.font.nunito_black
                                )
                            )
                            linearSubCard.addView(nomeJob)

                            // Adicione a data do projeto (TextView) ao novo LinearLayout de informações do projeto
                            val dataJob = TextView(requireContext())
                            dataJob.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            val dataOriginal = financeModel.finishDate
                            val dataFormatada = formatarData(dataOriginal)
                            dataJob.text = dataFormatada
                            dataJob.gravity = Gravity.CENTER
                            dataJob.setTextColor(Color.parseColor("#20AC69"))
                            dataJob.textSize = 12f
                            dataJob.setTypeface(
                                ResourcesCompat.getFont(
                                    requireContext(),
                                    R.font.nunito_black
                                )
                            )
                            linearSubCard.addView(dataJob)

                            // Adicione o LinearLayout de informações do projeto ao novo LinearLayout
                            newLinearLayout.addView(linearSubCard)

                            // Adicione o valor do projeto (TextView) ao novo LinearLayout
                            val valorJob = TextView(requireContext())
                            valorJob.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            valorJob.text =
                                "$" + financeModel.value.toString() // Configure o texto com base nos dados do modelo
                            valorJob.gravity = Gravity.CENTER
                            valorJob.setTextColor(Color.WHITE)
                            valorJob.textSize = 12f
                            valorJob.setTypeface(
                                ResourcesCompat.getFont(
                                    requireContext(),
                                    R.font.nunito_black
                                )
                            )
                            newLinearLayout.addView(valorJob)

                            linearLayoutParent?.addView(newLinearLayout)
                        }
                    } else {
                        //lista vazia
                    }
                } else {
                    when (response.code()) {
                        403 -> exibirSnackbar("Campos incorretos")
                        else -> Log.d("TAG", response.code().toString())
                    }
                }
            }

            override fun onFailure(call: Call<List<ProjectProgressCard>>, t: Throwable) {
                exibirSnackbar("Erro na chamada: ${t.message}")
                Log.d("TAG", t.message.toString())
            }
        })
    }


    private fun buscarProjetosRecebidos() {
        if (financeService == null) {
            exibirSnackbar("Problema na service, NULA")
            return
        }


        val call = financeService!!.getAllCompleted(userID)

        call.enqueue(object : Callback<List<ProjectProgressCard>> {
            override fun onResponse(
                call: Call<List<ProjectProgressCard>>,
                response: Response<List<ProjectProgressCard>>
            ) {

                if (response.isSuccessful) {
                    val financeModelList = response.body()
                    var somaTotalDinheiro = 0.0
                    var contagemElementosDinheiro = 0
                    var media = 0.0
                    if (!financeModelList.isNullOrEmpty()) {
                        val primeiroValor = financeModelList.firstOrNull()
                        if (primeiroValor != null) {
                            val tituloTextView = view?.findViewById<TextView>(R.id.titulo)
                            tituloTextView?.text = "$" + primeiroValor.value.toString()


                        }
                        for (financeModel in financeModelList) {
                            // Criar um novo LinearLayout
                            somaTotalDinheiro += financeModel.value
                            contagemElementosDinheiro++
                            media += financeModel.value
                            val newLinearLayout = LinearLayout(requireContext())
                            val layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                100.dpToPx(requireContext())


                            )
                            layoutParams.topMargin = 16.dpToPx(requireContext())
                            newLinearLayout.layoutParams = layoutParams
                            newLinearLayout.orientation = LinearLayout.HORIZONTAL
                            newLinearLayout.background = ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.card_carteira
                            )

                            newLinearLayout.gravity = Gravity.CENTER_VERTICAL

                            val logoEmpresa = ImageView(requireContext())
                            logoEmpresa.layoutParams = LinearLayout.LayoutParams(
                                60.dpToPx(requireContext()),
                                48.dpToPx(requireContext()),
                            )
                            logoEmpresa.setImageResource(R.drawable.logo_small)
                            newLinearLayout.addView(logoEmpresa)


                            val linearSubCard = LinearLayout(requireContext())
                            linearSubCard.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            linearSubCard.gravity = Gravity.CENTER_HORIZONTAL
                            linearSubCard.orientation = LinearLayout.VERTICAL
                            linearSubCard.setPadding(
                                12.dpToPx(requireContext()),
                                0, // Padding superior
                                12.dpToPx(requireContext()),
                                0 // Padding inferior
                            )

                            // Adicione o nome do projeto (TextView) ao novo LinearLayout de informações do projeto
                            val nomeJob = TextView(requireContext())
                            nomeJob.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            nomeJob.text =
                                financeModel.titleProject // Configure o texto com base nos dados do modelo
                            nomeJob.gravity = Gravity.CENTER
                            nomeJob.setTextColor(Color.WHITE)
                            nomeJob.textSize = 12f
                            nomeJob.setTypeface(
                                ResourcesCompat.getFont(
                                    requireContext(),
                                    R.font.nunito_black
                                )
                            )
                            linearSubCard.addView(nomeJob)

                            // Adicione a data do projeto (TextView) ao novo LinearLayout de informações do projeto
                            val dataJob = TextView(requireContext())
                            dataJob.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            val dataOriginal = financeModel.finishDate
                            val dataFormatada = formatarData(dataOriginal)
                            dataJob.text = dataFormatada
                            dataJob.gravity = Gravity.CENTER
                            dataJob.setTextColor(Color.parseColor("#20AC69"))
                            dataJob.textSize = 12f
                            dataJob.setTypeface(
                                ResourcesCompat.getFont(
                                    requireContext(),
                                    R.font.nunito_black
                                )
                            )
                            linearSubCard.addView(dataJob)

                            // Adicione o LinearLayout de informações do projeto ao novo LinearLayout
                            newLinearLayout.addView(linearSubCard)

                            // Adicione o valor do projeto (TextView) ao novo LinearLayout
                            val valorJob = TextView(requireContext())
                            valorJob.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            valorJob.text =
                                "$" + financeModel.value.toString() // Configure o texto com base nos dados do modelo
                            valorJob.gravity = Gravity.CENTER
                            valorJob.setTextColor(Color.WHITE)
                            valorJob.textSize = 12f
                            valorJob.setTypeface(
                                ResourcesCompat.getFont(
                                    requireContext(),
                                    R.font.nunito_black
                                )
                            )
                            newLinearLayout.addView(valorJob)

                            linearLayoutParent?.addView(newLinearLayout)
                        }
                        media /= financeModelList.size
                        val aumentoPorcentagem =
                            ((primeiroValor?.value ?: 0.0 - media) / media) * 100.0
                        val mediaDinheiro = somaTotalDinheiro / contagemElementosDinheiro

                        // Porcentagem do ultimo projeto recebido comparado com a media de todos os projetos recebidos
                        val tituloTextView2 = view?.findViewById<TextView>(R.id.porcentagem)
                        tituloTextView2?.text = "%" + aumentoPorcentagem.toString()

                        // media de todos os projetos recebidos
                        val tituloTextView3 = view?.findViewById<TextView>(R.id.porcentagemNum)
                        tituloTextView3?.text = mediaDinheiro.toString()

                    } else {
                        //lista vazia
                    }
                } else {
                    when (response.code()) {
                        403 -> exibirSnackbar("Campos incorretos")
                        else -> Log.d("TAG", response.code().toString())
                    }
                }
            }

            override fun onFailure(call: Call<List<ProjectProgressCard>>, t: Throwable) {
                exibirSnackbar("Erro na chamada: ${t.message}")
                Log.d("TAG", t.message.toString())
            }
        })
    }

    private fun buscarProjetosCancelados() {
        if (financeService == null) {
            exibirSnackbar("Problema na service, NULA")
            return
        }


        val call = financeService!!.getAllCanceled(userID)

        call.enqueue(object : Callback<List<ProjectProgressCard>> {
            override fun onResponse(
                call: Call<List<ProjectProgressCard>>,
                response: Response<List<ProjectProgressCard>>
            ) {
                if (response.isSuccessful) {
                    val financeModelList = response.body()

                    linearLayoutParent?.removeAllViews()
                    if (!financeModelList.isNullOrEmpty()) {
                        for (financeModel in financeModelList) {
                            // Criar um novo LinearLayout
                            val newLinearLayout = LinearLayout(requireContext())
                            val layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                100.dpToPx(requireContext())
                            )
                            layoutParams.topMargin = 16.dpToPx(requireContext())
                            newLinearLayout.layoutParams = layoutParams
                            newLinearLayout.orientation = LinearLayout.HORIZONTAL
                            newLinearLayout.background = ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.card_carteira
                            )

                            newLinearLayout.gravity = Gravity.CENTER_VERTICAL

                            val logoEmpresa = ImageView(requireContext())
                            logoEmpresa.layoutParams = LinearLayout.LayoutParams(
                                60.dpToPx(requireContext()),
                                48.dpToPx(requireContext()),
                            )
                            logoEmpresa.setImageResource(R.drawable.logo_small)
                            newLinearLayout.addView(logoEmpresa)


                            val linearSubCard = LinearLayout(requireContext())
                            linearSubCard.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            linearSubCard.gravity = Gravity.CENTER_HORIZONTAL
                            linearSubCard.orientation = LinearLayout.VERTICAL
                            linearSubCard.setPadding(
                                12.dpToPx(requireContext()),
                                0, // Padding superior
                                12.dpToPx(requireContext()),
                                0 // Padding inferior
                            )

                            // Adicione o nome do projeto (TextView) ao novo LinearLayout de informações do projeto
                            val nomeJob = TextView(requireContext())
                            nomeJob.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            nomeJob.text =
                                financeModel.titleProject // Configure o texto com base nos dados do modelo
                            nomeJob.gravity = Gravity.CENTER
                            nomeJob.setTextColor(Color.WHITE)
                            nomeJob.textSize = 12f
                            nomeJob.setTypeface(
                                ResourcesCompat.getFont(
                                    requireContext(),
                                    R.font.nunito_black
                                )
                            )
                            linearSubCard.addView(nomeJob)

                            // Adicione a data do projeto (TextView) ao novo LinearLayout de informações do projeto
                            val dataJob = TextView(requireContext())
                            dataJob.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            val dataOriginal = financeModel.finishDate
                            val dataFormatada = formatarData(dataOriginal)
                            dataJob.text = dataFormatada
                            dataJob.gravity = Gravity.CENTER
                            dataJob.setTextColor(Color.parseColor("#20AC69"))
                            dataJob.textSize = 12f
                            dataJob.setTypeface(
                                ResourcesCompat.getFont(
                                    requireContext(),
                                    R.font.nunito_black
                                )
                            )
                            linearSubCard.addView(dataJob)

                            // Adicione o LinearLayout de informações do projeto ao novo LinearLayout
                            newLinearLayout.addView(linearSubCard)

                            // Adicione o valor do projeto (TextView) ao novo LinearLayout
                            val valorJob = TextView(requireContext())
                            valorJob.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            valorJob.text =
                                "$" + financeModel.value.toString() // Configure o texto com base nos dados do modelo
                            valorJob.gravity = Gravity.CENTER
                            valorJob.setTextColor(Color.WHITE)
                            valorJob.textSize = 12f
                            valorJob.setTypeface(
                                ResourcesCompat.getFont(
                                    requireContext(),
                                    R.font.nunito_black
                                )
                            )
                            newLinearLayout.addView(valorJob)

                            linearLayoutParent.addView(newLinearLayout)
                        }
                    } else {
                        //lista vazia
                    }
                } else {
                    when (response.code()) {
                        403 -> exibirSnackbar("Campos incorretos")
                        else -> Log.d("TAG", response.code().toString())
                    }
                }
            }

            override fun onFailure(call: Call<List<ProjectProgressCard>>, t: Throwable) {
                exibirSnackbar("Erro na chamada: ${t.message}")
                Log.d("TAG", t.message.toString())
            }
        })
    }


    private fun exibirSnackbar(mensagem: String) {
        Snackbar.make(
            binding.root,
            mensagem,
            Snackbar.LENGTH_SHORT
        ).show()
    }


    fun Int.dpToPx(context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (this * density).toInt()
    }

//    fun formatarData(dataOriginal: String): String {
//        val sdfOriginal = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//        val sdfNovo = SimpleDateFormat("dd/MM/yyyy")
//
//
//        try {
//            val data = sdfOriginal.parse(dataOriginal)
//            return sdfNovo.format(data)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return dataOriginal // Em caso de erro, retorna a data original
//        }
//    }


    fun formatarData(dataOriginal: String): String {
        val sdfOriginal = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val sdfNovo = SimpleDateFormat("dd/MM/yyyy")

        try {
            if (dataOriginal != null) {
                val data = sdfOriginal.parse(dataOriginal)
                return sdfNovo.format(data)
            } else {
                return "Data original é nula"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return dataOriginal // Em caso de erro, retorna a data original
        }
    }

}