package com.stack.open_work_mobile.activities.lay_profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.activities.lay_home.HomeActivity
import com.stack.open_work_mobile.activities.lay_home.HomeMenuFragment
import com.stack.open_work_mobile.activities.lay_my_projects.ProjectProgressCard
import com.stack.open_work_mobile.api.Rest
import com.stack.open_work_mobile.databinding.ActivityProfileBinding
import com.stack.open_work_mobile.databinding.FragmentHomeMenuBinding
import com.stack.open_work_mobile.models.ApiResponse
import com.stack.open_work_mobile.models.ProfileModel
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.iconBack.setOnClickListener {
            startActivity(home)
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
                TODO("Not yet implemented")
            }
        })
    }
}