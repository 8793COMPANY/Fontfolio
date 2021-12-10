package com.corporation8793.fontfolio.library.room.dao.board

import androidx.room.*
import com.corporation8793.fontfolio.library.room.entity.board.Board
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
interface BoardDao {

    @Query("SELECT * FROM board")
    fun getAll() : List<Board>

    @Query("SELECT * FROM board WHERE board_name == :boardName")
    fun getBoardData(boardName: String) : List<Board>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg font : Font)

    @Update
    fun update(vararg font : Font)
}