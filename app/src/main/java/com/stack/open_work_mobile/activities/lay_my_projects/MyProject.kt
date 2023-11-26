package com.stack.open_work_mobile.activities.lay_my_projects

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.activities.Notify.NotificationActivity
import com.stack.open_work_mobile.databinding.ActivityMyProjectsBinding
import com.stack.open_work_mobile.activities.lay_home.HomeActivity
import com.stack.open_work_mobile.utils.Util

@Suppress("DEPRECATION")
class MyProject : AppCompatActivity() {

    private lateinit var binding: ActivityMyProjectsBinding

    private val notify by lazy {
        Intent(this, NotificationActivity::class.java)
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProjectsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(ProgressMenuFragment())

        binding.backToHome.setOnClickListener {
            val intentToHome = Intent(this, HomeActivity::class.java)
            startActivity(intentToHome)
        }

        binding.idNotify.setOnClickListener {
            startActivity(notify)
        }

        binding.bottomNavigationViewMyProject.setOnItemSelectedListener() {
            when (it.itemId) {
                R.id.progress_id -> replaceFragment(ProgressMenuFragment())
                R.id.completed_id -> replaceFragment(CompletedMenuFragment())
                R.id.canceled_id -> replaceFragment(CanceledMenuFragment())
            }
            true
        }
        val select = resources.getColorStateList(R.color.bottom_nav_icon_selector)
        Util.applyIconColor(binding.bottomNavigationViewMyProject, select)
    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout_my_project, fragment)
        fragmentTransaction.commit()
    }


}