package com.corporation8793.fontfolio.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.board.SaveBoardActivity
import com.corporation8793.fontfolio.common.Fontfolio
import com.corporation8793.fontfolio.fragment.*
import com.corporation8793.fontfolio.fragment.qna.QnAFragment
import com.corporation8793.fontfolio.fragment.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener{
    lateinit var bottomNavigationView:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().add(R.id.menu_view, HomeFragment(this)).commit()
        Fontfolio.searchFragment = SearchFragment(this, true)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.page_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.menu_view, HomeFragment(this)).commitAllowingStateLoss()
                return true
            }
            R.id.page_search -> {
                supportFragmentManager.beginTransaction().replace(R.id.menu_view, SearchFragment(this)).commitAllowingStateLoss()
                return true
            }
            R.id.page_camera -> {
                supportFragmentManager.beginTransaction().replace(R.id.menu_view, CameraFragment(this)).commitAllowingStateLoss()
                return false
            }

            R.id.page_qna -> {
                supportFragmentManager.beginTransaction().replace(R.id.menu_view, QnAFragment(this)).commitAllowingStateLoss()
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