package com.corporation8793.fontfolio.common

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent

/**
 * Fontfolio 의 애플리케이션 싱글톤 클래스 입니다.
 * @author  두동근
 * @see     Application
 */
class Fontfolio : Application() {
    lateinit var context: Context

    /**
     * 애플리케이션 인스턴스를 반환합니다.
     * @author  두동근
     * @param   c 애플리케이션 콘텍스트.
     * @return  Application
     * @see     Application
     */
    fun getInstance(c: Context) : Fontfolio {
        context = c

        return this
    }

    /**
     * 다른 액티비티로 화면을 전환하는 함수입니다.
     * @author  두동근
     * @param   act 현재 액티비티
     * @param   class_name 다른 액티비티
     * @param   isFinish 화면을 전환한 뒤, finish() 실행여부. 기본적으로 false.
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
}