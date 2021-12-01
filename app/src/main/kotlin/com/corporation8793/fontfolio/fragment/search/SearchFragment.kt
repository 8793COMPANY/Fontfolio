package com.corporation8793.fontfolio.fragment.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.activity.MainActivity
import com.corporation8793.fontfolio.activity.ocr.StartOcrActivity
import com.corporation8793.fontfolio.common.Fontfolio
import com.corporation8793.fontfolio.fragment.HomeFragment
import com.corporation8793.fontfolio.library.room.entity.Font
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment(val activity : MainActivity) : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var fontfolio : Fontfolio
    lateinit var total_result : List<Font>
    lateinit var filter_result : MutableList<Font>
    lateinit var search_listview : RecyclerView
    lateinit var search_listview_adapter : SearchListviewAdapter
    var listDataSet : MutableList<SearchListviewAdapter.listData> = mutableListOf()
    var mFragment : SearchFragment = this

    lateinit var search_div : ConstraintLayout
    lateinit var search_bar_div : ConstraintLayout
    lateinit var search_bar_reading_glass_icon : ImageView
    lateinit var search_bar_camera_icon : ImageView
    lateinit var search_bar_input : EditText
    lateinit var search_bar_input_init : ImageView
    lateinit var search_bar_input_cancel : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_search, container, false)
        search_div = view.findViewById(R.id.search_div)
        search_bar_div = view.findViewById(R.id.search_bar_div)
        search_bar_reading_glass_icon = view.findViewById(R.id.search_bar_reading_glass_icon)
        search_bar_camera_icon = view.findViewById(R.id.search_bar_camera_icon)
        search_bar_input = view.findViewById(R.id.search_bar_input)
        search_bar_input_init = view.findViewById(R.id.search_bar_input_init)
        search_bar_input_cancel = view.findViewById(R.id.search_bar_input_cancel)
        search_listview = view.findViewById(R.id.search_listview)

        fontfolio = Fontfolio().getInstance(activity.applicationContext)

        if (Fontfolio.list.isNullOrEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                Log.e("SearchFragment", "list is Null Or Empty, Trying... re-initialize")
                Fontfolio.list = fontfolio.db.fontDao().getAll()
            }
        } else {
            filter_result = Fontfolio.list.toMutableList()
        }

        // 스크롤 방지 코드
        search_listview.layoutManager = object : LinearLayoutManager(context) { override fun canScrollVertically(): Boolean { return false } }

        // 자동 키보드 노출
        search_bar_input.showSoftKeyboard()

        search_bar_camera_icon.setOnClickListener {
            activity.supportFragmentManager.beginTransaction().replace(R.id.menu_view, HomeFragment(activity)).commitAllowingStateLoss()
            activity.bottomNavigationView.selectedItemId = R.id.page_home
            fontfolio.moveToActivity(requireActivity(), StartOcrActivity::class.java, false)
        }

        search_bar_input_init.setOnClickListener {
            search_bar_input.text.clear()
        }

        search_bar_input_cancel.setOnClickListener {
            search_bar_input.text.clear()
            search_bar_reading_glass_icon.visibility = View.VISIBLE
            search_bar_camera_icon.visibility = View.VISIBLE
            search_bar_input_cancel.visibility = View.GONE
            search_bar_input_init.visibility = View.GONE
            search_div.constrainPercentWidth(R.id.search_bar_div, 0.95f)
            search_bar_div.setHorizontalBias(R.id.search_bar_input, 0.37f)
        }

        search_bar_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    filter_result = Fontfolio.list.toMutableList()
                    filter_result = filter_result.filter { font -> font.fontName.contains(s)}.toMutableList()

                    search_listview_adapter = SearchListviewAdapter(mFragment, filter_result, listDataSet, s)
                    search_listview.adapter = search_listview_adapter
                    search_listview.adapter?.notifyDataSetChanged()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.isEmpty()) {
                        search_bar_reading_glass_icon.visibility = View.VISIBLE
                        search_bar_camera_icon.visibility = View.VISIBLE
                        search_bar_input_cancel.visibility = View.GONE
                        search_bar_input_init.visibility = View.GONE
                        search_div.constrainPercentWidth(R.id.search_bar_div, 0.95f)
                        search_bar_div.setHorizontalBias(R.id.search_bar_input, 0.37f)

                        search_listview_adapter = SearchListviewAdapter(mFragment, mutableListOf(), listDataSet)
                        search_listview.adapter = search_listview_adapter
                        search_listview.adapter?.notifyDataSetChanged()
                    } else {
                        search_bar_reading_glass_icon.visibility = View.GONE
                        search_bar_camera_icon.visibility = View.GONE
                        search_bar_input_cancel.visibility = View.VISIBLE
                        search_bar_input_init.visibility = View.VISIBLE
                        search_div.constrainPercentWidth(R.id.search_bar_div, 0.75f)
                        search_bar_div.setHorizontalBias(R.id.search_bar_input, 0.14f)
                    }
                }
            }
        }
    )

        return view
    }

    // 써치-바 PercentWidth 동적 크기 변경
    fun ConstraintLayout.constrainPercentWidth(@IdRes targetViewId: Int, bias: Float) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.constrainPercentWidth(targetViewId, bias)
        constraintSet.applyTo(this)
    }

    // 써치-바 인풋 setHorizontalBias 동적 크기 변경
    fun ConstraintLayout.setHorizontalBias(@IdRes targetViewId: Int, bias: Float) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.setHorizontalBias(targetViewId, bias)
        constraintSet.applyTo(this)
    }

    fun View.showSoftKeyboard() {
        this.requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }
}