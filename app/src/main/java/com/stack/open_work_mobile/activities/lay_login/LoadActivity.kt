package com.stack.open_work_mobile.activities.lay_login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.stack.open_work_mobile.R

class LoadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, EntranceActivity::class.java)
            startActivity(intent)
            finish()

        }, 3000)
    }
}