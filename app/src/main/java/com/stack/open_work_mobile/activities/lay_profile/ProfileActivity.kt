package com.stack.open_work_mobile.activities.lay_profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.activities.lay_home.HomeActivity
import com.stack.open_work_mobile.activities.lay_login.EntranceActivity
import com.stack.open_work_mobile.api.Rest
import com.stack.open_work_mobile.databinding.ActivityProfileBinding
import com.stack.open_work_mobile.models.ApiResponse
import com.stack.open_work_mobile.models.UpdateResponse
import com.stack.open_work_mobile.models.authModel.UpdateProfileModel
import com.stack.open_work_mobile.services.ProfileService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

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

    private val entrance by lazy {
        Intent(this, EntranceActivity::class.java)
    }

    private val profile by lazy {
        UpdateProfileModel("" ,"","","","")
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

        binding.btnAlterarDados.setOnClickListener {
            updateProfileInfo()
        }

        tryGetInfo()
    }

    private fun tryGetInfo() {
        val userId = this.getSharedPreferences("IDENTIFY", MODE_PRIVATE).getLong("ID", 0)

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

                    val imgUrl = profile?.image
                    if (imgUrl != null) {
                        val imgProfile = findViewById<ShapeableImageView>(R.id.img_profile)
                        Glide.with(this@ProfileActivity)
                            .load(imgUrl)
                            .into(imgProfile)
                    }
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
            api?.deleteProfile(userId)?.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        dialog.dismiss()
                        startActivity(entrance)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    dialog.dismiss()
                    startActivity(entrance)
                }
            })
        }
        dialog = build.create()
        dialog.show()
    }

    private fun updateProfileInfo() {
        val userId = this.getSharedPreferences("IDENTIFY", MODE_PRIVATE).getLong("ID", 0)
        profile.name = binding.inputNome.text.toString()
        profile.email = binding.inputEmail.text.toString()
        profile.cpfCnpj = binding.inputCpf.text.toString()
        profile.cellphone = binding.inputTelefone.text.toString()
        profile.password = binding.inputSenha.text.toString()

        api?.putProfileInfo(userId, profile)?.enqueue(object : Callback<UpdateResponse> {
            override fun onResponse(call: Call<UpdateResponse>, response: Response<UpdateResponse>) {
                if (response.isSuccessful) {
                    val newToken = response.body()?.token
                    val auth = getSharedPreferences("AUTH", MODE_PRIVATE)
                    val editorAuth = auth.edit()
                    editorAuth.putString("TOKEN", newToken)
                    editorAuth.apply()
                    Toast.makeText(baseContext, "Perfil atualizado com sucesso", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(baseContext, "Não atualizado", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}