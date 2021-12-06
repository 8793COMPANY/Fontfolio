package com.corporation8793.fontfolio.library.room.entity.qna

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.util.*

/**
 * [QnAFragment]의 질문(Question) 데이터 클래스.
 * @author  두동근
 * @param   qid 질문 id
 * @param   questionPhotoPath 질문 이미지 경로(Path)
 * @param   questionTitle 질문 제목
 * @param   questionDescription 질문 내용
 * @param   questioner 질문 작성자
 * @param   questionViews 질문 조회수
 * @param   questionDate 질문 작성일
 * @see     [androidx.room.Entity]
 * @see     [com.corporation8793.fontfolio.fragment.QnAFragment]
 */
data class Question(
    @PrimaryKey(autoGenerate = true) val qid : Int = 0,
    @ColumnInfo(name = "questionPhotoPath") val questionPhotoPath : String,
    @ColumnInfo(name = "questionTitle") val questionTitle : String,
    @ColumnInfo(name = "questionDescription") val questionDescription : String,
    @ColumnInfo(name = "questioner") val questioner : Int,
    @ColumnInfo(name = "questionViews") val questionViews : Int = 0,
    @ColumnInfo(name = "questionDate") val questionDate : Calendar)
