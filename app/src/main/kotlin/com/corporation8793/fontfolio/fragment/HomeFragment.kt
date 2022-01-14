package com.corporation8793.fontfolio.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.fontfolio.activity.MainActivity
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.common.Fontfolio
import com.corporation8793.fontfolio.dialog.SortByDialog
import com.corporation8793.fontfolio.fragment.search.SearchFragment
import com.corporation8793.fontfolio.library.room.entity.font.Font
import com.corporation8793.fontfolio.recylcerview.FontAdapter
import com.reddit.indicatorfastscroll.FastScrollItemIndicator
import com.reddit.indicatorfastscroll.FastScrollerView
import java.lang.RuntimeException

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment(activity : MainActivity) : Fragment() {
    //push 확인
    private var param1: String? = null
    private var param2: String? = null
    lateinit var sort_by_btn: Button
    var mActivity = activity

    lateinit var fontfolio: Fontfolio
    lateinit var font_list: RecyclerView

    val datas = mutableListOf<Font>()
    val font_large_category= mutableListOf<String>()
    lateinit var mAdapter:FontAdapter
    lateinit var fastScroller: FastScrollerView
//    lateinit var indexBar: IndexFastScrollRecyclerView

    lateinit var sort_title : TextView
    var sortby = 1;
    var titles = arrayOf("Font recommendation","Open Font License","Commercial Paid Fonts","All Fonts")

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
        Fontfolio.searchFragment = SearchFragment(mActivity, true)

        sort_by_btn = view.findViewById(R.id.sort_by_btn)
        font_list = view.findViewById(R.id.font_list)
        sort_title  = view.findViewById(R.id.sort_title)
        fastScroller = view.findViewById(R.id.fastscroller)
        fastScroller.textPadding = 5.0f

        val sortByDialog = SortByDialog()
        sortByDialog.isCancelable = false
        mAdapter = FontAdapter(mActivity.applicationContext,activity)


        sort_title.text = titles[Fontfolio.prefs.getInt("sortBy",1)-1]
        sortby = Fontfolio.prefs.getInt("sortBy",1)
        if(Fontfolio.prefs.getInt("sortBy",1) == 1){
            notifyItem()
        }else if(Fontfolio.prefs.getInt("sortBy",1) == 2){
            getLicenseFontList("OFL (Open Font License)")
        }else if(Fontfolio.prefs.getInt("sortBy",1) == 3){
            getLicenseFontList("Paid Fonts")
        }else{
            getAllFonts()
        }

        fastScroller.visibility = View.INVISIBLE

        font_list.adapter = mAdapter
        font_list.layoutManager = LinearLayoutManager(context)
        font_list.addItemDecoration(DividerItemDecoration(context,1))

        fastScroller.useDefaultScroller = false
        fastScroller.itemIndicatorSelectedCallbacks += object : FastScrollerView.ItemIndicatorSelectedCallback {
            override fun onItemIndicatorSelected(
                indicator: FastScrollItemIndicator,
                indicatorCenterY: Int,
                itemPosition: Int
            ) {
                // Handle scrolling
                font_list.scrollToPosition(itemPosition)
                Log.e("itemPosition",itemPosition.toString())
            }
        }

        fastScroller.setupWithRecyclerView(font_list,{
            position->val item = datas[position]

            FastScrollItemIndicator.Text(
                item.fontName.substring(0,1).toUpperCase()
            )
        })

        

        sort_by_btn.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putInt("num",sortby)
            sortByDialog.arguments = bundle
            sortByDialog.show(
                mActivity.supportFragmentManager,
                sortByDialog.tag
            )

        })

        sortByDialog.setDismissListener(){
            sort_title.text = titles[it-1]
            sortby =it
            if (it==1){
                notifyItem()
                fastScroller.visibility = View.INVISIBLE
                mAdapter.setItemViewType(0)
            }else if (it == 2){
                getLicenseFontList("OFL (Open Font License)")
                fastScroller.visibility = View.VISIBLE
                mAdapter.setItemViewType(1)
            }else if(it ==3){
                getLicenseFontList("Paid Fonts")
                fastScroller.visibility = View.VISIBLE
                mAdapter.setItemViewType(1)
            }else{
                getAllFonts()
                fastScroller.visibility = View.VISIBLE
                mAdapter.setItemViewType(1)
            }
        }

        return view
    }

    fun notifyItem(){
        datas.clear()
        font_large_category.clear()
        var count = 0;

        for (i in Fontfolio.list){

            if((i.fontName.split("-")[0] in font_large_category )){

            }else{
                try {
                    if (!resources.getFont(resources.getIdentifier(
                            "${i.fontName.toLowerCase().replace("-", "_")
                                .replace(" ", "_")}",
                            "font", activity?.packageName)).toString().equals(0x00000000)) {
                        datas.add(i)
                        count++
                    }
                }catch (e : RuntimeException){
                    Log.e("폰트","저장 안 되어 있음")
                }

                font_large_category.add(i.fontName.split("-")[0])

                if (count == 100)
                    break
            }

            }
        mAdapter.datas = datas
    }

    fun getLicenseFontList(license:String){

        val font = Fontfolio.list.filter { font -> font.fontLicenseDescription == license }

        datas.clear()
        font_large_category.clear()
        for (i in font) {
            if ((i.fontName.split("-")[0] in font_large_category)) {

            }else{
                try {
                    if (!resources.getFont(resources.getIdentifier(
                            "${i.fontName.toLowerCase().replace("-", "_")
                                .replace(" ", "_")}",
                            "font", activity?.packageName)).toString().equals(0x00000000)) {
                        Log.e("i", i.fontName)
                        datas.add(i)
                        font_large_category.add(i.fontName.split("-")[0])

                    }
                }catch (e : RuntimeException){
                    Log.e("폰트","저장 안 되어 있음")
                }
            }
        }

        datas.sortBy { it.fontName }
        mAdapter.datas = datas
    }


    fun getAllFonts(){
        datas.clear()
        font_large_category.clear()
        var count = 0;
        for (i in Fontfolio.list){
            if((i.fontName.split("-")[0] in font_large_category )){

            }else{
                try {
                    if (!resources.getFont(resources.getIdentifier(
                            "${i.fontName.toLowerCase().replace("-", "_")
                                .replace(" ", "_")}",
                            "font", activity?.packageName)).toString().equals(0x00000000)) {
                        datas.add(i)
                        count++
                    }
                }catch (e : RuntimeException){
                    Log.e("폰트","저장 안 되어 있음")
                }

//                }


                font_large_category.add(i.fontName.split("-")[0])

            }

        }
        datas.sortBy { it.fontName }
        mAdapter.datas = datas

    }




}
