package com.corporation8793.fontfolio.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.activity.MainActivity
import com.corporation8793.fontfolio.common.Fontfolio
import com.corporation8793.fontfolio.activity.ocr.StartOcrActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CameraFragment(val activity : MainActivity) : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        activity.supportFragmentManager.beginTransaction().replace(R.id.menu_view, HomeFragment(activity)).commitAllowingStateLoss()
        activity.bottomNavigationView.selectedItemId = R.id.page_home
        Fontfolio().moveToActivity(requireActivity(), StartOcrActivity::class.java, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_camera, container, false)

        return view
    }
}