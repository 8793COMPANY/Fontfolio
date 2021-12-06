package com.corporation8793.fontfolio.library.room.entity.font

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Font_Data.xls 의 2행(row) 단위 중 [Font.fontClassification]열(column)을 [Entity]로 사용하기 위한 데이터 클래스.
 * @author  두동근
 * @see     [androidx.room.Entity]
 * @see     [Font]
 */
@Entity
data class FontClassification(
    @PrimaryKey(autoGenerate = true) var id : Int = 0,
    @ColumnInfo(name = "Serif") var Serif : Boolean = false,
    @ColumnInfo(name = "Sans_Serif") var Sans_Serif : Boolean = false,
    @ColumnInfo(name = "Slab_Serif") var Slab_Serif : Boolean = false,
    @ColumnInfo(name = "Display") var Display : Boolean = false,
    @ColumnInfo(name = "Hand_Written") var Hand_Written : Boolean = false,
    @ColumnInfo(name = "Calligraphy") var Calligraphy : Boolean = false,
    @ColumnInfo(name = "Script") var Script : Boolean = false,
)
