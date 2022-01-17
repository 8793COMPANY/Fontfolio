package com.corporation8793.fontfolio.common

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.room.Room
import com.corporation8793.fontfolio.MySharedPreferences
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.fragment.search.SearchFragment
import com.corporation8793.fontfolio.library.excel.Excel
import com.corporation8793.fontfolio.library.room.AppDatabase
import com.corporation8793.fontfolio.library.room.entity.font.Font
import com.corporation8793.fontfolio.library.room.entity.font.FontClassification
import com.corporation8793.fontfolio.library.room.entity.font.FontLicense
import com.corporation8793.fontfolio.library.room.entity.font.FontStyleInformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Fontfolio 의 애플리케이션 싱글톤 클래스 입니다.
 * @author  두동근
 * @see     Application
 */
class Fontfolio : Application() {
    /**
     * 애플리케이션 콘텍스트.
     */
    lateinit var context: Context
    /**
     * [Excel]의 인스턴스
     */
    lateinit var excel: Excel
    /**
     * [AppDatabase]의 인스턴스
     */
    lateinit var db : AppDatabase

    companion object {
        lateinit var list : List<Font>
        lateinit var prefs : MySharedPreferences
        lateinit var searchFragment : SearchFragment
    }

    /**
     * 애플리케이션 인스턴스를 반환합니다.
     * @author  두동근
     * @param   c 애플리케이션 콘텍스트.
     * @return  Application
     * @see     Application
     */
    fun getInstance(c: Context) : Fontfolio {
        context = c
        excel = Excel(c, "Font_Data.xls")
        db = Room.databaseBuilder(
            c,
            AppDatabase::class.java, "FontDB"
        ).build()

        val job = CoroutineScope(Dispatchers.IO).launch {
            Log.e("onCreate", "list is Null Or Empty, Trying... re-initialize")
            fun getList() : List<Font> {
                return db.fontDao().getAll()
            }

            Log.e("Join", "list initialize")
            val a_list = async { getList() }
            list = a_list.await()
            Log.e("onCreate", "list is OK")
        }

        return this
    }

    /**
     * 엑셀 파일의 데이터를 Room DB 로 마이그레이션합니다.
     * @author  두동근
     * @see     Excel
     * @see     kotlinx.coroutines.CoroutineScope
     */
    fun xlsToRoom() {
        /**
         * [Excel.extractTotalSheet]의 반환값을 저장하는 [List]
         */
        val xlsData : List<Array<String>> = excel.extractTotalSheet(
            arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
                "P", "Q", "R", "S", "T", "U", "V", "W"))
        /**
         * [Font]타입의 [List]
         */
        val roomData : MutableList<Font> = mutableListOf()

        for (row in xlsData.indices) {
            roomData.add(
                Font(
                id = row + 3,
                fontName = xlsData[row][0],
                fontClassification = FontClassification(
                    Serif = xlsData[row][1] == "true",
                    Sans_Serif = xlsData[row][2] == "true",
                    Slab_Serif = xlsData[row][3] == "true",
                    Display = xlsData[row][4] == "true",
                    Hand_Written = xlsData[row][5] == "true",
                    Calligraphy = xlsData[row][6] == "true",
                    Script = xlsData[row][7] == "true"
                ),
                fontStyle = xlsData[row][8],
                fontStyleInformation = FontStyleInformation(
                    id = row + 3,
                    Original = xlsData[row][9] == "true",
                    Normal = xlsData[row][10] == "true"
                ),
                fontLicense = FontLicense(
                    id = row + 3,
                    AL = xlsData[row][11] == "true",
                    PF = xlsData[row][12] == "true",
                    PPF = xlsData[row][13] == "true",
                    GNU_GPL = xlsData[row][14] == "true",
                    OFL = xlsData[row][15] == "true",
                    FPU = xlsData[row][16] == "true",
                    FCU = xlsData[row][17] == "true",
                    Free = xlsData[row][18] == "true",
                    Unknown = xlsData[row][19] == "true"
                ),
                fontLicenseDescription = xlsData[row][20],
                fontCopyrightHolder = xlsData[row][21],
                fontDownloadLink = xlsData[row][22]
            )
            )
        }

        CoroutineScope(Dispatchers.IO).launch {
            if (db.fontDao().getAll().isNullOrEmpty()) {
                Log.i("Application", "DB Data is Empty !! X_X")
                for (i in roomData.indices) {
                    db.fontDao().insertAll(roomData[i])
                    //Log.i("Application", "DB Data Successful Inserted !! -> ${roomData[i]}")
                }
            } else {
                Log.i("Application", "DB Data is Already Exist !! O_O")
                for (i in roomData.indices) {
                    //Log.i("Application", "DB Data Already Exist !! -> ${roomData[i].id} : ${roomData[i].fontName}")
                }
            }
        }
    }

    /**
     * 다른 액티비티로 화면을 전환하는 함수입니다.
     * @author  두동근
     * @param   act 현재 액티비티
     * @param   class_name 다른 액티비티
     * @param   isFinish 화면을 전환한 뒤, finish() 실행여부. false 가 기본값.
     * @return
     * @see     Activity
     */
    fun moveToActivity(act : Activity, class_name : Class<*>, isFinish : Boolean = false) {
        act.apply {
            startActivity(Intent(this, class_name))
            if (isFinish) {
                finish()
            }
        }
    }

    /**
     * 현재 [Activity]에 있는 [TextView]의 폰트를 바꿉니다.
     * @author  두동근
     * @param   act 현재 액티비티
     * @param   textView 현재 액티비티에 위치한 [TextView]의 인스턴스
     * @param   font_name res/font 에 있는 폰트파일의 이름
     * @see     Typeface
     * @see     android.widget.TextView
     * @see     <a href="https://developer.android.com/guide/topics/ui/look-and-feel/fonts-in-xml#fonts-in-code">developer-android</a>
     */
    fun changeFontOfTextView(act : FragmentActivity?, textView: TextView, font_name : String) {
        val res = act?.resources
        val pkg = act?.packageName

        textView.typeface = res?.getFont(res.getIdentifier(
            "${font_name.toLowerCase().replace("-", "_")
                .replace(" ", "_")}",
            "font", pkg))
    }

    override fun onCreate() {
        super.onCreate()
        prefs = MySharedPreferences(applicationContext)
    }
}