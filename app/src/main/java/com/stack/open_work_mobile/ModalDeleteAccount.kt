package com.stack.open_work_mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stack.open_work_mobile.databinding.ActivityModalBinding

class ModalDeleteAccount : AppCompatActivity() {

    private val binding by lazy {
        ActivityModalBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}