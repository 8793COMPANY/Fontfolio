package com.corporation8793.fontfolio.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.corporation8793.fontfolio.activity.MainActivity
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.common.Fontfolio
import com.corporation8793.fontfolio.dialog.SortByDialog
import com.corporation8793.fontfolio.library.room.entity.Font
import com.corporation8793.fontfolio.recylcerview.FontAdapter
import com.corporation8793.fontfolio.recylcerview.FontItem
import com.corporation8793.fontfolio.recylcerview.SpacesItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment(activity : MainActivity) : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    lateinit var sort_by_btn: Button
    var mActivity = activity

    lateinit var fontfolio: Fontfolio
    lateinit var font_list: RecyclerView

    val datas = mutableListOf<Font>()
    lateinit var mAdapter:FontAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        if (Fontfolio.list.isNullOrEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                Log.e("HomeFragment", "list is Null Or Empty, Trying... re-initialize")
                Fontfolio.list = fontfolio.db.fontDao().getAll()
            }
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
        font_list = view.findViewById(R.id.font_list)

        val sortByDialog = SortByDialog()
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        mAdapter = FontAdapter(mActivity.applicationContext,activity)

        if (Fontfolio.list.isNullOrEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                Log.e("HomeFragment", "list is Null Or Empty, Trying... re-initialize")
                Fontfolio.list = fontfolio.db.fontDao().getAll()
            }
        } else {
            notifyItem()
        }

        font_list.adapter = mAdapter
        font_list.layoutManager = staggeredGridLayoutManager

        font_list.addItemDecoration(SpacesItemDecoration(10))
        

        sort_by_btn.setOnClickListener(View.OnClickListener {
            sortByDialog.show(
                mActivity.supportFragmentManager,
                sortByDialog.tag
            )
        })

        return view
    }

    fun notifyItem(){
        datas.clear()
        for (i in Fontfolio.list){
<<<<<<< HEAD
            Log.e("fontStyle",i.fontStyle)
=======
>>>>>>> 48ab4a840e0dc98c38594c4b3ffcb70f8a4e141e
            datas.add(i)
        }

        mAdapter.datas = datas
        mAdapter.notifyDataSetChanged()
    }
}