package com.corporation8793.fontfolio.library.room

import androidx.room.*
import com.corporation8793.fontfolio.library.room.converter.TypeConverter
import com.corporation8793.fontfolio.library.room.dao.FontDao
import com.corporation8793.fontfolio.library.room.dao.QuestionDao
import com.corporation8793.fontfolio.library.room.entity.font.Font
import com.corporation8793.fontfolio.library.room.entity.font.FontClassification
import com.corporation8793.fontfolio.library.room.entity.font.FontLicense
import com.corporation8793.fontfolio.library.room.entity.font.FontStyleInformation
import com.corporation8793.fontfolio.library.room.entity.qna.Answer
import com.corporation8793.fontfolio.library.room.entity.qna.Question

/**
 * Fontfolio 애플리케이션의 [RoomDatabase]
 * @author  두동근
 * @see     [androidx.room.RoomDatabase]
 * @see     [androidx.room.Entity]
 * @see     [androidx.room.Dao]
 * @see     [TypeConverter]
 * @see     [Font]
 * @see     [FontClassification]
 * @see     [FontLicense]
 * @see     [FontStyleInformation]
 */
@Database(entities = [Font::class, FontClassification::class, FontLicense::class, FontStyleInformation::class,
                     Question::class, Answer::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    /**
     * [Font]의 [Dao].
     */
    abstract fun fontDao() : FontDao
    /**
     * [Question]의 [Dao].
     */
    abstract fun questionDao() : QuestionDao
}