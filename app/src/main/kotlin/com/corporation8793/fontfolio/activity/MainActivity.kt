package com.corporation8793.fontfolio.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.board.SaveBoardActivity
import com.corporation8793.fontfolio.common.Fontfolio
import com.corporation8793.fontfolio.fragment.CameraFragment
import com.corporation8793.fontfolio.fragment.HomeFragment
import com.corporation8793.fontfolio.fragment.factory.FragmentFactory
import com.corporation8793.fontfolio.fragment.qna.QnAFragment
import com.corporation8793.fontfolio.fragment.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener{
    lateinit var bottomNavigationView:BottomNavigationView
    //commit 확인
    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = FragmentFactory(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().add(R.id.menu_view,
            supportFragmentManager.fragmentFactory.instantiate(classLoader, HomeFragment::class.java.name)
        ).commit()
        Fontfolio.searchFragment = SearchFragment(this, true)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.page_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.menu_view,
                    supportFragmentManager.fragmentFactory.instantiate(classLoader, HomeFragment::class.java.name)
                ).commitAllowingStateLoss()
                return true
            }
            R.id.page_search -> {
                supportFragmentManager.beginTransaction().replace(R.id.menu_view,
                    supportFragmentManager.fragmentFactory.instantiate(classLoader, SearchFragment::class.java.name)
                ).commitAllowingStateLoss()
                return true
            }
            R.id.page_camera -> {
                supportFragmentManager.beginTransaction().replace(R.id.menu_view,
                    supportFragmentManager.fragmentFactory.instantiate(classLoader, CameraFragment::class.java.name)
                ).commitAllowingStateLoss()
                return false
            }

            R.id.page_qna -> {
                supportFragmentManager.beginTransaction().replace(R.id.menu_view,
                    supportFragmentManager.fragmentFactory.instantiate(classLoader, QnAFragment::class.java.name)
                ).commitAllowingStateLoss()
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