package com.corporation8793.fontfolio.library.room.dao.font

import androidx.room.*
import com.corporation8793.fontfolio.library.room.entity.font.Font
import com.corporation8793.fontfolio.library.room.entity.font.FontClassification

/**
 * [Font]의 [Dao].
 * @author  두동근
 * @see     Font
 * @see     androidx.room.RoomDatabase
 * @see     androidx.room.Dao
 */
@Dao
interface FontDao {
    /**
     * 모든 [Font]를 [List]로 반환합니다.
     * @author  두동근
     * @return  List<Font>
     */
    @Query("SELECT * FROM font")
    fun getAll() : List<Font>

    // TODO : 쿼리 작업 중...
    @Query("SELECT * FROM font WHERE fontClassification == :fontClassification")
    fun getAllByFontClassification(fontClassification: FontClassification) : List<Font>

    /**
     * [Font.fontName]과 [fontName]이 일치하는 [Font]를 [List]로 반환합니다.
     * @author  두동근
     * @param   fontName  찾으려는 폰트 이름
     * @return  List<Font>
     */
    @Query("SELECT * FROM font WHERE fontName == :fontName")
    fun findByCourseName(fontName: String) : List<Font>

    /**
     * [RoomDatabase]에 [Font]를 [Insert]합니다.
     * @author  두동근
     * @param   font  [Font]의 인스턴스
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg font : Font)

    /**
     * [RoomDatabase]에 [Font]를 [Update]합니다.
     * @author  두동근
     * @param   font  [Font]의 인스턴스
     */
    @Update
    fun update(vararg font : Font)
}