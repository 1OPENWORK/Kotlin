package com.stack.open_work_mobile.activities.lay_profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.activities.lay_home.HomeActivity
import com.stack.open_work_mobile.api.Rest
import com.stack.open_work_mobile.databinding.ActivityProfileBinding
import com.stack.open_work_mobile.models.ApiResponse
import com.stack.open_work_mobile.services.ProfileService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    private val api by lazy {
        Rest.getInstance()?.create(ProfileService::class.java)
    }

    private val home by lazy {
        Intent(this, HomeActivity::class.java)
    }

    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.iconBack.setOnClickListener {
            startActivity(home)
        }

        binding.btnExcluir.setOnClickListener {
            showDialog()
        }

        tryGetInfo()
    }

    private fun tryGetInfo() {
        val userId =
            this.getSharedPreferences("IDENTIFY", MODE_PRIVATE)
                .getLong("ID", 0)

        api?.getProfileInfo(userId)?.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val profile = response.body()?.perfil
                    val nome = profile?.name
                    val inputNome = findViewById<EditText>(R.id.input_nome)
                    inputNome.setText(nome)

                    val email = profile?.email
                    val inputEmail = findViewById<EditText>(R.id.input_email)
                    inputEmail.setText(email)

                    val telefone = profile?.cellphone
                    val inputTelefone = findViewById<EditText>(R.id.input_telefone)
                    inputTelefone.setText(telefone)

                    val cpf = profile?.cpfCnpj
                    val inputCpf = findViewById<EditText>(R.id.input_cpf)
                    inputCpf.setText(cpf)
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun showDialog() {
        val userId =
            this.getSharedPreferences("IDENTIFY", MODE_PRIVATE)
                .getLong("ID", 0)

        val build = AlertDialog.Builder(this, R.style.ThemeCustomDialog)
        val view = layoutInflater.inflate(R.layout.activity_modal, null)
        build.setView(view)

        val btnCancel = view.findViewById<AppCompatButton>(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        val btnDelete = view.findViewById<AppCompatButton>(R.id.btn_delete)
        btnDelete.setOnClickListener {
            api?.deleteProfile(userId)?.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        Log.d(response.toString(), "testando")
                        build.setView(R.layout.activity_entrance)
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(baseContext, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }
        dialog = build.create()
        dialog.show()
    }
}