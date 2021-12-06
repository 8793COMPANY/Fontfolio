package com.corporation8793.fontfolio.fragment.qna

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.activity.MainActivity
import com.corporation8793.fontfolio.common.Fontfolio
import com.corporation8793.fontfolio.dialog.SortByDialog
import com.corporation8793.fontfolio.library.room.entity.font.Font
import com.corporation8793.fontfolio.library.room.entity.qna.Answer
import com.corporation8793.fontfolio.library.room.entity.qna.Question
import com.corporation8793.fontfolio.recylcerview.FontAdapter
import com.corporation8793.fontfolio.recylcerview.SpacesItemDecoration
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class QnAFragment(val mActivity : MainActivity) : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var action_bar_setting_btn : AppCompatButton
    lateinit var action_bar_add_btn : AppCompatButton
    lateinit var qna_list : RecyclerView

    lateinit var fontfolio: Fontfolio

    val datas = mutableListOf<Question>()
    lateinit var mAdapter: qnaAdapter

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
        val view: View = inflater.inflate(R.layout.fragment_qna, container, false)

        action_bar_setting_btn = view.findViewById(R.id.action_bar_setting_btn)
        action_bar_add_btn = view.findViewById(R.id.action_bar_add_btn)
        qna_list = view.findViewById(R.id.qna_list)

        val sortByDialog = SortByDialog()
        //val addQuestionDialog = AddQuestionDialog()
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        mAdapter = qnaAdapter(mActivity.applicationContext, mActivity)

        notifyItem()

        qna_list.adapter = mAdapter
        qna_list.layoutManager = staggeredGridLayoutManager

        qna_list.addItemDecoration(SpacesItemDecoration(10))


        action_bar_setting_btn.setOnClickListener {
            sortByDialog.show(
                mActivity.supportFragmentManager,
                sortByDialog.tag
            )
        }

        action_bar_add_btn.setOnClickListener {

        }

        return view
    }

    fun notifyItem(){
        datas.clear()

        /*
        for (q in Fontfolio.q_list){
            Log.e("question : ", q.)
            datas.add(q)
        }
        */
        // TODO : MOCK DATA
        datas.add(Question(questionPhotoPath = "C://", questionTitle = "1번 질문 호호홍",
            questionDescription = "이건 모게~", questioner = 0, questionDate = Calendar.getInstance()))
        datas.add(Question(questionPhotoPath = "C://", questionTitle = "2번 질문 호호홍",
            questionDescription = "이건 모게~", questioner = 0, questionDate = Calendar.getInstance()))
        datas.add(Question(questionPhotoPath = "C://", questionTitle = "3번 질문 호호홍",
            questionDescription = "이건 모게~", questioner = 0, questionDate = Calendar.getInstance()))

        mAdapter.datas = datas
        mAdapter.notifyDataSetChanged()
    }
}