package com.corporation8793.fontfolio.library.room.dao.qna

import androidx.room.Dao
import androidx.room.Query
import com.corporation8793.fontfolio.library.room.entity.qna.Answer
import com.corporation8793.fontfolio.library.room.entity.qna.Question

@Dao
interface AnswerDao {
    /**
     * 모든 [Answer]를 [List]로 반환합니다.
     * @author  두동근
     * @return  List<Answer>
     */
    @Query("SELECT * FROM answer")
    fun getAll() : List<Answer>

    /**
     * [qid]에 대한 [Answer]만 [List]로 반환합니다.
     * @author  두동근
     * @return  List<Answer>
     */
    // TODO : 쿼리 작업 중...
    @Query("SELECT answer.* FROM answer, question WHERE Question.qid == :qid")
    fun getAnswerByQid(qid : Int) : List<Answer>
}