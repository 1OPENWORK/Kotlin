package com.stack.open_work_mobile.activities.lay_home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.activities.Notify.NotificationActivity
import com.stack.open_work_mobile.activities.Rating.RatingCompanies
import com.stack.open_work_mobile.activities.lay_my_projects.MyProject
import com.stack.open_work_mobile.activities.lay_profile.ProfileActivity
import com.stack.open_work_mobile.databinding.ActivityHomeBinding
import com.stack.open_work_mobile.utils.Util


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val myProject by lazy {
        Intent(this, MyProject::class.java)
    }
    private val avaliation by lazy {
        Intent(this, RatingCompanies::class.java)
    }
    private val notify by lazy {
        Intent(this, NotificationActivity::class.java)
    }

    private val profile by lazy {
        Intent(this, ProfileActivity::class.java)
    }


    @SuppressLint("UseCompatLoadingForColorStateLists")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeMenuFragment())

        binding.idNotify.setOnClickListener {
            startActivity(notify)
        }
        binding.profileImageView.setOnClickListener {
            startActivity(profile)
        }
        binding.bottomNavigationViewHome.setOnItemSelectedListener() {
            when (it.itemId) {
                R.id.house_home_id -> replaceFragment(HomeMenuFragment())
                R.id.project_home_id -> replaceFragment(JobsFragment())
                R.id.my_project_home_id -> {
                    startActivity(myProject)
                }

                R.id.avaliation_home_id -> {
                    startActivity(avaliation)
                }

                R.id.finance_home_id -> replaceFragment(FinanceFragment())
            }
            true
        }
        val select = resources.getColorStateList(R.color.bottom_nav_icon_selector)
        Util.applyIconColor(binding.bottomNavigationViewHome, select)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout_home, fragment)
        fragmentTransaction.commit()
    }


}





