package com.stack.open_work_mobile.activities.lay_login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.stack.open_work_mobile.databinding.ActivityLoginBinding
import com.stack.open_work_mobile.activities.lay_home.HomeActivity
import com.stack.open_work_mobile.api.Rest
import com.stack.open_work_mobile.models.authModel.AuthForm
import com.stack.open_work_mobile.models.authModel.AuthResponse
import com.stack.open_work_mobile.services.AuthService
import com.stack.open_work_mobile.utils.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val api by lazy {
        Rest.getInstance()?.create(AuthService::class.java)
    }

    private val home by lazy {
        Intent(this, HomeActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email: String = binding.emailInput.editText?.text.toString()
            val pass: String = binding.senhaInput.editText?.text.toString()
            when {

                email.isEmpty() -> {
                    binding.emailInput.error = "Preencha seu e-mail"
                }

                pass.isEmpty() -> {
                    binding.senhaInput.error = "Coloque sua senha"
                }

//                !Util.isValidEmail(email) -> {
//                    Toast.makeText(
//                        this,
//                        "E-mail inválido",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }

                pass.length < 6 -> {
                    Toast.makeText(
                        this,
                        "Senha deve ter no mínimo 6 caracteres",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> login(email, pass)
            }
        }
    }

    private fun login(email: String, pass: String) {
        val loginRequest = AuthForm(email, pass)

        Log.d("LoginActivity", "Email: $email, Password: $pass")
        api?.authentication(loginRequest)?.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val auth = getSharedPreferences("AUTH", MODE_PRIVATE)
                    val id = getSharedPreferences("IDENTIFY", MODE_PRIVATE)
                    val img = getSharedPreferences("IMAGE", MODE_PRIVATE)

                    val editorAuth = auth.edit()
                    editorAuth.putString("TOKEN", response.body()?.token)
                    editorAuth.apply()

                    val editorId = id.edit()
                    response.body()?.userId?.let { editorId.putLong("ID", it.toLong()) }
                    editorId.apply()

                    val editorImg = img.edit()
                    response.body()?.image?.let { editorImg.putString("IMG", it) }
                    editorImg.apply()

                    Handler(Looper.getMainLooper()).postDelayed({
                        Toast.makeText(
                            baseContext,
                            "Login efetuado com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()
                        navMainState()
                    }, 100)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun navMainState() {
        startActivity(home)
        finish()
    }
}


