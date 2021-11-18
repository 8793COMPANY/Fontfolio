package com.corporation8793.fontfolio.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.corporation8793.fontfolio.MainActivity
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.dialog.InitPwBottomDialog
import com.corporation8793.fontfolio.dialog.SortByDialog

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment(activity: MainActivity) : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    lateinit var sort_by_btn: Button
    var mActivity = activity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        sort_by_btn = view.findViewById(R.id.sort_by_btn)

        val sortByDialog = SortByDialog()




        sort_by_btn.setOnClickListener(View.OnClickListener {
            sortByDialog.show(
                mActivity.supportFragmentManager,
                sortByDialog.tag
            )
        })


        return view
    }
}