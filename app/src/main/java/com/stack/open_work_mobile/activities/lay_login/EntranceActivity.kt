package com.stack.open_work_mobile.activities.lay_login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.databinding.ActivityEntranceBinding

class EntranceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEntranceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntranceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnDirect.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}




