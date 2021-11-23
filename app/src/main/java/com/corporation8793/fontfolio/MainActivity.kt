package com.corporation8793.fontfolio

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.corporation8793.fontfolio.board.SaveBoardActivity
import com.corporation8793.fontfolio.fragment.*
import com.corporation8793.fontfolio.login.AppLoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener{
    lateinit var bottomNavigationView:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().add(R.id.menu_view, HomeFragment(this)).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.page_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.menu_view , HomeFragment(this)).commitAllowingStateLoss()
                return true
            }
            R.id.page_search -> {
                supportFragmentManager.beginTransaction().replace(R.id.menu_view, SearchFragment()).commitAllowingStateLoss()
                return true
            }
            R.id.page_camera -> {
                supportFragmentManager.beginTransaction().replace(R.id.menu_view, CameraFragment()).commitAllowingStateLoss()
                return true
            }

            R.id.page_qna -> {
                supportFragmentManager.beginTransaction().replace(R.id.menu_view, QnAFragment()).commitAllowingStateLoss()
                return true
            }

            R.id.page_info -> {
                val intent = Intent(this, SaveBoardActivity::class.java)
                startActivity(intent)

//                supportFragmentManager.beginTransaction().replace(R.id.menu_view, InfoFragment()).commitAllowingStateLoss()
//                return true
            }
        }

        return false
    }
}