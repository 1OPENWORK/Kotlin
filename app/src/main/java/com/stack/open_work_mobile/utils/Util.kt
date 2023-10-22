package com.stack.open_work_mobile.utils

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

internal class Util : AppCompatActivity() {
    companion object {

        fun isValidEmail(email: String): Boolean {
            val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
            return email.matches(emailRegex.toRegex())
        }

        fun applyIconColor(
            bottomNavigationView: BottomNavigationView,
            iconColorStateList: ColorStateList
        ) {
            val menu = bottomNavigationView.menu

            for (i in 0 until menu.size()) {
                val menuItem = menu.getItem(i)
                val icon = menuItem.icon
                val iconWithTint = icon?.mutate()
                iconWithTint?.setTintList(iconColorStateList)
                menuItem.icon = iconWithTint
            }
        }


    }
}