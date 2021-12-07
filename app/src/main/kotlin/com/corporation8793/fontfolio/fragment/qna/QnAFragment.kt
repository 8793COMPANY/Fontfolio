package com.corporation8793.fontfolio.fragment.qna

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
import com.corporation8793.fontfolio.library.room.entity.qna.Question
import com.corporation8793.fontfolio.recylcerview.SpacesItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
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
    var q_list = mutableListOf<Question>()
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

        fontfolio = Fontfolio().getInstance(mActivity.applicationContext)

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
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        datas.add(Question(questionPhotoPath = "https://mblogthumb-phinf.pstatic.net/20150605_113/0mansoo_14334864201526UMsu_JPEG/movie_image.jpg?type=w2", questionTitle = "Does anyone know the font on the poster?", questionViews = 109,
            questionDescription = "1번 테스트 질문", questioner = 0, questionDate = "2021-11-13 11:33:20"))
        datas.add(Question(questionPhotoPath = "https://upload.wikimedia.org/wikipedia/ko/9/92/%EC%96%B4%EB%B2%A4%EC%A0%B8%EC%8A%A4_%EC%9D%B8%ED%94%BC%EB%8B%88%ED%8B%B0_%EC%9B%8C.jpg", questionTitle = "What font is this?", questionViews = 84,
            questionDescription = "2번 테스트 질문", questioner = 0, questionDate = "2021-11-25 11:33:20"))
        datas.add(Question(questionPhotoPath = "", questionTitle = "If Image Loading Fail, I'll Show you this image", questionViews = 111,
            questionDescription = "3번 테스트 질문", questioner = 0, questionDate = "2021-12-01 11:33:20"))
        datas.add(Question(questionPhotoPath = "https://onimg.nate.com/orgImg/my/2018/09/17/201809170927585087_1.jpg", questionTitle = "What kind of font is this in comic books?", questionViews = 77,
            questionDescription = "4번 테스트 질문", questioner = 0, questionDate = "2021-12-05 11:33:20"))
        datas.add(Question(questionPhotoPath = "https://cphoto.asiae.co.kr/listimglink/6/2012050413271468373_1.jpg", questionTitle = "What kind of font is this in C books?", questionViews = 25,
            questionDescription = "5번 테스트 질문", questioner = 0, questionDate = sdf.format(cal.time).toString()))
        datas.add(Question(questionPhotoPath = "https://mblogthumb-phinf.pstatic.net/20160503_78/3345_1462207281832vaHKQ_JPEG/7.jpg?type=w2", questionTitle = "What kind of font is this in A books?!", questionViews = 66,
            questionDescription = "6번 테스트 질문", questioner = 0, questionDate = sdf.format(cal.time).toString()))
        datas.add(Question(questionPhotoPath = "https://cdn.notefolio.net/img/5c/48/5c48db78aa280562c4a2a2bc3e7f3626e964afdc37ca5ff45031def60949a68e_v1.jpg", questionTitle = "What kind of font is this in B books?", questionViews = 91,
            questionDescription = "7번 테스트 질문", questioner = 0, questionDate = sdf.format(cal.time).toString()))

        CoroutineScope(Dispatchers.IO).launch {
            fun getList() : MutableList<Question> {
                return fontfolio.db.questionDao().getAll().toMutableList()
            }

            fun setList() {
                if (fontfolio.db.questionDao().getAll().isNullOrEmpty()) {
                    fontfolio.db.questionDao().insertAll(*datas.toTypedArray())
                }
            }

            val a_list = async {
                setList()
                getList()
            }
            q_list = a_list.await()
            Log.e("QnAFragment", "q_list initialize : ${q_list.size} questions")
            q_list.onEach {
                Log.e("q_list", "$it")
            }

            CoroutineScope(Dispatchers.Main).launch {
                mAdapter.datas = q_list
                mAdapter.notifyDataSetChanged()
            }
        }
    }
}