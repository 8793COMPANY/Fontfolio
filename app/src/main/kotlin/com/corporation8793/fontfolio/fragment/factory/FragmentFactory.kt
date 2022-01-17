package com.corporation8793.fontfolio.fragment.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.corporation8793.fontfolio.activity.MainActivity
import com.corporation8793.fontfolio.fragment.CameraFragment
import com.corporation8793.fontfolio.fragment.HomeFragment
import com.corporation8793.fontfolio.fragment.qna.QnAFragment
import com.corporation8793.fontfolio.fragment.search.SearchFragment

class FragmentFactory(val activity : MainActivity) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            HomeFragment::class.java.name -> HomeFragment(activity)
            SearchFragment::class.java.name -> SearchFragment(activity)
            CameraFragment::class.java.name -> CameraFragment(activity)
            QnAFragment::class.java.name -> QnAFragment(activity)
            else -> super.instantiate(classLoader, className)
        }
    }
}