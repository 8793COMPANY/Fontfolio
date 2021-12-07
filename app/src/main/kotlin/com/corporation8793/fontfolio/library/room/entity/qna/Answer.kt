package com.corporation8793.fontfolio.library.room.entity.qna

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * [QnAFragment]의 답변(Answer) 데이터 클래스.
 * @author  두동근
 * @param   aid 답변 id
 * @param   qid 답변이 달린 질문의 [Question.qid], 없으면 대댓글.
 * @param   isSelection 답변 채택 여부
 * @param   like 좋아요 수
 * @param   helpful Helpful 수
 * @see     [androidx.room.Entity]
 * @see     [com.corporation8793.fontfolio.fragment.QnAFragment]
 * @see     [com.corporation8793.fontfolio.library.room.entity.qna.Question]
 */
@Entity
data class Answer(
    @PrimaryKey(autoGenerate = true) val aid : Int = 0,
    @ColumnInfo(name = "qid") val qid : Int?,
    @ColumnInfo(name = "isSelection") val isSelection : Boolean = false,
    @ColumnInfo(name = "questioner") val like : Int = 0,
    @ColumnInfo(name = "questionViews") val helpful : Int = 0)